package com.jnape.palatable.lambda.comonad;

import com.jnape.palatable.lambda.comonad.Comonad;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.IntStream;

public final class PointedList<A> implements Comonad<A, PointedList> {
    private int cursor;
    private ArrayList<A> list;

    private PointedList(int cursor, ArrayList<A> items) {
        this.cursor = cursor;
        this.list = items;
    }

    public PointedList(ArrayList<A> left, A middle, ArrayList<A> right) {
        this.cursor = left.size() + 1;
        this.list = new ArrayList<A>();
        left.forEach(x -> list.add(x));
        this.list.add(middle);
        right.forEach(x -> list.add(x));
    }

    @Override
    public final A extract() {
        return list.get(cursor);
    }

    public final PointedList<A> cursorAt(int i) {
        return new PointedList<A>(i, list);
    }

    @Override
    public final <B> PointedList<B> extend(Function<? super Comonad<A, PointedList>, ? extends B> f) {
        ArrayList<B> newList = new ArrayList<B>();
        IntStream.range(0, list.size()).forEach(i -> newList.add(f.apply(cursorAt(i))));
        return new PointedList<B>(cursor, newList);
    }
}
