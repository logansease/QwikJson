//
//  QJViewController.m
//  QJsonable
//
//  Created by Logan Sease on 12/14/2015.
//  Copyright (c) 2015 Logan Sease. All rights reserved.
//

#import "QJViewController.h"
#import "Restaurant.h"
#import "Menu.h"
#import "MenuItem.h"

@interface QJViewController ()

@property(nonatomic,strong)Restaurant * restaurant;

@end

@implementation QJViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self addData];
    
    [QwikJson setSerializeNullsByDefault:YES];
    
    //write to prefs
    [self.restaurant writeToPreferencesWithKey:@"data"];
    
    //serialize to dictionary and output
    NSDictionary * dictionary = [self.restaurant toDictionary];
    //self.label.text = [NSString stringWithFormat:@"%@",dictionary];
    self.label.text = [NSString stringWithFormat:@"%@",[dictionary toJsonString]];
    
    NSArray * array = @[self.restaurant];
    [Restaurant writeArray:array toPreferencesWithKey:@"PREFS"];
    
    NSArray * loaded = [Restaurant readArrayFromPrefencesWithKey:@"PREFS"];
    NSArray * dicts = [Restaurant toDictionaryArrayFrom:loaded];
    NSLog(@"%@", [dicts toJsonString]);
}

-(IBAction)makeMagic:(id)sender
{
    //read from prefs
    self.restaurant = [Restaurant readFromPrefencesWithKey:@"data"];
    
    //serialize
    NSDictionary * dictionary = [self.restaurant toDictionary];
    
    //deserialize
    self.restaurant = [Restaurant objectFromDictionary:dictionary];
    
    //serialize again
    dictionary = [self.restaurant toDictionary];
    
    //output
    self.label.text = [NSString stringWithFormat:@"RESERIALIZED!:\n%@",dictionary];
}

-(void)addData
{
    Restaurant * rest = [Restaurant objectWithId:@"1"];
    rest.name = @"Testaurant";
    rest.createdAt = [[DBTimeStamp alloc]initWithDate:[NSDate date]];
    self.restaurant= rest;
    
    Menu * menu = [Menu objectWithId:@"2"];
    menu.name = @"Food Menu";
    rest.menus = @[menu];
    
    MenuItem * item = [MenuItem objectWithId:@"3"];
    item.name = @"Yummy";
    item.descriptionText = @"Good stuff!";
    menu.menuItems = @[item];
    item.serializeNulls = kNullSerializationSettingSerialize;
}



- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
