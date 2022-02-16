public class CCBEA
{
	public static byte[] encrypt(byte[] data, byte[] key) { return process(data, key, mod(key.length)); }
	public static byte[] decrypt(byte[] data, byte[] key) { return process(data, key, -mod(key.length)); }

	private static int mod(int keyLength) { return keyLength % 2 == 0 ? 1 : -1; }

	private static byte[] process(byte[] data, byte[] key, int mod)
	{
		int sum = 0;
		for (int i = 0; i < key.length; ++i)
			sum += key[i] * (i + 1);
		
		for (int i = 0; i < key.length; ++i)
			key[i] *= sum * mod;

		for (int i = 0; i < data.length; ++i)
			data[i] += key[i % key.length];

		return data;
	}
}
