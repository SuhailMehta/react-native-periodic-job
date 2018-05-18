
# react-native-periodic-job

## Getting started

`$ npm install react-native-periodic-job --save`

### Mostly automatic installation

`$ react-native link react-native-periodic-job`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-periodic-job` and add `RNPeriodicJob.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNPeriodicJob.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNPeriodicJobPackage;` to the imports at the top of the file
  - Add `new RNPeriodicJobPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-periodic-job'
  	project(':react-native-periodic-job').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-periodic-job/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-periodic-job')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNPeriodicJob.sln` in `node_modules/react-native-periodic-job/windows/RNPeriodicJob.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Periodic.Job.RNPeriodicJob;` to the usings at the top of the file
  - Add `new RNPeriodicJobPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNPeriodicJob from 'react-native-periodic-job';

// TODO: What to do with the module?
RNPeriodicJob;
```
  