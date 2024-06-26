//
//  Encounter.h
//  App
//
//  Created by Logan Sease on 2/25/15.
//  Copyright (c) 2015 iParty Mobile. All rights reserved.
//

#import "QwikJson.h"

@interface Restaurant : QwikJson

@property(nonatomic,strong)NSString * image_url;
@property(nonatomic,strong)NSString * name;
@property(nonatomic,strong)NSArray * menus;
@property(nonatomic,strong)DBTimeStamp * createdAt;
@end
