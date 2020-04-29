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

+ (NSDictionary *)getFileData:(NSURL *)url
{
    NSDictionary *fileData = @{
                               @"mime": [FileHelper getMimeTypeFromPath:url],
                               @"name": [FileHelper getFileNameFromPath:url],
                               @"uri": url.absoluteString
                               };

    return fileData;
}

+ (NSString *) getFileNameFromPath:(NSURL *)filePath
{
    return [filePath lastPathComponent];
}

+ (NSString *) getMimeTypeFromPath:(NSURL *)filePath
{
    CFStringRef fileUTI = UTTypeCreatePreferredIdentifierForTag(kUTTagClassFilenameExtension, (__bridge CFStringRef _Nonnull)([filePath pathExtension]), nil);
    CFStringRef fileMimeType = UTTypeCopyPreferredTagWithClass(fileUTI, kUTTagClassMIMEType);
    return (__bridge NSString *)(fileMimeType);
}

+ (void) getFileUrl:(NSItemProvider *)item completionHandler:(NSItemProviderCompletionHandler)completionHandler {

    if ([item hasItemConformingToTypeIdentifier:(NSString *)kUTTypeData]) {
        [item loadItemForTypeIdentifier:(NSString *)kUTTypeData options:nil completionHandler:completionHandler];
    }
}

@end
