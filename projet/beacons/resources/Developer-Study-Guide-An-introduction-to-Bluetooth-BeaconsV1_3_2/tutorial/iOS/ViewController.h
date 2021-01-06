//
//  ViewController.h
//  demo
//
//  Created by RenKai on 2018/7/1.
//  Copyright © 2018 Kai Ren. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BeaconModel.h"
@import CoreBluetooth;

@interface ViewController : UITableViewController <UITableViewDataSource, CBCentralManagerDelegate>
@property (nonatomic, strong) NSMutableArray* beacons;

@end

