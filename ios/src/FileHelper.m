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

+ (NSString *)MIMETypeFromURL:(NSURL *)localFileURL
{
    NSString *extension = [localFileURL pathExtension];
    NSString *exportedUTI = (__bridge_transfer NSString *)UTTypeCreatePreferredIdentifierForTag(kUTTagClassFilenameExtension, (__bridge CFStringRef)extension, NULL);
    NSString *mimeType = (__bridge_transfer NSString *)UTTypeCopyPreferredTagWithClass((__bridge CFStringRef)exportedUTI, kUTTagClassMIMEType);
    return mimeType;
}

+ (NSString *)fileNameFromPath:(NSString *)filePath
{
    return [filePath lastPathComponent];
}

+ (NSDictionary *)getFileData:(NSURL *)url
{
    NSDictionary *fileData = @{
                               @"mime": [FileHelper MIMETypeFromURL:url],
                               @"name": [FileHelper fileNameFromPath: [url absoluteString]],
                               @"path": [url absoluteString]
                               };
    
    return fileData;
}

@end
