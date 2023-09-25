package net.luis.utils.io;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.*;

import java.util.Scanner;

/**
 *
 * @author Luis-St
 *
 */

@Blocking
@Deprecated(forRemoval = true)
public class ScannerUtils {
	
	public static int nextInt(@Nullable String output) {
		Scanner scanner = new Scanner(System.in);
		if (!StringUtils.isEmpty(output)) {
			System.out.println(output);
		}
		int value = scanner.nextInt();
		scanner.close();
		return value;
	}
	
	public static long nextLong(@Nullable String output) {
		Scanner scanner = new Scanner(System.in);
		if (!StringUtils.isEmpty(output)) {
			System.out.println(output);
		}
		long value = scanner.nextLong();
		scanner.close();
		return value;
	}
	
	public static double nextDouble(@Nullable String output) {
		Scanner scanner = new Scanner(System.in);
		if (!StringUtils.isEmpty(output)) {
			System.out.println(output);
		}
		double value = scanner.nextDouble();
		scanner.close();
		return value;
	}
	
	public static @NotNull String nextString(@Nullable String output) {
		Scanner scanner = new Scanner(System.in);
		if (!StringUtils.isEmpty(output)) {
			System.out.println(output);
		}
		String value = scanner.nextLine();
		scanner.close();
		return value;
	}
}
