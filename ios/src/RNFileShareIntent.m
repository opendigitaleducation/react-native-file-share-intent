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

RCT_REMAP_METHOD(getFile,
                 resolve:(RCTPromiseResolveBlock)resolve
                 reject:(RCTPromiseRejectBlock)reject ) {
    [FileHelper getFileUrl:[RNFileShareIntent extractDataFromContext] completionHandler:^(NSURL *item, NSError *error) {
       if(error) {
            reject(@"error", error.description, nil);
        } else {
            resolve([FileHelper getFileData:item]);
        }
    }];
}

RCT_REMAP_METHOD(openURL, url:(NSString *)url) {
    NSURL *urlToOpen = [NSURL URLWithString:url];
    UIApplication *application = [UIApplication sharedApplication];

    if (@available(iOS 10.0, *)) {
        [application openURL:urlToOpen options:@{} completionHandler: nil];
    } else {
        [application openURL:urlToOpen];
    }
}

RCT_EXPORT_METHOD(close) {
    [ extContext completeRequestReturningItems: @[] completionHandler: nil ];
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

