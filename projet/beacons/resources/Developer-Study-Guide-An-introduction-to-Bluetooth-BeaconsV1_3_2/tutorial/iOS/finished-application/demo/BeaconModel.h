#import <Foundation/Foundation.h>

@interface BeaconModel : NSObject

@property (nonatomic, strong) NSString* uuid;
@property (nonatomic)             short arg1;
@property (nonatomic)             short arg2;
@property (nonatomic)               int referenceRssi;
@property (nonatomic)               int currentRssi;

@property (nonatomic, strong) NSString* beaconId;
@property (nonatomic)              long timestamp;

@end
