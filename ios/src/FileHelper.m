//
//  FileHelper.m
//  RNFileShareIntent
//
//  Created by Valentyn Halkin on 8/19/19.
//  Copyright © 2019 Facebook. All rights reserved.
//
#import <MobileCoreServices/MobileCoreServices.h>
#import "FileHelper.h"


@implementation FileHelper

+ (NSString *)fileNameFromPath:(NSString *)filePath
{
    return [filePath lastPathComponent];
}

+ (void) getItem:(NSItemProvider *)item completionHandler:(NSItemProviderCompletionHandler)completionHandler {

    if ([item hasItemConformingToTypeIdentifier:(NSString *)kUTTypeData]) {
        [item loadItemForTypeIdentifier:(NSString *)kUTTypeData options:nil completionHandler:completionHandler];
    }
}

@end
