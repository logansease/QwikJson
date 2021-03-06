# QwikJson

[![CI Status](http://img.shields.io/travis/Logan Sease/QwikJson.svg?style=flat)](https://travis-ci.org/Logan Sease/QwikJson)
[![Version](https://img.shields.io/cocoapods/v/QwikJson.svg?style=flat)](http://cocoapods.org/pods/QwikJson)
[![License](https://img.shields.io/cocoapods/l/QwikJson.svg?style=flat)](http://cocoapods.org/pods/QwikJson)
[![Platform](https://img.shields.io/cocoapods/p/QwikJson.svg?style=flat)](http://cocoapods.org/pods/QwikJson)

## Summary
In our ReSTful API world, we are constantly passing JSON objects to our api and receiving them back. Constantly serializating these objects to and from json string and dictionaries can be cumbersome and can make your model classes and data services start to fill up with boiler plate parsing code.

To solve this, I introduce QwikJson. An amazingly powerful and simple library for serializing and deserializing json objects.

Simply have your model classes extend QwikJson and the world shall become your oyster.

QwikJson makes converting objects to dictionaries and arrays of dictionaries (and Vice Versa) a breeze. It includes support for nested model objects, nested array model objects, multiple styles of dates and times, simple storage to NSUserDefaults and conversion to and from JSON Strings.


## Installation

QwikJson is available through [CocoaPods](http://cocoapods.org). To install
it, simply add the following line to your Podfile:

```ruby
pod "QwikJson"
```

And import the following Header file
```ruby
#import "QwikJson.h"
```

This pod is written in Objective-C but works great with Swift projects as well.

## Usage

```
As of Swift 3, all swift properties must contain the @objc tag to be visible to swift
```

make a model class and extend QwikJson, and add your fields
```objective-c
//menu.h
@interface Menu : QwikJson
@property(nonatomic,strong)NSString * name;
@property(nonatomic,strong)NSArray * menuItems;
@end
```

```swift
class Menu : QwikJson
{
    @objc var name : String?
}
```

now you can convert from dictionaries and vice versa with ease
```objective-c
//deserialize
menu = [Menu objectFromDictionary:dictionary];

//serialize again
dictionary = [menu toDictionary];
```

Use Nested Objects (even nested arrays) and custom date serlizers
```objective-c
//restaurant.h
@interface Restaurant : QwikJson
@property(nonatomic,strong)NSString * image_url;
@property(nonatomic,strong)NSString * name;
@property(nonatomic,strong)NSArray * menus;
@property(nonatomic,strong)DBTimeStamp * createdAt;
@end

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
```

Or in swift
```swift
class Restaurant: QwikJson {
   @objc var imageUrl: String?
   @objc var name: String?
   @objc var menus: [Menu] = []
   @objc var createdAt: DBTimeStamp?
   
   override public class func type(forKey: String!) -> AnyClass!
   {
    if forKey == "createdAt"
    {
        return DBTimeStamp
    }
   
    if forKey == "menu"
    {
        return Menu.self
    }

    return super.type(forKey: forKey)
   }
}
```

Perform Specialized Logic during serialization or deserialization.
```objective-c

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
-(void)serializeObject:(NSObject*)object withApiKey:(NSString*)apiKey fromKey:(NSString*)objectKey toDictionary:(NSMutableDictionary*)dictionary
{
    //adjust the property name since the database is formatted with _'s instead of camel case
    if([objectKey isEqualToString:@"menuItems"])
    {
        apiKey = @"menu_items";
    }
    [super serializeObject:object withApiKey:apiKey fromKey:objectKey toDictionary:dictionary];
}
```

Define a property map to name your object fields differently for your model objects vs the API. This may be necessary if you use a reserved keyword like "description" or if your api returns underscore cased fields
```
+(NSDictionary<NSString*,NSString*>*)apiToObjectMapping
{
    //specify custom field mappings for qwikJsonObjects
    return @{@"description": @"descriptionText"};
}
```

Define an array of transient properties to specify properties that should not be written during serialization.
Note that some fields are marked transient by default. These are as follows:
#define kDefaultTransientProperties @[@"superclass", @"hash", @"debugDescription", @"description"]
If you wish to pass one of these variables in, simply rename the field using the apiToObjectMapping method

```
+(NSArray<NSString*>*)transientProperties
{
    return @{@"someCalculatedFieldName"};
}
```

Write straight to preferences
```objective-c
[self.restaurant writeToPreferencesWithKey:@"data"];
self.restaurant = [Restaurant readFromPrefencesWithKey:@"data"];
```

Convert to and from Strings
```objective-c
@interface NSDictionary (QwikJson)
-(NSString*)toJsonString;
+(NSDictionary*)fromJsonString:(NSString*)json;
@end

@interface NSArray (QwikJson)
-(NSString*)toJsonString;
+(NSArray*)fromJsonString:(NSString*)json;
@end
```


## Supported Field Types Types
- Boolean  / Bool
- NSString / String
- NSArray  / []
- NSNumber 
- NSDecimalNumber (but you must specify the type in classForKey: )

* Note, if you are using Swift and using booleans, use Bool. DO NOT use Bool? since optional booleans cannot be expressed in objective c

* Another Note: When using Swift 4, all swift properties must be prefaced with @objc for them to be readable.


### Custom Date Serializers, handle parsing various date / time formats

#### DBDate            
2015-12-30

#### DBDateTime        
2015-01-01T10:15:30 

#### DBTimeStamp   
0312345512

#### DBTime            
12:00:00

Note that you can customize the date formats by calling setDateFormat on the date class.
Or call setAlternateDateFormats to provide an array of alternate formats if the primary one fails to parse
```objective-c
[DBDate setDateFormat:@"MM/DD/YYYY"];
```

For DBTimeStamp, set a multiplier to support millisecond formatting if your api returns milliseconds instead of seconds
```
[DBTimeStamp setTimeStampMultiplier: 1000.0f];
```


### Number / String compatibility
If your api sometimes returns Strings in number fields or if you are supporting NSDecimalNumber in order to support high precision numbers (in which case your api may serialize into Strings), you can use the classForKey method to specify the type and the parser will check to make sure it is deserializing correctly
```
@property NSNumber* amount;
@property NSDecimalNumber* cost;

//restaurant.m
+(Class)classForKey:(NSString*)key
{
    if([key isEqualToString:@"amount"])
    {
        return [NSNumber class];
    }
    if([key isEqualToString:@"cost"])
    {
        return [NSDecimalNumber class];
    }

    return [super classForKey:key];
}
```

## Serialize Nulls
Nulls will not be serlialized by default however there is a global setting as well as a per object setting to decide if nulls should be serialized
```
    [QwikJson setSerializeNullsByDefault:YES];
    object.serializeNulls = kNullSerializationSettingDoNotSerialize;
```

## NSManagedObject Support
If you are using CoreData and would like to use QwikJson, you may also simply import and extend QwikJsonManagedObject instead of QwikJson

## Android

Inside this repo and in the android directory, you will also find a very similar class, QwikJson.java that offers very similar functionality for Android and other Java Platforms.

## Further Notes

In addition to parsing and serializing JSON, the other essential pieice of communicatiing with RESTful APIs is a good
networking library.
Consider using QwikHttp in combination with this library to complete your toolset.
https://github.com/logansease/QwikHttp

Also, checkout the SeaseAssist pod for a ton of great helpers to make writing your iOS code even simpler!
https://github.com/logansease/SeaseAssist


## Author

Logan Sease, lsease@gmail.com

## License

QwikJson is available under the MIT license. See the LICENSE file for more info.
