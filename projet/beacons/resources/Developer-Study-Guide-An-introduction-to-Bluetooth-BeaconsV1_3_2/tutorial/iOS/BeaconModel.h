//
//  BeaconModel.h
//  demo
//
//  Created by RenKai on 2018/7/1.
//  Copyright © 2018 Kai Ren. All rights reserved.
//

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
