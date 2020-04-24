//
//  ShareFileIntentModule.m
//  Share Intent
//
//  Created by Ajith A B on 16/08/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <MobileCoreServices/MobileCoreServices.h>

#import "RNFileShareIntent.h"
#import "FileHelper.h"


@implementation RNFileShareIntent
static NSExtensionContext* extContext;

RCT_EXPORT_MODULE();

RCT_REMAP_METHOD(getFilePath,
                 resolve:(RCTPromiseResolveBlock)resolve
                 reject:(RCTPromiseRejectBlock)reject ) {
    [FileHelper getItem:[RNFileShareIntent extractDataFromContext] completionHandler:^(NSURL *item, NSError *error) {
       if(error) {
            reject(@"error", error.description, nil);
        } else {
            resolve(@[item.absoluteString]);
        }
    }];
}

RCT_EXPORT_METHOD(close) {
    [ extContext completeRequestReturningItems: @[] completionHandler: nil ];
}

RCT_EXPORT_METHOD(openURL:(NSString *)url) {
    UIApplication *application = [UIApplication sharedApplication];
    NSString *encodedURL =[url stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]];
    NSURL *urlToOpen = [NSURL URLWithString:encodedURL];
    [application openURL:urlToOpen options:@{} completionHandler: nil];
}

+(NSItemProvider *) extractDataFromContext {
    NSExtensionItem *item = [extContext.inputItems firstObject];
    NSItemProvider *attachment = [item.attachments firstObject];
    return attachment;
}

+(void) setContext: (NSExtensionContext*) context{
    extContext = context;
}

@end

