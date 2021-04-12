//
//  ViewController.m
//  demo
//
//  Created by RenKai on 2018/7/1.
//  Copyright © 2018 Kai Ren. All rights reserved.
//

#import "ViewController.h"
@import CoreBluetooth;

@interface ViewController ()
@property (nonatomic, strong) CBCentralManager* bleManager;
@property (nonatomic, strong) NSTimer* timer;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    _beacons = [[NSMutableArray alloc] init];

    _bleManager = [[CBCentralManager alloc] initWithDelegate:self queue:nil];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (NSInteger)tableView:(UITableView *)tableView
 numberOfRowsInSection:(NSInteger)section
{
    return _beacons.count;
}

- (UITableViewCell*) tableView:(UITableView *)tableView
         cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString* cellIdentifier = @"BeaconItemCell";
    
    UITableViewCell* cell = [self.tableView
                             dequeueReusableCellWithIdentifier:cellIdentifier
                             forIndexPath:indexPath];
    
    BeaconModel* beacon = [self.beacons objectAtIndex:indexPath.row];
    
    cell.textLabel.text = beacon.uuid;
    cell.detailTextLabel.text = [NSString
                                 stringWithFormat:@"arg1: %04x arg2: %04x RSSI: %d TxPower: %d",
                                 beacon.arg1 & 0xffff,
                                 beacon.arg2 & 0xffff,
                                 beacon.currentRssi,
                                 beacon.referenceRssi];
    return cell;
}

- (void) viewWillDisappear:(BOOL)animated {
    [self.timer invalidate];
    self.timer = nil;
    
    [self stopScanning];

}

- (void) viewDidAppear:(BOOL)animated {
    [self startScanning];
    
    // fire it, every 4.3 seconds
    self.timer = [NSTimer scheduledTimerWithTimeInterval:4.3
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
        [options setValue:[NSNumber numberWithBool:YES]
                   forKey:CBCentralManagerScanOptionAllowDuplicatesKey];
    }
    
    // we will put scanning start code below . . .
    // start scanning:
    [_bleManager scanForPeripheralsWithServices:nil options:options];
}

- (void) stopScanning {
    [_bleManager stopScan];
}


- (void) centralManagerDidUpdateState:(CBCentralManager *)central {
    if ([central state] == CBManagerStatePoweredOn) {
        [self startScanning];
    }
    else {
        [self stopScanning];
    }
}

static const int BEACON_CODE_INDEX = 2;
static const int BEACON_CODE_VALUE = 0xbeac;
static const int UUID_START = 4;
static const int UUID_STOP = 19;
static const int CONTENT_START = 20;
static const int CONTENT_STOP = 23;
static const int REFERENCE_RSSI_START = 24;

- (void) centralManager:(CBCentralManager *)central
  didDiscoverPeripheral:(CBPeripheral *)peripheral
      advertisementData:(NSDictionary *)advertisementData
                   RSSI:(NSNumber *)rssi
{
    // reporting callback
    NSData* data = [advertisementData
                    objectForKey:CBAdvertisementDataManufacturerDataKey];
    if (data == nil) return;
    unsigned char* bytes = (unsigned char*)[data bytes];

    int code = (((bytes[BEACON_CODE_INDEX] << 8) & 0x0000ff00) |
                (bytes[BEACON_CODE_INDEX+1]     & 0x000000ff) );
    if(code != BEACON_CODE_VALUE) return;

    NSString* beaconId = [peripheral.identifier UUIDString];
    BeaconModel* beacon = nil;
    for (BeaconModel* tmp in _beacons) {
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
    
    [self.tableView reloadData];

}

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

static const long BEACON_DURATION = 8.5; // 8.5 [seconds]
- (BOOL) validateBeacons {
    BOOL anythingWasRemoved = NO;
    long earliestTimestampAllowed =
    @([NSDate date].timeIntervalSince1970).longValue - BEACON_DURATION;
    
    NSMutableArray* newArray = [NSMutableArray arrayWithCapacity:_beacons.count];
    
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

- (void) repeatableValidationTask:(NSTimer*)theTimer {
    if ([self validateBeacons]) {
        [self.tableView reloadData];
    }
}


@end
