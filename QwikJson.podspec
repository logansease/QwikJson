#
# Be sure to run `pod lib lint QwikJson.podspec' to ensure this is a
# valid spec before submitting.
#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
s.name             = "QwikJson"
s.version          = "1.1.8"
s.summary          = "A deep serialization and deserialization base object."

# This description is used to generate tags and improve search results.
#   * Think: What does it do? Why did you write it? What is the focus?
#   * Try to keep it short, snappy and to the point.
#   * Write the description between the DESC delimiters below.
#   * Finally, don't worry about the indent, CocoaPods strips it!
s.description      = <<-DESC
In our ReSTful API world, we are constantly passing JSON objects to our api and receiving them back. Constantly serializating these objects to and from json string and dictionaries can be cumbersome and can make your model classes and data services start to fill up with boiler plate parsing code.

To solve this, I introduce QwikJson. An amazingly powerful and simple library for serializing and deserializing json objects.

Simply have your model classes extend the QwikJson class and the world shall become your oyster.

QwikJson makes converting objects to dictionaries and arrays of dictionaries a breeze. It includes support for nested model objects, nested array model objects, multiple date serializers, easily storing and loading objects from user defaults and converting your array arrays and dictionaries to json Strings and vice versa.

DESC

s.homepage         = "https://github.com/logansease/QwikJson"
s.license          = 'MIT'
s.author           = { "Logan Sease" => "lsease@gmail.com" }
s.source           = { :git => "https://github.com/logansease/QwikJson.git", :tag => s.version.to_s }

s.tvos.deployment_target = '12.0'
s.ios.deployment_target = '13.0'
s.osx.deployment_target = '10.13'
s.watchos.deployment_target = '4.0'
# s.visionos.deployment_target = '1.2'

s.requires_arc = true

s.source_files = 'Pod/Classes/**/*', 'Pod/Classes/include/**/*'

# s.public_header_files = 'Pod/Classes/**/*.h'
 s.frameworks = 'CoreData'
# s.dependency 'AFNetworking', '~> 2.3'
end
