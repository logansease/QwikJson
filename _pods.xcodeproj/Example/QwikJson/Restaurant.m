//
//  Encounter.m
//  App
//
//  Created by Logan Sease on 2/25/15.
//  Copyright (c) 2015 iParty Mobile. All rights reserved.
//

#import "Restaurant.h"
#import "Menu.h"

@implementation Restaurant

+(Class)classForKey:(NSString*)key
{
    if([key isEqualToString:@"menus"])
    {
        return [Menu class];
    }
    if([key isEqualToString:@"createdAt"])
    {
        return [DBTimeStamp class];
    }

    return [super classForKey:key];
}

@end
