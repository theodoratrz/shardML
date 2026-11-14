package io.shardml.parser.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSQLLexer extends Lexer {
    public static final int CREATE=10;
    public static final int DECIMAL=6;
    public static final int TEXT=16;
    public static final int RULE_STRING=29;
    public static final int INT=18;
    public static final int RULE_SL_COMMENT=32;
    public static final int TABLE=13;
    public static final int Comma=24;
    public static final int LeftParenthesis=22;
    public static final int TIMESTAMP=4;
    public static final int UUID=17;
    public static final int EOF=-1;
    public static final int IF=21;
    public static final int FullStop=25;
    public static final int NULL=15;
    public static final int EXISTS=11;
    public static final int RULE_ID=27;
    public static final int RULE_WS=33;
    public static final int RULE_QUOTED_ID=28;
    public static final int UNIQUE=12;
    public static final int RightParenthesis=23;
    public static final int PRIMARY=7;
    public static final int BIGINT=9;
    public static final int RULE_ANY_OTHER=34;
    public static final int BOOLEAN=5;
    public static final int Semicolon=26;
    public static final int DATE=14;
    public static final int NOT=20;
    public static final int RULE_INT=30;
    public static final int VARCHAR=8;
    public static final int RULE_ML_COMMENT=31;
    public static final int KEY=19;

    // delegates
    // delegators

    public InternalSQLLexer() {;} 
    public InternalSQLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalSQLLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalSQLLexer.g"; }

    // $ANTLR start "TIMESTAMP"
    public final void mTIMESTAMP() throws RecognitionException {
        try {
            int _type = TIMESTAMP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:14:11: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'P' | 'p' ) )
            // InternalSQLLexer.g:14:13: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'P' | 'p' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMESTAMP"

    // $ANTLR start "BOOLEAN"
    public final void mBOOLEAN() throws RecognitionException {
        try {
            int _type = BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:16:9: ( ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'N' | 'n' ) )
            // InternalSQLLexer.g:16:11: ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOOLEAN"

    // $ANTLR start "DECIMAL"
    public final void mDECIMAL() throws RecognitionException {
        try {
            int _type = DECIMAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:18:9: ( ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'A' | 'a' ) ( 'L' | 'l' ) )
            // InternalSQLLexer.g:18:11: ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'A' | 'a' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DECIMAL"

    // $ANTLR start "PRIMARY"
    public final void mPRIMARY() throws RecognitionException {
        try {
            int _type = PRIMARY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:20:9: ( ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'Y' | 'y' ) )
            // InternalSQLLexer.g:20:11: ( 'P' | 'p' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRIMARY"

    // $ANTLR start "VARCHAR"
    public final void mVARCHAR() throws RecognitionException {
        try {
            int _type = VARCHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:22:9: ( ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // InternalSQLLexer.g:22:11: ( 'V' | 'v' ) ( 'A' | 'a' ) ( 'R' | 'r' ) ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VARCHAR"

    // $ANTLR start "BIGINT"
    public final void mBIGINT() throws RecognitionException {
        try {
            int _type = BIGINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:24:8: ( ( 'B' | 'b' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSQLLexer.g:24:10: ( 'B' | 'b' ) ( 'I' | 'i' ) ( 'G' | 'g' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BIGINT"

    // $ANTLR start "CREATE"
    public final void mCREATE() throws RecognitionException {
        try {
            int _type = CREATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:26:8: ( ( 'C' | 'c' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSQLLexer.g:26:10: ( 'C' | 'c' ) ( 'R' | 'r' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CREATE"

    // $ANTLR start "EXISTS"
    public final void mEXISTS() throws RecognitionException {
        try {
            int _type = EXISTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:28:8: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'S' | 's' ) )
            // InternalSQLLexer.g:28:10: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXISTS"

    // $ANTLR start "UNIQUE"
    public final void mUNIQUE() throws RecognitionException {
        try {
            int _type = UNIQUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:30:8: ( ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'Q' | 'q' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // InternalSQLLexer.g:30:10: ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'Q' | 'q' ) ( 'U' | 'u' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Q'||input.LA(1)=='q' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNIQUE"

    // $ANTLR start "TABLE"
    public final void mTABLE() throws RecognitionException {
        try {
            int _type = TABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:32:7: ( ( 'T' | 't' ) ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // InternalSQLLexer.g:32:9: ( 'T' | 't' ) ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TABLE"

    // $ANTLR start "DATE"
    public final void mDATE() throws RecognitionException {
        try {
            int _type = DATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:34:6: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // InternalSQLLexer.g:34:8: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DATE"

    // $ANTLR start "NULL"
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:36:6: ( ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' ) )
            // InternalSQLLexer.g:36:8: ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NULL"

    // $ANTLR start "TEXT"
    public final void mTEXT() throws RecognitionException {
        try {
            int _type = TEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:38:6: ( ( 'T' | 't' ) ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' ) )
            // InternalSQLLexer.g:38:8: ( 'T' | 't' ) ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TEXT"

    // $ANTLR start "UUID"
    public final void mUUID() throws RecognitionException {
        try {
            int _type = UUID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:40:6: ( ( 'U' | 'u' ) ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'D' | 'd' ) )
            // InternalSQLLexer.g:40:8: ( 'U' | 'u' ) ( 'U' | 'u' ) ( 'I' | 'i' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UUID"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:42:5: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // InternalSQLLexer.g:42:7: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "KEY"
    public final void mKEY() throws RecognitionException {
        try {
            int _type = KEY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:44:5: ( ( 'K' | 'k' ) ( 'E' | 'e' ) ( 'Y' | 'y' ) )
            // InternalSQLLexer.g:44:7: ( 'K' | 'k' ) ( 'E' | 'e' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "KEY"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:46:5: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' ) )
            // InternalSQLLexer.g:46:7: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:48:4: ( ( 'I' | 'i' ) ( 'F' | 'f' ) )
            // InternalSQLLexer.g:48:6: ( 'I' | 'i' ) ( 'F' | 'f' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "LeftParenthesis"
    public final void mLeftParenthesis() throws RecognitionException {
        try {
            int _type = LeftParenthesis;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:50:17: ( '(' )
            // InternalSQLLexer.g:50:19: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LeftParenthesis"

    // $ANTLR start "RightParenthesis"
    public final void mRightParenthesis() throws RecognitionException {
        try {
            int _type = RightParenthesis;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:52:18: ( ')' )
            // InternalSQLLexer.g:52:20: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RightParenthesis"

    // $ANTLR start "Comma"
    public final void mComma() throws RecognitionException {
        try {
            int _type = Comma;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:54:7: ( ',' )
            // InternalSQLLexer.g:54:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Comma"

    // $ANTLR start "FullStop"
    public final void mFullStop() throws RecognitionException {
        try {
            int _type = FullStop;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:56:10: ( '.' )
            // InternalSQLLexer.g:56:12: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FullStop"

    // $ANTLR start "Semicolon"
    public final void mSemicolon() throws RecognitionException {
        try {
            int _type = Semicolon;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:58:11: ( ';' )
            // InternalSQLLexer.g:58:13: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Semicolon"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:60:9: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '$' )* )
            // InternalSQLLexer.g:60:11: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '$' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // InternalSQLLexer.g:60:35: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '$' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='$'||(LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSQLLexer.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_QUOTED_ID"
    public final void mRULE_QUOTED_ID() throws RecognitionException {
        try {
            int _type = RULE_QUOTED_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:62:16: ( '\"' ( '\"\"' | ~ ( '\"' ) )* '\"' )
            // InternalSQLLexer.g:62:18: '\"' ( '\"\"' | ~ ( '\"' ) )* '\"'
            {
            match('\"'); 
            // InternalSQLLexer.g:62:22: ( '\"\"' | ~ ( '\"' ) )*
            loop2:
            do {
                int alt2=3;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\"') ) {
                    int LA2_1 = input.LA(2);

                    if ( (LA2_1=='\"') ) {
                        alt2=1;
                    }


                }
                else if ( ((LA2_0>='\u0000' && LA2_0<='!')||(LA2_0>='#' && LA2_0<='\uFFFF')) ) {
                    alt2=2;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSQLLexer.g:62:23: '\"\"'
            	    {
            	    match("\"\""); 


            	    }
            	    break;
            	case 2 :
            	    // InternalSQLLexer.g:62:28: ~ ( '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_QUOTED_ID"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:64:13: ( '\\'' ( '\\'\\'' | ~ ( '\\'' ) )* '\\'' )
            // InternalSQLLexer.g:64:15: '\\'' ( '\\'\\'' | ~ ( '\\'' ) )* '\\''
            {
            match('\''); 
            // InternalSQLLexer.g:64:20: ( '\\'\\'' | ~ ( '\\'' ) )*
            loop3:
            do {
                int alt3=3;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='\'') ) {
                    int LA3_1 = input.LA(2);

                    if ( (LA3_1=='\'') ) {
                        alt3=1;
                    }


                }
                else if ( ((LA3_0>='\u0000' && LA3_0<='&')||(LA3_0>='(' && LA3_0<='\uFFFF')) ) {
                    alt3=2;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalSQLLexer.g:64:21: '\\'\\''
            	    {
            	    match("''"); 


            	    }
            	    break;
            	case 2 :
            	    // InternalSQLLexer.g:64:28: ~ ( '\\'' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:66:10: ( ( '0' .. '9' )+ )
            // InternalSQLLexer.g:66:12: ( '0' .. '9' )+
            {
            // InternalSQLLexer.g:66:12: ( '0' .. '9' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalSQLLexer.g:66:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:68:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalSQLLexer.g:68:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalSQLLexer.g:68:24: ( options {greedy=false; } : . )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='*') ) {
                    int LA5_1 = input.LA(2);

                    if ( (LA5_1=='/') ) {
                        alt5=2;
                    }
                    else if ( ((LA5_1>='\u0000' && LA5_1<='.')||(LA5_1>='0' && LA5_1<='\uFFFF')) ) {
                        alt5=1;
                    }


                }
                else if ( ((LA5_0>='\u0000' && LA5_0<=')')||(LA5_0>='+' && LA5_0<='\uFFFF')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalSQLLexer.g:68:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:70:17: ( '--' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalSQLLexer.g:70:19: '--' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("--"); 

            // InternalSQLLexer.g:70:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='\u0000' && LA6_0<='\t')||(LA6_0>='\u000B' && LA6_0<='\f')||(LA6_0>='\u000E' && LA6_0<='\uFFFF')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalSQLLexer.g:70:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            // InternalSQLLexer.g:70:40: ( ( '\\r' )? '\\n' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='\n'||LA8_0=='\r') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSQLLexer.g:70:41: ( '\\r' )? '\\n'
                    {
                    // InternalSQLLexer.g:70:41: ( '\\r' )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='\r') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // InternalSQLLexer.g:70:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:72:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalSQLLexer.g:72:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalSQLLexer.g:72:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='\t' && LA9_0<='\n')||LA9_0=='\r'||LA9_0==' ') ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalSQLLexer.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalSQLLexer.g:74:16: ( . )
            // InternalSQLLexer.g:74:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalSQLLexer.g:1:8: ( TIMESTAMP | BOOLEAN | DECIMAL | PRIMARY | VARCHAR | BIGINT | CREATE | EXISTS | UNIQUE | TABLE | DATE | NULL | TEXT | UUID | INT | KEY | NOT | IF | LeftParenthesis | RightParenthesis | Comma | FullStop | Semicolon | RULE_ID | RULE_QUOTED_ID | RULE_STRING | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt10=31;
        alt10 = dfa10.predict(input);
        switch (alt10) {
            case 1 :
                // InternalSQLLexer.g:1:10: TIMESTAMP
                {
                mTIMESTAMP(); 

                }
                break;
            case 2 :
                // InternalSQLLexer.g:1:20: BOOLEAN
                {
                mBOOLEAN(); 

                }
                break;
            case 3 :
                // InternalSQLLexer.g:1:28: DECIMAL
                {
                mDECIMAL(); 

                }
                break;
            case 4 :
                // InternalSQLLexer.g:1:36: PRIMARY
                {
                mPRIMARY(); 

                }
                break;
            case 5 :
                // InternalSQLLexer.g:1:44: VARCHAR
                {
                mVARCHAR(); 

                }
                break;
            case 6 :
                // InternalSQLLexer.g:1:52: BIGINT
                {
                mBIGINT(); 

                }
                break;
            case 7 :
                // InternalSQLLexer.g:1:59: CREATE
                {
                mCREATE(); 

                }
                break;
            case 8 :
                // InternalSQLLexer.g:1:66: EXISTS
                {
                mEXISTS(); 

                }
                break;
            case 9 :
                // InternalSQLLexer.g:1:73: UNIQUE
                {
                mUNIQUE(); 

                }
                break;
            case 10 :
                // InternalSQLLexer.g:1:80: TABLE
                {
                mTABLE(); 

                }
                break;
            case 11 :
                // InternalSQLLexer.g:1:86: DATE
                {
                mDATE(); 

                }
                break;
            case 12 :
                // InternalSQLLexer.g:1:91: NULL
                {
                mNULL(); 

                }
                break;
            case 13 :
                // InternalSQLLexer.g:1:96: TEXT
                {
                mTEXT(); 

                }
                break;
            case 14 :
                // InternalSQLLexer.g:1:101: UUID
                {
                mUUID(); 

                }
                break;
            case 15 :
                // InternalSQLLexer.g:1:106: INT
                {
                mINT(); 

                }
                break;
            case 16 :
                // InternalSQLLexer.g:1:110: KEY
                {
                mKEY(); 

                }
                break;
            case 17 :
                // InternalSQLLexer.g:1:114: NOT
                {
                mNOT(); 

                }
                break;
            case 18 :
                // InternalSQLLexer.g:1:118: IF
                {
                mIF(); 

                }
                break;
            case 19 :
                // InternalSQLLexer.g:1:121: LeftParenthesis
                {
                mLeftParenthesis(); 

                }
                break;
            case 20 :
                // InternalSQLLexer.g:1:137: RightParenthesis
                {
                mRightParenthesis(); 

                }
                break;
            case 21 :
                // InternalSQLLexer.g:1:154: Comma
                {
                mComma(); 

                }
                break;
            case 22 :
                // InternalSQLLexer.g:1:160: FullStop
                {
                mFullStop(); 

                }
                break;
            case 23 :
                // InternalSQLLexer.g:1:169: Semicolon
                {
                mSemicolon(); 

                }
                break;
            case 24 :
                // InternalSQLLexer.g:1:179: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 25 :
                // InternalSQLLexer.g:1:187: RULE_QUOTED_ID
                {
                mRULE_QUOTED_ID(); 

                }
                break;
            case 26 :
                // InternalSQLLexer.g:1:202: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 27 :
                // InternalSQLLexer.g:1:214: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 28 :
                // InternalSQLLexer.g:1:223: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 29 :
                // InternalSQLLexer.g:1:239: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 30 :
                // InternalSQLLexer.g:1:255: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 31 :
                // InternalSQLLexer.g:1:263: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS =
        "\1\uffff\13\34\6\uffff\2\30\1\uffff\2\30\2\uffff\3\34\1\uffff\15\34\1\107\1\34\13\uffff\16\34\1\127\1\130\1\uffff\1\131\2\34\1\134\3\34\1\140\5\34\1\146\1\147\3\uffff\1\34\1\151\1\uffff\3\34\1\uffff\5\34\2\uffff\1\34\1\uffff\1\34\1\164\3\34\1\170\1\171\1\172\1\34\1\174\1\uffff\1\175\1\176\1\177\3\uffff\1\34\4\uffff\1\u0081\1\uffff";
    static final String DFA10_eofS =
        "\u0082\uffff";
    static final String DFA10_minS =
        "\1\0\1\101\1\111\1\101\1\122\1\101\1\122\1\130\1\116\1\117\1\106\1\105\6\uffff\2\0\1\uffff\1\52\1\55\2\uffff\1\115\1\102\1\130\1\uffff\1\117\1\107\1\103\1\124\1\111\1\122\1\105\3\111\1\114\2\124\1\44\1\131\13\uffff\1\105\1\114\1\124\1\114\2\111\1\105\1\115\1\103\1\101\1\123\1\121\1\104\1\114\2\44\1\uffff\1\44\1\123\1\105\1\44\1\105\1\116\1\115\1\44\1\101\1\110\2\124\1\125\2\44\3\uffff\1\124\1\44\1\uffff\1\101\1\124\1\101\1\uffff\1\122\1\101\1\105\1\123\1\105\2\uffff\1\101\1\uffff\1\116\1\44\1\114\1\131\1\122\3\44\1\115\1\44\1\uffff\3\44\3\uffff\1\120\4\uffff\1\44\1\uffff";
    static final String DFA10_maxS =
        "\1\uffff\1\151\1\157\1\145\1\162\1\141\1\162\1\170\2\165\1\156\1\145\6\uffff\2\uffff\1\uffff\1\52\1\55\2\uffff\1\155\1\142\1\170\1\uffff\1\157\1\147\1\143\1\164\1\151\1\162\1\145\3\151\1\154\2\164\1\172\1\171\13\uffff\1\145\1\154\1\164\1\154\2\151\1\145\1\155\1\143\1\141\1\163\1\161\1\144\1\154\2\172\1\uffff\1\172\1\163\1\145\1\172\1\145\1\156\1\155\1\172\1\141\1\150\2\164\1\165\2\172\3\uffff\1\164\1\172\1\uffff\1\141\1\164\1\141\1\uffff\1\162\1\141\1\145\1\163\1\145\2\uffff\1\141\1\uffff\1\156\1\172\1\154\1\171\1\162\3\172\1\155\1\172\1\uffff\3\172\3\uffff\1\160\4\uffff\1\172\1\uffff";
    static final String DFA10_acceptS =
        "\14\uffff\1\23\1\24\1\25\1\26\1\27\1\30\2\uffff\1\33\2\uffff\1\36\1\37\3\uffff\1\30\17\uffff\1\23\1\24\1\25\1\26\1\27\1\31\1\32\1\33\1\34\1\35\1\36\20\uffff\1\22\17\uffff\1\21\1\17\1\20\2\uffff\1\15\3\uffff\1\13\5\uffff\1\16\1\14\1\uffff\1\12\12\uffff\1\6\3\uffff\1\7\1\10\1\11\1\uffff\1\2\1\3\1\4\1\5\1\uffff\1\1";
    static final String DFA10_specialS =
        "\1\1\21\uffff\1\0\1\2\156\uffff}>";
    static final String[] DFA10_transitionS = {
            "\11\30\2\27\2\30\1\27\22\30\1\27\1\30\1\22\4\30\1\23\1\14\1\15\2\30\1\16\1\26\1\17\1\25\12\24\1\30\1\20\5\30\1\21\1\2\1\6\1\3\1\7\3\21\1\12\1\21\1\13\2\21\1\11\1\21\1\4\3\21\1\1\1\10\1\5\4\21\4\30\1\21\1\30\1\21\1\2\1\6\1\3\1\7\3\21\1\12\1\21\1\13\2\21\1\11\1\21\1\4\3\21\1\1\1\10\1\5\4\21\uff85\30",
            "\1\32\3\uffff\1\33\3\uffff\1\31\27\uffff\1\32\3\uffff\1\33\3\uffff\1\31",
            "\1\36\5\uffff\1\35\31\uffff\1\36\5\uffff\1\35",
            "\1\40\3\uffff\1\37\33\uffff\1\40\3\uffff\1\37",
            "\1\41\37\uffff\1\41",
            "\1\42\37\uffff\1\42",
            "\1\43\37\uffff\1\43",
            "\1\44\37\uffff\1\44",
            "\1\45\6\uffff\1\46\30\uffff\1\45\6\uffff\1\46",
            "\1\50\5\uffff\1\47\31\uffff\1\50\5\uffff\1\47",
            "\1\52\7\uffff\1\51\27\uffff\1\52\7\uffff\1\51",
            "\1\53\37\uffff\1\53",
            "",
            "",
            "",
            "",
            "",
            "",
            "\0\61",
            "\0\62",
            "",
            "\1\64",
            "\1\65",
            "",
            "",
            "\1\67\37\uffff\1\67",
            "\1\70\37\uffff\1\70",
            "\1\71\37\uffff\1\71",
            "",
            "\1\72\37\uffff\1\72",
            "\1\73\37\uffff\1\73",
            "\1\74\37\uffff\1\74",
            "\1\75\37\uffff\1\75",
            "\1\76\37\uffff\1\76",
            "\1\77\37\uffff\1\77",
            "\1\100\37\uffff\1\100",
            "\1\101\37\uffff\1\101",
            "\1\102\37\uffff\1\102",
            "\1\103\37\uffff\1\103",
            "\1\104\37\uffff\1\104",
            "\1\105\37\uffff\1\105",
            "\1\106\37\uffff\1\106",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\110\37\uffff\1\110",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\111\37\uffff\1\111",
            "\1\112\37\uffff\1\112",
            "\1\113\37\uffff\1\113",
            "\1\114\37\uffff\1\114",
            "\1\115\37\uffff\1\115",
            "\1\116\37\uffff\1\116",
            "\1\117\37\uffff\1\117",
            "\1\120\37\uffff\1\120",
            "\1\121\37\uffff\1\121",
            "\1\122\37\uffff\1\122",
            "\1\123\37\uffff\1\123",
            "\1\124\37\uffff\1\124",
            "\1\125\37\uffff\1\125",
            "\1\126\37\uffff\1\126",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\132\37\uffff\1\132",
            "\1\133\37\uffff\1\133",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\135\37\uffff\1\135",
            "\1\136\37\uffff\1\136",
            "\1\137\37\uffff\1\137",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\141\37\uffff\1\141",
            "\1\142\37\uffff\1\142",
            "\1\143\37\uffff\1\143",
            "\1\144\37\uffff\1\144",
            "\1\145\37\uffff\1\145",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "",
            "",
            "",
            "\1\150\37\uffff\1\150",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "",
            "\1\152\37\uffff\1\152",
            "\1\153\37\uffff\1\153",
            "\1\154\37\uffff\1\154",
            "",
            "\1\155\37\uffff\1\155",
            "\1\156\37\uffff\1\156",
            "\1\157\37\uffff\1\157",
            "\1\160\37\uffff\1\160",
            "\1\161\37\uffff\1\161",
            "",
            "",
            "\1\162\37\uffff\1\162",
            "",
            "\1\163\37\uffff\1\163",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\165\37\uffff\1\165",
            "\1\166\37\uffff\1\166",
            "\1\167\37\uffff\1\167",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\173\37\uffff\1\173",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            "",
            "",
            "",
            "\1\u0080\37\uffff\1\u0080",
            "",
            "",
            "",
            "",
            "\1\34\13\uffff\12\34\7\uffff\32\34\4\uffff\1\34\1\uffff\32\34",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( TIMESTAMP | BOOLEAN | DECIMAL | PRIMARY | VARCHAR | BIGINT | CREATE | EXISTS | UNIQUE | TABLE | DATE | NULL | TEXT | UUID | INT | KEY | NOT | IF | LeftParenthesis | RightParenthesis | Comma | FullStop | Semicolon | RULE_ID | RULE_QUOTED_ID | RULE_STRING | RULE_INT | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA10_18 = input.LA(1);

                        s = -1;
                        if ( ((LA10_18>='\u0000' && LA10_18<='\uFFFF')) ) {s = 49;}

                        else s = 24;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA10_0 = input.LA(1);

                        s = -1;
                        if ( (LA10_0=='T'||LA10_0=='t') ) {s = 1;}

                        else if ( (LA10_0=='B'||LA10_0=='b') ) {s = 2;}

                        else if ( (LA10_0=='D'||LA10_0=='d') ) {s = 3;}

                        else if ( (LA10_0=='P'||LA10_0=='p') ) {s = 4;}

                        else if ( (LA10_0=='V'||LA10_0=='v') ) {s = 5;}

                        else if ( (LA10_0=='C'||LA10_0=='c') ) {s = 6;}

                        else if ( (LA10_0=='E'||LA10_0=='e') ) {s = 7;}

                        else if ( (LA10_0=='U'||LA10_0=='u') ) {s = 8;}

                        else if ( (LA10_0=='N'||LA10_0=='n') ) {s = 9;}

                        else if ( (LA10_0=='I'||LA10_0=='i') ) {s = 10;}

                        else if ( (LA10_0=='K'||LA10_0=='k') ) {s = 11;}

                        else if ( (LA10_0=='(') ) {s = 12;}

                        else if ( (LA10_0==')') ) {s = 13;}

                        else if ( (LA10_0==',') ) {s = 14;}

                        else if ( (LA10_0=='.') ) {s = 15;}

                        else if ( (LA10_0==';') ) {s = 16;}

                        else if ( (LA10_0=='A'||(LA10_0>='F' && LA10_0<='H')||LA10_0=='J'||(LA10_0>='L' && LA10_0<='M')||LA10_0=='O'||(LA10_0>='Q' && LA10_0<='S')||(LA10_0>='W' && LA10_0<='Z')||LA10_0=='_'||LA10_0=='a'||(LA10_0>='f' && LA10_0<='h')||LA10_0=='j'||(LA10_0>='l' && LA10_0<='m')||LA10_0=='o'||(LA10_0>='q' && LA10_0<='s')||(LA10_0>='w' && LA10_0<='z')) ) {s = 17;}

                        else if ( (LA10_0=='\"') ) {s = 18;}

                        else if ( (LA10_0=='\'') ) {s = 19;}

                        else if ( ((LA10_0>='0' && LA10_0<='9')) ) {s = 20;}

                        else if ( (LA10_0=='/') ) {s = 21;}

                        else if ( (LA10_0=='-') ) {s = 22;}

                        else if ( ((LA10_0>='\t' && LA10_0<='\n')||LA10_0=='\r'||LA10_0==' ') ) {s = 23;}

                        else if ( ((LA10_0>='\u0000' && LA10_0<='\b')||(LA10_0>='\u000B' && LA10_0<='\f')||(LA10_0>='\u000E' && LA10_0<='\u001F')||LA10_0=='!'||(LA10_0>='#' && LA10_0<='&')||(LA10_0>='*' && LA10_0<='+')||LA10_0==':'||(LA10_0>='<' && LA10_0<='@')||(LA10_0>='[' && LA10_0<='^')||LA10_0=='`'||(LA10_0>='{' && LA10_0<='\uFFFF')) ) {s = 24;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA10_19 = input.LA(1);

                        s = -1;
                        if ( ((LA10_19>='\u0000' && LA10_19<='\uFFFF')) ) {s = 50;}

                        else s = 24;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 10, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}