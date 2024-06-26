// swift-tools-version:5.6
import PackageDescription

let package = Package(
    name: "QwikJson",
    platforms: [
        .macOS(.v10_14), .iOS(.v13), .tvOS(.v13)
    ],
    products: [
        // Products define the executables and libraries a package produces, and make them visible to other packages.
        .library(
            name: "QwikJson",
            targets: ["QwikJson"])
    ],
    dependencies: [],
    targets: [
        .target(
            name: "QwikJson",
            path: "Pod/Classes"
        )
    ]
)
