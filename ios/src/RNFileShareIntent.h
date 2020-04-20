#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RNFileShareIntent : NSObject<RCTBridgeModule>

+(NSItemProvider *) extractDataFromContext:(NSExtensionContext*) context;

+(void) setContext: (NSExtensionContext*) context;

@end
