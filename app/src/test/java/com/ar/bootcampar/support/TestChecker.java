package com.ar.bootcampar.support;

import static org.junit.Assert.assertTrue;

import com.ar.bootcampar.model.Database;
import com.ar.bootcampar.model.IDatabase;

import java.util.function.Function;
import java.util.function.Supplier;

public class TestChecker {
    public static void probarCerrarCursorAlBuscarConExito(Function<IDatabase, Boolean> assertion, Supplier<CursorWrapperStub> cursorCreator) {
        CursorWrapperStub cursorStub = cursorCreator.get();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        assertion.apply(sut);

        assertTrue(cursorStub.getCloseCalled());
    }

    public static void probarCerrarCursorAlBuscarSinExito(int count, Function<IDatabase, Boolean> assertion) {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(count)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        try {
            assertion.apply(sut);
        } catch (Exception ex) {
        }

        assertTrue(cursorStub.getCloseCalled());
    }

    public static void probarCerrarBaseDeDatosAlBuscarConExito(Function<IDatabase, Boolean> assertion, Supplier<CursorWrapperStub> cursorCreator) {
        CursorWrapperStub cursorStub = cursorCreator.get();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        assertion.apply(sut);

        assertTrue(spy.getCloseCalled());
    }

    public static void probarCerrarBaseDeDatosAlBuscarSinExito(int count, Function<IDatabase, Boolean> assertion) {
        CursorWrapperStub cursorStub = new CursorWrapperStub.Builder()
                .conCountRetornando(count)
                .build();
        SqliteDatabaseWrapperSpy spy = new SqliteDatabaseWrapperSpy.Builder()
                .conQueryRetornando(cursorStub)
                .build();

        Database sut = new TestableDatabase(spy);
        try {
            assertion.apply(sut);
        } catch (Exception ex) {
        }

        assertTrue(spy.getCloseCalled());
    }
}