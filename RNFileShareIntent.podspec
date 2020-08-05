
require 'json'

package = JSON.parse(File.read(File.join(File.dirname(__FILE__), "package.json")))

Pod::Spec.new do |s|

  s.name         = "RNFileShareIntent"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.license      = package['license']
  s.author       = { package["author"]["name"] => package["author"]["email"] }
  s.homepage     = package['homepage']
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/opendigitaleducation/react-native-file-share-intent.git", :tag => "master" }
  s.source_files = "ios/**/*.{h,m}"
  s.dependency "React"
end