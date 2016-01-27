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

+(Class)classForKey:(NSString*)key
{
    if([key isEqualToString:@"menu_items"] || [key isEqualToString:@"menuItems"])
    {
        return [MenuItem class];
    }
    return [super classForKey:key];
}

//override in subclass to perform some custom deserizliation or change property keys
-(void)writeObjectFrom:(NSDictionary*)inputDictionary forKey:(NSString*)key toProperty:(NSString*)property
{
    //adjust the property name since the database is formatted with _'s instead of camel case
    if([property isEqualToString:@"menu_items"])
    {
        property = @"menuItems";
    }
    
    [super writeObjectFrom:inputDictionary forKey:key toProperty:property];

}

//override in subclass to specify a new key or perform some custom action on serialize
-(void)serializeObject:(NSObject*)object withKey:(NSString*)key toDictionary:(NSMutableDictionary*)dictionary
{
    //adjust the property name since the database is formatted with _'s instead of camel case
    if([key isEqualToString:@"menuItems"])
    {
        key = @"menu_items";
    }
    [super serializeObject:object withKey:key toDictionary:dictionary];
}

@end
