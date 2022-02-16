public class CCBEA
{
	public static byte[] encrypt(byte[] data, byte[] key) { return process(data, key, (byte) 1); }
	public static byte[] decrypt(byte[] data, byte[] key) { return process(data, key, (byte) -1); }

	private static byte[] process(byte[] data, byte[] key, byte mod)
	{
		int sum = 0;
		for (int i = 0; i < key.length; ++i)
			sum += key[i];
		
		for (int i = 0; i < key.length; ++i)
			key[i] *= sum * mod;

		for (int i = 0; i < data.length; ++i)
			data[i] += key[i % key.length] * (i + 1);

		return data;
	}
}
