package net.luis.utils.util;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author Luis-st
 *
 */

public class Result<T> implements Supplier<Either<T, String>> {
	
	private final Either<T, String> either;

	private Result(Either<T, String> either) {
		this.either = either;
	}
	
	public static <T> Result<T> success(T value) {
		return new Result<>(Either.left(value));
	}
	
	public static <T> Result<T> error(String error) {
		return new Result<>(Either.right(error));
	}

	@Override
	public Either<T, String> get() {
		return this.either;
	}
	
	public boolean isSuccess() {
		return this.either.isLeft();
	}
	
	public Optional<T> result() {
		return this.either.left();
	}
	
	public T orThrow() {
		return this.orThrow((error) -> {
			
		});
	}
	
	public T orThrow(Consumer<String> onError) {
		if (this.either.isLeft()) {
			return this.either.leftOrThrow();
		}
		onError.accept(this.either.rightOrThrow());
		throw new NoSuchElementException("No value present");
	}
	
	public boolean isError() {
		return this.either.isLeft();
	}
	
	public Optional<String> error() {
		return this.either.right();
	}
	
}
