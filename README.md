###TextDrawable 
This light-weight library provides images with letter/text like the Gmail app. It extends the `Drawable` class thus can be used with existing/custom/network `ImageView` classes. Also included is a [fluent interface](http://en.wikipedia.org/wiki/Fluent_interface) for creating drawables and a customizable `ColorGenerator`.

<p align="center"><img src ="https://github.com/amulyakhare/TextDrawable/blob/master/screens/screen1-material.png" width="350"/>
<img src ="https://github.com/amulyakhare/TextDrawable/blob/master/screens/screen2-material.png" width="350"/>
</p>

###How to use

#### Import with Gradle:

```groovy
repositories {
    // ...
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.afollestad:TextDrawable:4a4892e92c'
}
```

####1. Create simple tile:

<p align="center"><img src ="https://github.com/amulyakhare/TextDrawable/blob/master/screens/screen3.png"/>
</p>

```xml
<ImageView android:layout_width="60dp"
	       android:layout_height="60dp"
	       android:id="@+id/image_view"/>
```
**Note:** Specify width/height for the `ImageView` and the `drawable` will auto-scale to fit the size.
```java
TextDrawable drawable = TextDrawable.builder(this)
    // use buildRect(String, int) for literal color value
    .buildRectRes("A", R.color.material_red);

ImageView image = (ImageView) findViewById(R.id.image_view);
image.setImageDrawable(drawable);
```

####2. Create rounded corner or circular tiles:

<p align="center"><img src ="https://github.com/amulyakhare/TextDrawable/blob/master/screens/screen6.png"/>
</p>

```java
TextDrawable drawable1 = TextDrawable.builder()
    // use buildRoundRect(String, int, int) for literal color value and pixel size
    .buildRoundRectRes("A", R.color.material_red, R.dimen.radius_size); 

TextDrawable drawable2 = TextDrawable.builder()
    .buildRound("A", Color.RED);
```

####3. Add border:

<p align="center"><img src ="https://github.com/amulyakhare/TextDrawable/blob/master/screens/screen5.png"/>
</p>

```java
TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                    .withBorderRes(R.dimen.border_size)  // use withBorder(int) for literal pixel size
                .endConfig()
                .buildRoundRect("A", Color.RED, 10);
```

####4. Modify font style:

```java
TextDrawable drawable = TextDrawable.builder(this)
                .beginConfig()
	                .textColorRes(android.R.color.black)  // use textColor(int) for literal color value
                    .useFont(Typeface.DEFAULT)
                    .fontSizeRes(R.dimen.title_size)  // use fontSize(int) for literal pixel size
                    .bold()
                    .toUpperCase()
                .endConfig()
                .buildRect("a", Color.RED)
```

####5. Built-in color generator:

```java
ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
// generate random color
int color1 = generator.getRandomColor();
// generate color based on a key (same key returns the same color), useful for list/grid views
int color2 = generator.getColor("user@gmail.com")

// declare the builder object once.
TextDrawable.IBuilder builder = TextDrawable.builder(this)
				.beginConfig()
					.withBorderRes(R.dimen.border_size)  // use withBorder(int) for literal values
				.endConfig()
				.rect();

// reuse the builder specs to create multiple drawables
TextDrawable ic1 = builder.build("A", color1);
TextDrawable ic2 = builder.build("B", color2);
``` 

####6. Specify the width / height:

```xml
<ImageView android:layout_width="wrap_content"
	       android:layout_height="wrap_content"
	       android:id="@+id/image_view" />
```
**Note:**  The `ImageView` could use `wrap_content` width/height. You could set the width/height of the `drawable` using code.

```java
TextDrawable drawable = TextDrawable.builder(this)
				.beginConfig()
					.widthRes(R.dimen.thumbnail_size)  // use width(int) for literal values
					.heightRes(R.dimen.thumbnail_size)  // use height(int) for literal values
				.endConfig()
                .buildRectRes("A", R.color.material_red);  // use buildRect(String, int) for literal color value

ImageView image = (ImageView) findViewById(R.id.image_view);
image.setImageDrawable(drawable);
```

####7. Other features:

1. Mix-match with other drawables. Use it in conjunction with `LayerDrawable`, `InsetDrawable`, `AnimationDrawable`, `TransitionDrawable` etc.

2. Compatible with other views (not just `ImageView`). Use it as background drawable, compound drawable for `TextView`, `Button` etc.

3. Use multiple letters or `unicode` characters to create interesting tiles. 

<p align="center"><img src ="https://github.com/amulyakhare/TextDrawable/blob/master/screens/screen7.png" width="350"/></p>
