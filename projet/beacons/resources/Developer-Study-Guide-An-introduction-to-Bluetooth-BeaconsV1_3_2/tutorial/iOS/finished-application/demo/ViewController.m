#import "ViewController.h"
#import "BeaconModel.h"

@interface ViewController ()

@property (nonatomic, strong) NSMutableArray* beacons;
@property (nonatomic, strong) CBCentralManager* bleManager;
@property (nonatomic, strong) NSTimer* timer;

@end

@implementation ViewController

@synthesize beacons;
@synthesize bleManager;
@synthesize timer;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    beacons = [[NSMutableArray alloc] init];
    bleManager = [[CBCentralManager alloc] initWithDelegate:self queue:nil];
}

- (void) viewWillDisappear:(BOOL)animated {
    [self.timer invalidate];
    self.timer = nil;
    
    [self stopScanning];
}

- (void) viewDidAppear:(BOOL)animated {
    [self startScanning];
    
    self.timer = [NSTimer scheduledTimerWithTimeInterval:2.5 // 2.5 [seconds]
                                                  target:self
                                                selector:@selector(repeatableValidationTask:)
                                                userInfo:nil
                                                 repeats:YES];
    [self.timer fire];
}

- (void) startScanning {
    static NSMutableDictionary* options = nil;
    if(options == nil) {
        options = [[NSMutableDictionary alloc] init];
        [options setValue:[NSNumber numberWithBool:YES] forKey:CBCentralManagerScanOptionAllowDuplicatesKey];
    }
    
    [bleManager scanForPeripheralsWithServices:nil options:options];
}

- (void) stopScanning {
    [bleManager stopScan];
}

- (void) repeatableValidationTask:(NSTimer*)theTimer {
    if ([self validateBeacons]) {
        [self.tableView reloadData];
    }
}

static const long BEACON_DURATION = 8.5; // 8.5 [seconds]

- (BOOL) validateBeacons {
    BOOL anythingWasRemoved = NO;
    long earliestTimestampAllowed = @([NSDate date].timeIntervalSince1970).longValue - BEACON_DURATION;
    
    NSMutableArray* newArray = [NSMutableArray arrayWithCapacity:beacons.count];
    
    for (BeaconModel* beacon in self.beacons) {
        if (beacon.timestamp >= earliestTimestampAllowed) {
            [newArray addObject:beacon];
        }
        else {
            anythingWasRemoved = YES;
        }
    }
    
    if (anythingWasRemoved) {
        [self.beacons setArray:newArray];
    }
    
    return anythingWasRemoved;
}

#pragma mark - CBCentralManagerDelegate protocol

- (void) centralManagerDidUpdateState:(CBCentralManager *)central {
    if ([central state] == CBManagerStatePoweredOn) {
        [self startScanning];
    }
    else {
        [self stopScanning];
    }
}

- (void) centralManager:(CBCentralManager *)central
  didDiscoverPeripheral:(CBPeripheral *)peripheral
      advertisementData:(NSDictionary *)advertisementData
                   RSSI:(NSNumber *)rssi
{
    // get advetisement data
    NSData* data = [advertisementData objectForKey:CBAdvertisementDataManufacturerDataKey];
    if (data == nil) return;
    unsigned char* bytes = (unsigned char*)[data bytes];
    
    // verify if it is AltBeacon
    int code = ( ((bytes[BEACON_CODE_INDEX] << 8) & 0x0000ff00) |
                  (bytes[BEACON_CODE_INDEX+1]     & 0x000000ff) );
    if(code != BEACON_CODE_VALUE) return;
    
    // get ID of the beacon
    NSString* beaconId = [peripheral.identifier UUIDString];
    
    // get pointer to proper model (create new if needed)
    BeaconModel* beacon = nil;
    for (BeaconModel* tmp in beacons) {
        if (tmp != nil && [tmp.beaconId isEqualToString:beaconId]) {
            beacon = tmp;
            break;
        }
    }
    if (beacon == nil) {
        // reported for the first time - create new model
        beacon = [[BeaconModel alloc] init];
        beacon.beaconId = beaconId;
        beacon.referenceRssi = (bytes[REFERENCE_RSSI_START] & 0xff) - 256;
        beacon.arg1 = ((bytes[CONTENT_START] << 8)   & 0x0000ff00) |
                      ((bytes[CONTENT_START+1])      & 0x000000ff);
        beacon.arg2 = ((bytes[CONTENT_START+2] << 8) & 0x0000ff00) |
                      ((bytes[CONTENT_START+3])      & 0x000000ff);
        beacon.uuid = [self getBeaconUuidFromAdvertisement:bytes];
        
        [self.beacons addObject:beacon];
    }
    
    // update current RSSI field and timestamp
    beacon.currentRssi = rssi.intValue;
    beacon.timestamp = @([NSDate date].timeIntervalSince1970).longValue;
    
    // request to reload the data
    [self.tableView reloadData];
}


#pragma mark - AltBeacon utilities

static const int BEACON_CODE_INDEX = 2;
static const int BEACON_CODE_VALUE = 0xbeac;
static const int UUID_START = 4;
static const int UUID_STOP = 19;
static const int CONTENT_START = 20;
static const int REFERENCE_RSSI_START = 24;

- (NSString*) getBeaconUuidFromAdvertisement:(unsigned char*)adv {
    NSMutableString* val = [NSMutableString stringWithString:@""];
    for (int i = UUID_START, offset = 0; i <= UUID_STOP; ++i, ++offset) {
        [val appendFormat:@"%02x", (adv[i] & 0x000000ff)];
        if (offset == 3 || offset == 5 || offset == 7 || offset == 9) {
            [val appendString:@"-"];
        }
    }
    return [NSString stringWithString:val];
}


#pragma mark - UITableVIewDataSource protocol

- (NSInteger)tableView:(UITableView *)tableView
 numberOfRowsInSection:(NSInteger)section
{
    return beacons.count;
}

- (UITableViewCell*) tableView:(UITableView *)tableView
         cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString* cellIdentifier = @"BeaconItemCell";
    
    UITableViewCell* cell = [self.tableView dequeueReusableCellWithIdentifier:cellIdentifier
                                                                forIndexPath:indexPath];
    
    BeaconModel* beacon = [self.beacons objectAtIndex:indexPath.row];

    cell.textLabel.text = beacon.uuid;
    cell.detailTextLabel.text = [NSString stringWithFormat:@"arg1: %04x   arg2: %04x   RSSI: %d   TxPower: %d",
                                 beacon.arg1 & 0xffff, beacon.arg2 & 0xffff, beacon.currentRssi, beacon.referenceRssi];
    return cell;
}

@end
