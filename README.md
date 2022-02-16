# CCBEA :lock: :key:
#### Caesar Cipher Based Encryption Algorithm
## Example of usage :ox:
```java
import java.util.Base64;
import java.util.Arrays;

public class Main
{
  public static void main(String[] args)
  {
    String data = "Hello, world!";
    String key = "Qwerty123";

    byte[] enc = CCBEA.encrypt(data.getBytes(), key.getBytes());
    byte[] dec = CCBEA.decrypt(Arrays.copyOf(enc, enc.length), key.getBytes());
    
    System.out.println(data); 
    System.out.println(Base64.getEncoder().encodeToString(enc));
    System.out.println(new String(dec));
  }
}
```
```
$ javac Main.java && java Main
Hello, world!
CgGq/HeYrpdlBkZc9Q==
Hello, world!
```

## How it works? :eyes:
To better understand how the algorithm works, let's define the essence of encryption:

> These are some reversible transformations of the original data using a secret key, as a result of which data is obtained that has nothing to do with the original (which ensures the security of information).  

The __CCBEA__ algorithm can be divided into 3 main stages. Let's look at each separately.

### Stage I ~ «Key sum calculation»
```java
int sum = 0;
for (int i = 0; i < key.length; ++i)
  sum += Math.abs(key[i] * (i + 1));
```
At this stage, we must calculate the sum of the key, that is, calculate the sum of all elements of the array.

Also, adding the next element to the __sum__ variable, we must multiply this element by its `index in the array` + `1` (**+1** because we don't want to multiply the first byte by zero and devalue it completely).  

So why do we need to calculate it? The exact answer lies in the depths of the cryptographic ocean, but in simple terms:
> Each individual byte of the key must have an effect on all the bytes of the key.

This is necessary for the «butterfly effect», when changing even one byte of the key completely changes the entire key and, as a result, the entire cipher (this moment is very similar to hashing).

### Stage II ~ «Key modification»
```java
for (int i = 0; i < key.length; ++i)
  key[i] *= mod * sum;
```
Here we multiply each byte of the key by __mod__, which determines the direction in which the data bytes will be shifted.  

We also multiply each byte of the key by the sum calculated in the previous step, which provides the «butterfly effect».

So, to demonstrate this effect, below I have encrypted the data with two keys that differ by one byte. You can see how much one byte affects the encryption result.
<details>
  <summary>spoiler</summary>
  
  ```java
  import java.util.Base64;
  import java.util.Arrays;

  public class Main
  { 
    public static void main(String[] args)
    {   
      String data = "Hello, world!";
      String key1 = "Qwerty123";
      String key2 = "Qwerty124";

      byte[] enc1 = CCBEA.encrypt(data.getBytes(), key1.getBytes());
      byte[] enc2 = CCBEA.encrypt(data.getBytes(), key2.getBytes());

      System.out.println(key1 + ": " + Arrays.toString(enc1));
      System.out.println(key2 + ": " + Arrays.toString(enc2));
    }   
  }
  ```
  ```
  $ javac Main.java && java Main
  Qwerty123: [-90, 119, -94, -120, -121, 58, -66, 19, 9, -48, 126, -102, 61]
  Qwerty124: [-51, 72, 21, -122, 115, -7, 5, 81, 51, -9, 79, 13, 59]
  ```
</details>

### Stage III ~ «Data transformation»
```java
for (int i = 0; i < data.length; ++i)
  data[i] += key[i % key.length];
```
This is the final and key stage, the task of which is to change each byte of data. Here we do this by simply summing each data byte with the key bytes.  

To determine which key byte to use in a particular iteration, we resort to simple modulo division operation:  

`current data byte index` **%** `key length`  

Thus we evenly apply the key to the entire range of data.

## Conclusion :beer:

In this algorithm, the complexity of encryption depends entirely on the complexity of the key.  

#### «The more bytes in the key, the more diverse they are, the more difficult it is to break the cipher using the brute force»
