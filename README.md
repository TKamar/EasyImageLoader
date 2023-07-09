# ImageLoader Library Documentation

<p align="center">
  <img src="https://github.com/TKamar/EasyImageLoader/blob/master/EasyImageLoader/src/main/res/raw/EasyImageLoader.png?raw=true">
</p>

The ImageLoader library simplifies the process of loading and caching images from remote sources (such as URLs) in Android apps. It handles image downloading, caching, memory management, and provides support for placeholders and image transformations. This library is designed to be easy to use and can be integrated into any Android project.

## Features

- Image loading from remote sources (URLs)
- Memory caching to improve performance and reduce network requests
- Placeholder support to display a temporary image while the actual image is being loaded
- Support for image transformations (not implemented in the provided code sample)
- Thread management for efficient image loading and UI updates

## Installation

To use the ImageLoader library in your Android project, follow these steps:

1. Copy the `ImageLoader` class into your project's source code.
2. Make sure to replace the package name in the `import` statements if necessary.
3. Build and run your project.

## Usage

### Initializing ImageLoader

Before you can start loading images, you need to initialize the `ImageLoader` instance in your application class or activity. Typically, this should be done in the `onCreate` method of your Application class or the `onCreate` method of your activity.

```
ImageLoader imageLoader = ImageLoader.getInstance(getApplicationContext());
```

### Loading Images

To load an image into an `ImageView`, call the `loadImage` method on the `ImageLoader` instance. Provide the URL of the image and the `ImageView` where you want to display the image.

```
String imageUrl = "https://example.com/image.jpg";
ImageView imageView = findViewById(R.id.imageView);
imageLoader.loadImage(imageUrl, imageView);
```

### Advanced Usage

#### Placeholder Image

You can set a placeholder image to be displayed while the actual image is being loaded. This provides a better user experience by giving visual feedback that the image is loading.

```
imageLoader.loadImage(imageUrl, imageView, R.drawable.placeholder);
```

In the example above, `R.drawable.placeholder` refers to the resource ID of the placeholder image.

#### Image Transformations

The library supports applying transformations to the loaded images, such as resizing, cropping, or applying filters. However, the provided code sample does not implement this feature. You can extend the library and modify the `ImageLoaderTask` class to include image transformation logic.

#### Caching

The library automatically caches the downloaded images in memory using an LruCache. This improves performance by reducing the number of network requests. The cache size is automatically calculated based on the device's available memory.

#### Memory Management

The library handles memory management by automatically evicting the least recently used bitmaps from the memory cache when the cache size limit is reached. This ensures efficient memory usage and prevents out-of-memory errors.

## Sample App

For a complete example of how to use the ImageLoader library, you can refer to the following code snippet:

```
public class MainActivity extends AppCompatActivity {
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageLoader = ImageLoader.getInstance(getApplicationContext());

        String imageUrl = "https://example.com/image.jpg";
        ImageView imageView = findViewById(R.id.imageView);
        imageLoader.loadImage(imageUrl, imageView, R.drawable.placeholder);
    }
}
```

Make sure to replace `R.layout.activity_main` with your actual layout file and `R.id.imageView` with the ID of your ImageView.

## How to Use

1. Add it in your root build.gradle at the end of repositories:
   ```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
   ```
2. Add the dependency:
   ```
	dependencies {
	        implementation 'com.github.TKamar:EasyImageLoader:v1.0'
	}
   ```

## Conclusion

The ImageLoader library provides a convenient and efficient way to load and cache images in Android apps. It simplifies the process of image handling, improves performance, and enhances the user experience. By following the installation and usage instructions provided in this documentation, you can easily integrate the library into your Android project.

Note: This is a simplified implementation of an image loading and caching library. In real-world scenarios, you may need to handle additional features like disk caching, image transformations, error handling, and cancellation of image loading tasks. Feel free to extend and modify the library according to your specific requirements.
