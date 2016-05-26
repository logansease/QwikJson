//
//  MenuItem.m
//  App
//
//  Created by Logan Sease on 12/12/15.
//  Copyright © 2015 iParty Mobile. All rights reserved.
//

#import "MenuItem.h"

@implementation MenuItem

+(void)setApiToObjectMapping
{
    //specify custom field mappings for qwikJsonObjects
    [self setApiToObjectMapping:@{@"desc": @"descriptionText"}];
}

@end
