//
//  MenuItem.h
//  App
//
//  Created by Logan Sease on 12/12/15.
//  Copyright © 2015 iParty Mobile. All rights reserved.
//

#import "QwikJson.h"

@interface MenuItem : QwikJson
@property(nonatomic,strong)NSString * name;
@property(nonatomic,strong)NSString * imageUrl;
@property(nonatomic,strong)NSString * descriptionText;

@end
