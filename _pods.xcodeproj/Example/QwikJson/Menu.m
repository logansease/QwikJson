//
//  Encounter.m
//  App
//
//  Created by Logan Sease on 2/25/15.
//  Copyright (c) 2015 iParty Mobile. All rights reserved.
//

#import "Menu.h"
#import "MenuItem.h"

@implementation Menu


+(NSDictionary<NSString*,NSString*>*)apiToObjectMapping
{
    //specify custom field mappings for qwikJsonObjects
    return @{@"menu_items": @"menuItems"};
}

+(Class)classForKey:(NSString*)key
{
    if([key isEqualToString:@"menuItems"])
    {
        return [MenuItem class];
    }
    return [super classForKey:key];
}

@end
