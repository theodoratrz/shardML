package io.shardml.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import io.shardml.services.SQLGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSQLParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "TIMESTAMPTZ", "CONSTRAINT", "DEFERRABLE", "REFERENCES", "BIGSERIAL", "CHARACTER", "GENERATED", "IMMEDIATE", "INITIALLY", "PRECISION", "TIMESTAMP", "DEFERRED", "IDENTITY", "RESTRICT", "SMALLINT", "BOOLEAN", "CASCADE", "DECIMAL", "DEFAULT", "FOREIGN", "INCLUDE", "INTEGER", "NUMERIC", "PRIMARY", "VARCHAR", "VARYING", "WITHOUT", "ACTION", "ALWAYS", "BIGINT", "CREATE", "DELETE", "DOUBLE", "EXISTS", "SCHEMA", "SERIAL", "SIMPLE", "STORED", "UNIQUE", "UPDATE", "ALTER", "BYTEA", "CHECK", "FLOAT", "INDEX", "JSONB", "MATCH", "TABLE", "USING", "WHERE", "CHAR", "DATE", "FULL", "NULL", "ONLY", "REAL", "TEXT", "TIME", "UUID", "WITH", "ZONE", "ADD", "INT", "KEY", "NOT", "SET", "AS", "BY", "IF", "NO", "ON", "LeftParenthesis", "RightParenthesis", "Comma", "FullStop", "Semicolon", "RULE_ID", "RULE_QUOTED_ID", "RULE_STRING", "RULE_INT", "RULE_DOLLAR_STRING", "RULE_PSQL_META", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int ADD=65;
    public static final int CREATE=34;
    public static final int ZONE=64;
    public static final int DECIMAL=21;
    public static final int ONLY=58;
    public static final int TIMESTAMPTZ=4;
    public static final int TEXT=60;
    public static final int IMMEDIATE=11;
    public static final int TABLE=51;
    public static final int LeftParenthesis=75;
    public static final int TIMESTAMP=14;
    public static final int INDEX=48;
    public static final int DEFERRED=15;
    public static final int INCLUDE=24;
    public static final int UUID=62;
    public static final int BIGSERIAL=8;
    public static final int IF=72;
    public static final int INTEGER=25;
    public static final int VARYING=29;
    public static final int NULL=57;
    public static final int RULE_ID=80;
    public static final int RULE_QUOTED_ID=81;
    public static final int UNIQUE=42;
    public static final int RightParenthesis=76;
    public static final int RESTRICT=17;
    public static final int BIGINT=33;
    public static final int WHERE=53;
    public static final int BOOLEAN=19;
    public static final int FULL=56;
    public static final int IDENTITY=16;
    public static final int USING=52;
    public static final int DATE=55;
    public static final int NOT=68;
    public static final int AS=70;
    public static final int RULE_INT=83;
    public static final int VARCHAR=28;
    public static final int REAL=59;
    public static final int RULE_ML_COMMENT=86;
    public static final int DEFERRABLE=6;
    public static final int WITHOUT=30;
    public static final int CHECK=46;
    public static final int RULE_PSQL_META=85;
    public static final int KEY=67;
    public static final int ALTER=44;
    public static final int NO=73;
    public static final int FLOAT=47;
    public static final int SET=69;
    public static final int STORED=41;
    public static final int CASCADE=20;
    public static final int CHAR=54;
    public static final int RULE_STRING=82;
    public static final int CONSTRAINT=5;
    public static final int ALWAYS=32;
    public static final int INT=66;
    public static final int PRECISION=13;
    public static final int RULE_SL_COMMENT=87;
    public static final int Comma=77;
    public static final int DELETE=35;
    public static final int FOREIGN=23;
    public static final int SCHEMA=38;
    public static final int BY=71;
    public static final int CHARACTER=9;
    public static final int UPDATE=43;
    public static final int DOUBLE=36;
    public static final int EOF=-1;
    public static final int GENERATED=10;
    public static final int ON=74;
    public static final int SERIAL=39;
    public static final int FullStop=78;
    public static final int NUMERIC=26;
    public static final int JSONB=49;
    public static final int EXISTS=37;
    public static final int RULE_WS=88;
    public static final int PRIMARY=27;
    public static final int TIME=61;
    public static final int RULE_ANY_OTHER=89;
    public static final int WITH=63;
    public static final int Semicolon=79;
    public static final int INITIALLY=12;
    public static final int BYTEA=45;
    public static final int ACTION=31;
    public static final int SMALLINT=18;
    public static final int SIMPLE=40;
    public static final int RULE_DOLLAR_STRING=84;
    public static final int REFERENCES=7;
    public static final int DEFAULT=22;
    public static final int MATCH=50;

    // delegates
    // delegators


        public InternalSQLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSQLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSQLParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSQLParser.g"; }



     	private SQLGrammarAccess grammarAccess;

        public InternalSQLParser(TokenStream input, SQLGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Schema";
       	}

       	@Override
       	protected SQLGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleSchema"
    // InternalSQLParser.g:58:1: entryRuleSchema returns [EObject current=null] : iv_ruleSchema= ruleSchema EOF ;
    public final EObject entryRuleSchema() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSchema = null;


        try {
            // InternalSQLParser.g:58:47: (iv_ruleSchema= ruleSchema EOF )
            // InternalSQLParser.g:59:2: iv_ruleSchema= ruleSchema EOF
            {
             newCompositeNode(grammarAccess.getSchemaRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSchema=ruleSchema();

            state._fsp--;

             current =iv_ruleSchema; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSchema"


    // $ANTLR start "ruleSchema"
    // InternalSQLParser.g:65:1: ruleSchema returns [EObject current=null] : ( ( ( (lv_tables_0_0= ruleTable ) ) | ( (lv_alters_1_0= ruleAlterTable ) ) | ( (lv_indexes_2_0= ruleIndex ) ) | ( (lv_schemas_3_0= ruleSchemaDecl ) ) | ( (lv_statements_4_0= ruleStatement ) ) ) (otherlv_5= Semicolon )? )* ;
    public final EObject ruleSchema() throws RecognitionException {
        EObject current = null;

        Token otherlv_5=null;
        EObject lv_tables_0_0 = null;

        EObject lv_alters_1_0 = null;

        EObject lv_indexes_2_0 = null;

        EObject lv_schemas_3_0 = null;

        EObject lv_statements_4_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:71:2: ( ( ( ( (lv_tables_0_0= ruleTable ) ) | ( (lv_alters_1_0= ruleAlterTable ) ) | ( (lv_indexes_2_0= ruleIndex ) ) | ( (lv_schemas_3_0= ruleSchemaDecl ) ) | ( (lv_statements_4_0= ruleStatement ) ) ) (otherlv_5= Semicolon )? )* )
            // InternalSQLParser.g:72:2: ( ( ( (lv_tables_0_0= ruleTable ) ) | ( (lv_alters_1_0= ruleAlterTable ) ) | ( (lv_indexes_2_0= ruleIndex ) ) | ( (lv_schemas_3_0= ruleSchemaDecl ) ) | ( (lv_statements_4_0= ruleStatement ) ) ) (otherlv_5= Semicolon )? )*
            {
            // InternalSQLParser.g:72:2: ( ( ( (lv_tables_0_0= ruleTable ) ) | ( (lv_alters_1_0= ruleAlterTable ) ) | ( (lv_indexes_2_0= ruleIndex ) ) | ( (lv_schemas_3_0= ruleSchemaDecl ) ) | ( (lv_statements_4_0= ruleStatement ) ) ) (otherlv_5= Semicolon )? )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=CREATE && LA3_0<=DELETE)||(LA3_0>=UPDATE && LA3_0<=ALTER)||LA3_0==SET||LA3_0==RULE_ID||LA3_0==RULE_PSQL_META||LA3_0==RULE_ANY_OTHER) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalSQLParser.g:73:3: ( ( (lv_tables_0_0= ruleTable ) ) | ( (lv_alters_1_0= ruleAlterTable ) ) | ( (lv_indexes_2_0= ruleIndex ) ) | ( (lv_schemas_3_0= ruleSchemaDecl ) ) | ( (lv_statements_4_0= ruleStatement ) ) ) (otherlv_5= Semicolon )?
            	    {
            	    // InternalSQLParser.g:73:3: ( ( (lv_tables_0_0= ruleTable ) ) | ( (lv_alters_1_0= ruleAlterTable ) ) | ( (lv_indexes_2_0= ruleIndex ) ) | ( (lv_schemas_3_0= ruleSchemaDecl ) ) | ( (lv_statements_4_0= ruleStatement ) ) )
            	    int alt1=5;
            	    switch ( input.LA(1) ) {
            	    case CREATE:
            	        {
            	        switch ( input.LA(2) ) {
            	        case SCHEMA:
            	            {
            	            alt1=4;
            	            }
            	            break;
            	        case RULE_ID:
            	            {
            	            alt1=5;
            	            }
            	            break;
            	        case UNIQUE:
            	        case INDEX:
            	            {
            	            alt1=3;
            	            }
            	            break;
            	        case TABLE:
            	            {
            	            alt1=1;
            	            }
            	            break;
            	        default:
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 1, 1, input);

            	            throw nvae;
            	        }

            	        }
            	        break;
            	    case ALTER:
            	        {
            	        int LA1_2 = input.LA(2);

            	        if ( (LA1_2==TABLE) ) {
            	            alt1=2;
            	        }
            	        else if ( (LA1_2==DEFAULT||LA1_2==SCHEMA||LA1_2==INDEX||LA1_2==RULE_ID) ) {
            	            alt1=5;
            	        }
            	        else {
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 1, 2, input);

            	            throw nvae;
            	        }
            	        }
            	        break;
            	    case DELETE:
            	    case UPDATE:
            	    case SET:
            	    case RULE_ID:
            	    case RULE_PSQL_META:
            	    case RULE_ANY_OTHER:
            	        {
            	        alt1=5;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 1, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt1) {
            	        case 1 :
            	            // InternalSQLParser.g:74:4: ( (lv_tables_0_0= ruleTable ) )
            	            {
            	            // InternalSQLParser.g:74:4: ( (lv_tables_0_0= ruleTable ) )
            	            // InternalSQLParser.g:75:5: (lv_tables_0_0= ruleTable )
            	            {
            	            // InternalSQLParser.g:75:5: (lv_tables_0_0= ruleTable )
            	            // InternalSQLParser.g:76:6: lv_tables_0_0= ruleTable
            	            {

            	            						newCompositeNode(grammarAccess.getSchemaAccess().getTablesTableParserRuleCall_0_0_0());
            	            					
            	            pushFollow(FOLLOW_3);
            	            lv_tables_0_0=ruleTable();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getSchemaRule());
            	            						}
            	            						add(
            	            							current,
            	            							"tables",
            	            							lv_tables_0_0,
            	            							"io.shardml.SQL.Table");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalSQLParser.g:94:4: ( (lv_alters_1_0= ruleAlterTable ) )
            	            {
            	            // InternalSQLParser.g:94:4: ( (lv_alters_1_0= ruleAlterTable ) )
            	            // InternalSQLParser.g:95:5: (lv_alters_1_0= ruleAlterTable )
            	            {
            	            // InternalSQLParser.g:95:5: (lv_alters_1_0= ruleAlterTable )
            	            // InternalSQLParser.g:96:6: lv_alters_1_0= ruleAlterTable
            	            {

            	            						newCompositeNode(grammarAccess.getSchemaAccess().getAltersAlterTableParserRuleCall_0_1_0());
            	            					
            	            pushFollow(FOLLOW_3);
            	            lv_alters_1_0=ruleAlterTable();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getSchemaRule());
            	            						}
            	            						add(
            	            							current,
            	            							"alters",
            	            							lv_alters_1_0,
            	            							"io.shardml.SQL.AlterTable");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }


            	            }


            	            }
            	            break;
            	        case 3 :
            	            // InternalSQLParser.g:114:4: ( (lv_indexes_2_0= ruleIndex ) )
            	            {
            	            // InternalSQLParser.g:114:4: ( (lv_indexes_2_0= ruleIndex ) )
            	            // InternalSQLParser.g:115:5: (lv_indexes_2_0= ruleIndex )
            	            {
            	            // InternalSQLParser.g:115:5: (lv_indexes_2_0= ruleIndex )
            	            // InternalSQLParser.g:116:6: lv_indexes_2_0= ruleIndex
            	            {

            	            						newCompositeNode(grammarAccess.getSchemaAccess().getIndexesIndexParserRuleCall_0_2_0());
            	            					
            	            pushFollow(FOLLOW_3);
            	            lv_indexes_2_0=ruleIndex();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getSchemaRule());
            	            						}
            	            						add(
            	            							current,
            	            							"indexes",
            	            							lv_indexes_2_0,
            	            							"io.shardml.SQL.Index");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }


            	            }


            	            }
            	            break;
            	        case 4 :
            	            // InternalSQLParser.g:134:4: ( (lv_schemas_3_0= ruleSchemaDecl ) )
            	            {
            	            // InternalSQLParser.g:134:4: ( (lv_schemas_3_0= ruleSchemaDecl ) )
            	            // InternalSQLParser.g:135:5: (lv_schemas_3_0= ruleSchemaDecl )
            	            {
            	            // InternalSQLParser.g:135:5: (lv_schemas_3_0= ruleSchemaDecl )
            	            // InternalSQLParser.g:136:6: lv_schemas_3_0= ruleSchemaDecl
            	            {

            	            						newCompositeNode(grammarAccess.getSchemaAccess().getSchemasSchemaDeclParserRuleCall_0_3_0());
            	            					
            	            pushFollow(FOLLOW_3);
            	            lv_schemas_3_0=ruleSchemaDecl();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getSchemaRule());
            	            						}
            	            						add(
            	            							current,
            	            							"schemas",
            	            							lv_schemas_3_0,
            	            							"io.shardml.SQL.SchemaDecl");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }


            	            }


            	            }
            	            break;
            	        case 5 :
            	            // InternalSQLParser.g:154:4: ( (lv_statements_4_0= ruleStatement ) )
            	            {
            	            // InternalSQLParser.g:154:4: ( (lv_statements_4_0= ruleStatement ) )
            	            // InternalSQLParser.g:155:5: (lv_statements_4_0= ruleStatement )
            	            {
            	            // InternalSQLParser.g:155:5: (lv_statements_4_0= ruleStatement )
            	            // InternalSQLParser.g:156:6: lv_statements_4_0= ruleStatement
            	            {

            	            						newCompositeNode(grammarAccess.getSchemaAccess().getStatementsStatementParserRuleCall_0_4_0());
            	            					
            	            pushFollow(FOLLOW_3);
            	            lv_statements_4_0=ruleStatement();

            	            state._fsp--;


            	            						if (current==null) {
            	            							current = createModelElementForParent(grammarAccess.getSchemaRule());
            	            						}
            	            						add(
            	            							current,
            	            							"statements",
            	            							lv_statements_4_0,
            	            							"io.shardml.SQL.Statement");
            	            						afterParserOrEnumRuleCall();
            	            					

            	            }


            	            }


            	            }
            	            break;

            	    }

            	    // InternalSQLParser.g:174:3: (otherlv_5= Semicolon )?
            	    int alt2=2;
            	    int LA2_0 = input.LA(1);

            	    if ( (LA2_0==Semicolon) ) {
            	        alt2=1;
            	    }
            	    switch (alt2) {
            	        case 1 :
            	            // InternalSQLParser.g:175:4: otherlv_5= Semicolon
            	            {
            	            otherlv_5=(Token)match(input,Semicolon,FOLLOW_3); 

            	            				newLeafNode(otherlv_5, grammarAccess.getSchemaAccess().getSemicolonKeyword_1());
            	            			

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSchema"


    // $ANTLR start "entryRuleTable"
    // InternalSQLParser.g:184:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalSQLParser.g:184:46: (iv_ruleTable= ruleTable EOF )
            // InternalSQLParser.g:185:2: iv_ruleTable= ruleTable EOF
            {
             newCompositeNode(grammarAccess.getTableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTable=ruleTable();

            state._fsp--;

             current =iv_ruleTable; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTable"


    // $ANTLR start "ruleTable"
    // InternalSQLParser.g:191:1: ruleTable returns [EObject current=null] : (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ( (lv_options_15_0= ruleTableOptions ) )? ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_ifNotExists_2_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_11=null;
        Token otherlv_14=null;
        AntlrDatatypeRuleToken lv_schemaName_5_0 = null;

        AntlrDatatypeRuleToken lv_name_7_0 = null;

        EObject lv_columns_9_0 = null;

        EObject lv_constraints_10_0 = null;

        EObject lv_columns_12_0 = null;

        EObject lv_constraints_13_0 = null;

        EObject lv_options_15_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:197:2: ( (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ( (lv_options_15_0= ruleTableOptions ) )? ) )
            // InternalSQLParser.g:198:2: (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ( (lv_options_15_0= ruleTableOptions ) )? )
            {
            // InternalSQLParser.g:198:2: (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ( (lv_options_15_0= ruleTableOptions ) )? )
            // InternalSQLParser.g:199:3: otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ( (lv_options_15_0= ruleTableOptions ) )?
            {
            otherlv_0=(Token)match(input,CREATE,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getTableAccess().getCREATEKeyword_0());
            		
            otherlv_1=(Token)match(input,TABLE,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getTableAccess().getTABLEKeyword_1());
            		
            // InternalSQLParser.g:207:3: ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==IF) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==NOT) ) {
                    alt4=1;
                }
            }
            switch (alt4) {
                case 1 :
                    // InternalSQLParser.g:208:4: ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS
                    {
                    // InternalSQLParser.g:208:4: ( (lv_ifNotExists_2_0= IF ) )
                    // InternalSQLParser.g:209:5: (lv_ifNotExists_2_0= IF )
                    {
                    // InternalSQLParser.g:209:5: (lv_ifNotExists_2_0= IF )
                    // InternalSQLParser.g:210:6: lv_ifNotExists_2_0= IF
                    {
                    lv_ifNotExists_2_0=(Token)match(input,IF,FOLLOW_6); 

                    						newLeafNode(lv_ifNotExists_2_0, grammarAccess.getTableAccess().getIfNotExistsIFKeyword_2_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTableRule());
                    						}
                    						setWithLastConsumed(current, "ifNotExists", lv_ifNotExists_2_0 != null, "IF");
                    					

                    }


                    }

                    otherlv_3=(Token)match(input,NOT,FOLLOW_7); 

                    				newLeafNode(otherlv_3, grammarAccess.getTableAccess().getNOTKeyword_2_1());
                    			
                    otherlv_4=(Token)match(input,EXISTS,FOLLOW_5); 

                    				newLeafNode(otherlv_4, grammarAccess.getTableAccess().getEXISTSKeyword_2_2());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:231:3: ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )?
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // InternalSQLParser.g:232:4: ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop
                    {
                    // InternalSQLParser.g:232:4: ( (lv_schemaName_5_0= ruleName ) )
                    // InternalSQLParser.g:233:5: (lv_schemaName_5_0= ruleName )
                    {
                    // InternalSQLParser.g:233:5: (lv_schemaName_5_0= ruleName )
                    // InternalSQLParser.g:234:6: lv_schemaName_5_0= ruleName
                    {

                    						newCompositeNode(grammarAccess.getTableAccess().getSchemaNameNameParserRuleCall_3_0_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_schemaName_5_0=ruleName();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTableRule());
                    						}
                    						set(
                    							current,
                    							"schemaName",
                    							lv_schemaName_5_0,
                    							"io.shardml.SQL.Name");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_6=(Token)match(input,FullStop,FOLLOW_5); 

                    				newLeafNode(otherlv_6, grammarAccess.getTableAccess().getFullStopKeyword_3_1());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:256:3: ( (lv_name_7_0= ruleName ) )
            // InternalSQLParser.g:257:4: (lv_name_7_0= ruleName )
            {
            // InternalSQLParser.g:257:4: (lv_name_7_0= ruleName )
            // InternalSQLParser.g:258:5: lv_name_7_0= ruleName
            {

            					newCompositeNode(grammarAccess.getTableAccess().getNameNameParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_9);
            lv_name_7_0=ruleName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTableRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_7_0,
            						"io.shardml.SQL.Name");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_8=(Token)match(input,LeftParenthesis,FOLLOW_10); 

            			newLeafNode(otherlv_8, grammarAccess.getTableAccess().getLeftParenthesisKeyword_5());
            		
            // InternalSQLParser.g:279:3: ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==TIMESTAMPTZ||(LA6_0>=BIGSERIAL && LA6_0<=IMMEDIATE)||(LA6_0>=PRECISION && LA6_0<=DECIMAL)||(LA6_0>=INCLUDE && LA6_0<=NUMERIC)||(LA6_0>=VARCHAR && LA6_0<=BIGINT)||(LA6_0>=DELETE && LA6_0<=STORED)||(LA6_0>=UPDATE && LA6_0<=BYTEA)||(LA6_0>=FLOAT && LA6_0<=MATCH)||(LA6_0>=CHAR && LA6_0<=DATE)||(LA6_0>=REAL && LA6_0<=UUID)||(LA6_0>=ZONE && LA6_0<=KEY)||LA6_0==SET||(LA6_0>=BY && LA6_0<=NO)||(LA6_0>=RULE_ID && LA6_0<=RULE_QUOTED_ID)) ) {
                alt6=1;
            }
            else if ( (LA6_0==CONSTRAINT||LA6_0==FOREIGN||LA6_0==PRIMARY||LA6_0==UNIQUE||LA6_0==CHECK) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalSQLParser.g:280:4: ( (lv_columns_9_0= ruleColumn ) )
                    {
                    // InternalSQLParser.g:280:4: ( (lv_columns_9_0= ruleColumn ) )
                    // InternalSQLParser.g:281:5: (lv_columns_9_0= ruleColumn )
                    {
                    // InternalSQLParser.g:281:5: (lv_columns_9_0= ruleColumn )
                    // InternalSQLParser.g:282:6: lv_columns_9_0= ruleColumn
                    {

                    						newCompositeNode(grammarAccess.getTableAccess().getColumnsColumnParserRuleCall_6_0_0());
                    					
                    pushFollow(FOLLOW_11);
                    lv_columns_9_0=ruleColumn();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTableRule());
                    						}
                    						add(
                    							current,
                    							"columns",
                    							lv_columns_9_0,
                    							"io.shardml.SQL.Column");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:300:4: ( (lv_constraints_10_0= ruleTableConstraint ) )
                    {
                    // InternalSQLParser.g:300:4: ( (lv_constraints_10_0= ruleTableConstraint ) )
                    // InternalSQLParser.g:301:5: (lv_constraints_10_0= ruleTableConstraint )
                    {
                    // InternalSQLParser.g:301:5: (lv_constraints_10_0= ruleTableConstraint )
                    // InternalSQLParser.g:302:6: lv_constraints_10_0= ruleTableConstraint
                    {

                    						newCompositeNode(grammarAccess.getTableAccess().getConstraintsTableConstraintParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_11);
                    lv_constraints_10_0=ruleTableConstraint();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTableRule());
                    						}
                    						add(
                    							current,
                    							"constraints",
                    							lv_constraints_10_0,
                    							"io.shardml.SQL.TableConstraint");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalSQLParser.g:320:3: (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==Comma) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalSQLParser.g:321:4: otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) )
            	    {
            	    otherlv_11=(Token)match(input,Comma,FOLLOW_10); 

            	    				newLeafNode(otherlv_11, grammarAccess.getTableAccess().getCommaKeyword_7_0());
            	    			
            	    // InternalSQLParser.g:325:4: ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) )
            	    int alt7=2;
            	    int LA7_0 = input.LA(1);

            	    if ( (LA7_0==TIMESTAMPTZ||(LA7_0>=BIGSERIAL && LA7_0<=IMMEDIATE)||(LA7_0>=PRECISION && LA7_0<=DECIMAL)||(LA7_0>=INCLUDE && LA7_0<=NUMERIC)||(LA7_0>=VARCHAR && LA7_0<=BIGINT)||(LA7_0>=DELETE && LA7_0<=STORED)||(LA7_0>=UPDATE && LA7_0<=BYTEA)||(LA7_0>=FLOAT && LA7_0<=MATCH)||(LA7_0>=CHAR && LA7_0<=DATE)||(LA7_0>=REAL && LA7_0<=UUID)||(LA7_0>=ZONE && LA7_0<=KEY)||LA7_0==SET||(LA7_0>=BY && LA7_0<=NO)||(LA7_0>=RULE_ID && LA7_0<=RULE_QUOTED_ID)) ) {
            	        alt7=1;
            	    }
            	    else if ( (LA7_0==CONSTRAINT||LA7_0==FOREIGN||LA7_0==PRIMARY||LA7_0==UNIQUE||LA7_0==CHECK) ) {
            	        alt7=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 7, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt7) {
            	        case 1 :
            	            // InternalSQLParser.g:326:5: ( (lv_columns_12_0= ruleColumn ) )
            	            {
            	            // InternalSQLParser.g:326:5: ( (lv_columns_12_0= ruleColumn ) )
            	            // InternalSQLParser.g:327:6: (lv_columns_12_0= ruleColumn )
            	            {
            	            // InternalSQLParser.g:327:6: (lv_columns_12_0= ruleColumn )
            	            // InternalSQLParser.g:328:7: lv_columns_12_0= ruleColumn
            	            {

            	            							newCompositeNode(grammarAccess.getTableAccess().getColumnsColumnParserRuleCall_7_1_0_0());
            	            						
            	            pushFollow(FOLLOW_11);
            	            lv_columns_12_0=ruleColumn();

            	            state._fsp--;


            	            							if (current==null) {
            	            								current = createModelElementForParent(grammarAccess.getTableRule());
            	            							}
            	            							add(
            	            								current,
            	            								"columns",
            	            								lv_columns_12_0,
            	            								"io.shardml.SQL.Column");
            	            							afterParserOrEnumRuleCall();
            	            						

            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalSQLParser.g:346:5: ( (lv_constraints_13_0= ruleTableConstraint ) )
            	            {
            	            // InternalSQLParser.g:346:5: ( (lv_constraints_13_0= ruleTableConstraint ) )
            	            // InternalSQLParser.g:347:6: (lv_constraints_13_0= ruleTableConstraint )
            	            {
            	            // InternalSQLParser.g:347:6: (lv_constraints_13_0= ruleTableConstraint )
            	            // InternalSQLParser.g:348:7: lv_constraints_13_0= ruleTableConstraint
            	            {

            	            							newCompositeNode(grammarAccess.getTableAccess().getConstraintsTableConstraintParserRuleCall_7_1_1_0());
            	            						
            	            pushFollow(FOLLOW_11);
            	            lv_constraints_13_0=ruleTableConstraint();

            	            state._fsp--;


            	            							if (current==null) {
            	            								current = createModelElementForParent(grammarAccess.getTableRule());
            	            							}
            	            							add(
            	            								current,
            	            								"constraints",
            	            								lv_constraints_13_0,
            	            								"io.shardml.SQL.TableConstraint");
            	            							afterParserOrEnumRuleCall();
            	            						

            	            }


            	            }


            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            otherlv_14=(Token)match(input,RightParenthesis,FOLLOW_12); 

            			newLeafNode(otherlv_14, grammarAccess.getTableAccess().getRightParenthesisKeyword_8());
            		
            // InternalSQLParser.g:371:3: ( (lv_options_15_0= ruleTableOptions ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==WITH) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalSQLParser.g:372:4: (lv_options_15_0= ruleTableOptions )
                    {
                    // InternalSQLParser.g:372:4: (lv_options_15_0= ruleTableOptions )
                    // InternalSQLParser.g:373:5: lv_options_15_0= ruleTableOptions
                    {

                    					newCompositeNode(grammarAccess.getTableAccess().getOptionsTableOptionsParserRuleCall_9_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_options_15_0=ruleTableOptions();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getTableRule());
                    					}
                    					set(
                    						current,
                    						"options",
                    						lv_options_15_0,
                    						"io.shardml.SQL.TableOptions");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTable"


    // $ANTLR start "entryRuleTableOptions"
    // InternalSQLParser.g:394:1: entryRuleTableOptions returns [EObject current=null] : iv_ruleTableOptions= ruleTableOptions EOF ;
    public final EObject entryRuleTableOptions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableOptions = null;


        try {
            // InternalSQLParser.g:394:53: (iv_ruleTableOptions= ruleTableOptions EOF )
            // InternalSQLParser.g:395:2: iv_ruleTableOptions= ruleTableOptions EOF
            {
             newCompositeNode(grammarAccess.getTableOptionsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTableOptions=ruleTableOptions();

            state._fsp--;

             current =iv_ruleTableOptions; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTableOptions"


    // $ANTLR start "ruleTableOptions"
    // InternalSQLParser.g:401:1: ruleTableOptions returns [EObject current=null] : ( ( (lv_kind_0_0= ruleWithKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) ) ;
    public final EObject ruleTableOptions() throws RecognitionException {
        EObject current = null;

        Enumerator lv_kind_0_0 = null;

        AntlrDatatypeRuleToken lv_text_1_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:407:2: ( ( ( (lv_kind_0_0= ruleWithKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) ) )
            // InternalSQLParser.g:408:2: ( ( (lv_kind_0_0= ruleWithKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) )
            {
            // InternalSQLParser.g:408:2: ( ( (lv_kind_0_0= ruleWithKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) )
            // InternalSQLParser.g:409:3: ( (lv_kind_0_0= ruleWithKind ) ) ( (lv_text_1_0= ruleParenthesized ) )
            {
            // InternalSQLParser.g:409:3: ( (lv_kind_0_0= ruleWithKind ) )
            // InternalSQLParser.g:410:4: (lv_kind_0_0= ruleWithKind )
            {
            // InternalSQLParser.g:410:4: (lv_kind_0_0= ruleWithKind )
            // InternalSQLParser.g:411:5: lv_kind_0_0= ruleWithKind
            {

            					newCompositeNode(grammarAccess.getTableOptionsAccess().getKindWithKindEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_9);
            lv_kind_0_0=ruleWithKind();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTableOptionsRule());
            					}
            					set(
            						current,
            						"kind",
            						lv_kind_0_0,
            						"io.shardml.SQL.WithKind");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSQLParser.g:428:3: ( (lv_text_1_0= ruleParenthesized ) )
            // InternalSQLParser.g:429:4: (lv_text_1_0= ruleParenthesized )
            {
            // InternalSQLParser.g:429:4: (lv_text_1_0= ruleParenthesized )
            // InternalSQLParser.g:430:5: lv_text_1_0= ruleParenthesized
            {

            					newCompositeNode(grammarAccess.getTableOptionsAccess().getTextParenthesizedParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_text_1_0=ruleParenthesized();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTableOptionsRule());
            					}
            					set(
            						current,
            						"text",
            						lv_text_1_0,
            						"io.shardml.SQL.Parenthesized");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTableOptions"


    // $ANTLR start "entryRuleAlterTable"
    // InternalSQLParser.g:451:1: entryRuleAlterTable returns [EObject current=null] : iv_ruleAlterTable= ruleAlterTable EOF ;
    public final EObject entryRuleAlterTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAlterTable = null;


        try {
            // InternalSQLParser.g:451:51: (iv_ruleAlterTable= ruleAlterTable EOF )
            // InternalSQLParser.g:452:2: iv_ruleAlterTable= ruleAlterTable EOF
            {
             newCompositeNode(grammarAccess.getAlterTableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlterTable=ruleAlterTable();

            state._fsp--;

             current =iv_ruleAlterTable; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlterTable"


    // $ANTLR start "ruleAlterTable"
    // InternalSQLParser.g:458:1: ruleAlterTable returns [EObject current=null] : (otherlv_0= ALTER otherlv_1= TABLE ( ( (lv_ifExists_2_0= IF ) ) otherlv_3= EXISTS )? ( (lv_only_4_0= ONLY ) )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( ( ( ( ruleName ) ) otherlv_8= ADD ( (lv_constraint_9_0= ruleTableConstraint ) ) ) | ( ( (lv_target_10_0= ruleName ) ) ( (lv_body_11_0= ruleAlterTableBody ) ) otherlv_12= Semicolon ) ) ) ;
    public final EObject ruleAlterTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_ifExists_2_0=null;
        Token otherlv_3=null;
        Token lv_only_4_0=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_12=null;
        AntlrDatatypeRuleToken lv_schemaName_5_0 = null;

        EObject lv_constraint_9_0 = null;

        AntlrDatatypeRuleToken lv_target_10_0 = null;

        AntlrDatatypeRuleToken lv_body_11_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:464:2: ( (otherlv_0= ALTER otherlv_1= TABLE ( ( (lv_ifExists_2_0= IF ) ) otherlv_3= EXISTS )? ( (lv_only_4_0= ONLY ) )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( ( ( ( ruleName ) ) otherlv_8= ADD ( (lv_constraint_9_0= ruleTableConstraint ) ) ) | ( ( (lv_target_10_0= ruleName ) ) ( (lv_body_11_0= ruleAlterTableBody ) ) otherlv_12= Semicolon ) ) ) )
            // InternalSQLParser.g:465:2: (otherlv_0= ALTER otherlv_1= TABLE ( ( (lv_ifExists_2_0= IF ) ) otherlv_3= EXISTS )? ( (lv_only_4_0= ONLY ) )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( ( ( ( ruleName ) ) otherlv_8= ADD ( (lv_constraint_9_0= ruleTableConstraint ) ) ) | ( ( (lv_target_10_0= ruleName ) ) ( (lv_body_11_0= ruleAlterTableBody ) ) otherlv_12= Semicolon ) ) )
            {
            // InternalSQLParser.g:465:2: (otherlv_0= ALTER otherlv_1= TABLE ( ( (lv_ifExists_2_0= IF ) ) otherlv_3= EXISTS )? ( (lv_only_4_0= ONLY ) )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( ( ( ( ruleName ) ) otherlv_8= ADD ( (lv_constraint_9_0= ruleTableConstraint ) ) ) | ( ( (lv_target_10_0= ruleName ) ) ( (lv_body_11_0= ruleAlterTableBody ) ) otherlv_12= Semicolon ) ) )
            // InternalSQLParser.g:466:3: otherlv_0= ALTER otherlv_1= TABLE ( ( (lv_ifExists_2_0= IF ) ) otherlv_3= EXISTS )? ( (lv_only_4_0= ONLY ) )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( ( ( ( ruleName ) ) otherlv_8= ADD ( (lv_constraint_9_0= ruleTableConstraint ) ) ) | ( ( (lv_target_10_0= ruleName ) ) ( (lv_body_11_0= ruleAlterTableBody ) ) otherlv_12= Semicolon ) )
            {
            otherlv_0=(Token)match(input,ALTER,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getAlterTableAccess().getALTERKeyword_0());
            		
            otherlv_1=(Token)match(input,TABLE,FOLLOW_13); 

            			newLeafNode(otherlv_1, grammarAccess.getAlterTableAccess().getTABLEKeyword_1());
            		
            // InternalSQLParser.g:474:3: ( ( (lv_ifExists_2_0= IF ) ) otherlv_3= EXISTS )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==IF) ) {
                int LA10_1 = input.LA(2);

                if ( (LA10_1==EXISTS) ) {
                    alt10=1;
                }
            }
            switch (alt10) {
                case 1 :
                    // InternalSQLParser.g:475:4: ( (lv_ifExists_2_0= IF ) ) otherlv_3= EXISTS
                    {
                    // InternalSQLParser.g:475:4: ( (lv_ifExists_2_0= IF ) )
                    // InternalSQLParser.g:476:5: (lv_ifExists_2_0= IF )
                    {
                    // InternalSQLParser.g:476:5: (lv_ifExists_2_0= IF )
                    // InternalSQLParser.g:477:6: lv_ifExists_2_0= IF
                    {
                    lv_ifExists_2_0=(Token)match(input,IF,FOLLOW_7); 

                    						newLeafNode(lv_ifExists_2_0, grammarAccess.getAlterTableAccess().getIfExistsIFKeyword_2_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getAlterTableRule());
                    						}
                    						setWithLastConsumed(current, "ifExists", lv_ifExists_2_0 != null, "IF");
                    					

                    }


                    }

                    otherlv_3=(Token)match(input,EXISTS,FOLLOW_13); 

                    				newLeafNode(otherlv_3, grammarAccess.getAlterTableAccess().getEXISTSKeyword_2_1());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:494:3: ( (lv_only_4_0= ONLY ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ONLY) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalSQLParser.g:495:4: (lv_only_4_0= ONLY )
                    {
                    // InternalSQLParser.g:495:4: (lv_only_4_0= ONLY )
                    // InternalSQLParser.g:496:5: lv_only_4_0= ONLY
                    {
                    lv_only_4_0=(Token)match(input,ONLY,FOLLOW_5); 

                    					newLeafNode(lv_only_4_0, grammarAccess.getAlterTableAccess().getOnlyONLYKeyword_3_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getAlterTableRule());
                    					}
                    					setWithLastConsumed(current, "only", lv_only_4_0 != null, "ONLY");
                    				

                    }


                    }
                    break;

            }

            // InternalSQLParser.g:508:3: ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )?
            int alt12=2;
            alt12 = dfa12.predict(input);
            switch (alt12) {
                case 1 :
                    // InternalSQLParser.g:509:4: ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop
                    {
                    // InternalSQLParser.g:509:4: ( (lv_schemaName_5_0= ruleName ) )
                    // InternalSQLParser.g:510:5: (lv_schemaName_5_0= ruleName )
                    {
                    // InternalSQLParser.g:510:5: (lv_schemaName_5_0= ruleName )
                    // InternalSQLParser.g:511:6: lv_schemaName_5_0= ruleName
                    {

                    						newCompositeNode(grammarAccess.getAlterTableAccess().getSchemaNameNameParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_schemaName_5_0=ruleName();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getAlterTableRule());
                    						}
                    						set(
                    							current,
                    							"schemaName",
                    							lv_schemaName_5_0,
                    							"io.shardml.SQL.Name");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_6=(Token)match(input,FullStop,FOLLOW_5); 

                    				newLeafNode(otherlv_6, grammarAccess.getAlterTableAccess().getFullStopKeyword_4_1());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:533:3: ( ( ( ( ruleName ) ) otherlv_8= ADD ( (lv_constraint_9_0= ruleTableConstraint ) ) ) | ( ( (lv_target_10_0= ruleName ) ) ( (lv_body_11_0= ruleAlterTableBody ) ) otherlv_12= Semicolon ) )
            int alt13=2;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // InternalSQLParser.g:534:4: ( ( ( ruleName ) ) otherlv_8= ADD ( (lv_constraint_9_0= ruleTableConstraint ) ) )
                    {
                    // InternalSQLParser.g:534:4: ( ( ( ruleName ) ) otherlv_8= ADD ( (lv_constraint_9_0= ruleTableConstraint ) ) )
                    // InternalSQLParser.g:535:5: ( ( ruleName ) ) otherlv_8= ADD ( (lv_constraint_9_0= ruleTableConstraint ) )
                    {
                    // InternalSQLParser.g:535:5: ( ( ruleName ) )
                    // InternalSQLParser.g:536:6: ( ruleName )
                    {
                    // InternalSQLParser.g:536:6: ( ruleName )
                    // InternalSQLParser.g:537:7: ruleName
                    {

                    							if (current==null) {
                    								current = createModelElement(grammarAccess.getAlterTableRule());
                    							}
                    						

                    							newCompositeNode(grammarAccess.getAlterTableAccess().getTableTableCrossReference_5_0_0_0());
                    						
                    pushFollow(FOLLOW_14);
                    ruleName();

                    state._fsp--;


                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }

                    otherlv_8=(Token)match(input,ADD,FOLLOW_10); 

                    					newLeafNode(otherlv_8, grammarAccess.getAlterTableAccess().getADDKeyword_5_0_1());
                    				
                    // InternalSQLParser.g:555:5: ( (lv_constraint_9_0= ruleTableConstraint ) )
                    // InternalSQLParser.g:556:6: (lv_constraint_9_0= ruleTableConstraint )
                    {
                    // InternalSQLParser.g:556:6: (lv_constraint_9_0= ruleTableConstraint )
                    // InternalSQLParser.g:557:7: lv_constraint_9_0= ruleTableConstraint
                    {

                    							newCompositeNode(grammarAccess.getAlterTableAccess().getConstraintTableConstraintParserRuleCall_5_0_2_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_constraint_9_0=ruleTableConstraint();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getAlterTableRule());
                    							}
                    							set(
                    								current,
                    								"constraint",
                    								lv_constraint_9_0,
                    								"io.shardml.SQL.TableConstraint");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:576:4: ( ( (lv_target_10_0= ruleName ) ) ( (lv_body_11_0= ruleAlterTableBody ) ) otherlv_12= Semicolon )
                    {
                    // InternalSQLParser.g:576:4: ( ( (lv_target_10_0= ruleName ) ) ( (lv_body_11_0= ruleAlterTableBody ) ) otherlv_12= Semicolon )
                    // InternalSQLParser.g:577:5: ( (lv_target_10_0= ruleName ) ) ( (lv_body_11_0= ruleAlterTableBody ) ) otherlv_12= Semicolon
                    {
                    // InternalSQLParser.g:577:5: ( (lv_target_10_0= ruleName ) )
                    // InternalSQLParser.g:578:6: (lv_target_10_0= ruleName )
                    {
                    // InternalSQLParser.g:578:6: (lv_target_10_0= ruleName )
                    // InternalSQLParser.g:579:7: lv_target_10_0= ruleName
                    {

                    							newCompositeNode(grammarAccess.getAlterTableAccess().getTargetNameParserRuleCall_5_1_0_0());
                    						
                    pushFollow(FOLLOW_15);
                    lv_target_10_0=ruleName();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getAlterTableRule());
                    							}
                    							set(
                    								current,
                    								"target",
                    								lv_target_10_0,
                    								"io.shardml.SQL.Name");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }

                    // InternalSQLParser.g:596:5: ( (lv_body_11_0= ruleAlterTableBody ) )
                    // InternalSQLParser.g:597:6: (lv_body_11_0= ruleAlterTableBody )
                    {
                    // InternalSQLParser.g:597:6: (lv_body_11_0= ruleAlterTableBody )
                    // InternalSQLParser.g:598:7: lv_body_11_0= ruleAlterTableBody
                    {

                    							newCompositeNode(grammarAccess.getAlterTableAccess().getBodyAlterTableBodyParserRuleCall_5_1_1_0());
                    						
                    pushFollow(FOLLOW_16);
                    lv_body_11_0=ruleAlterTableBody();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getAlterTableRule());
                    							}
                    							set(
                    								current,
                    								"body",
                    								lv_body_11_0,
                    								"io.shardml.SQL.AlterTableBody");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }

                    otherlv_12=(Token)match(input,Semicolon,FOLLOW_2); 

                    					newLeafNode(otherlv_12, grammarAccess.getAlterTableAccess().getSemicolonKeyword_5_1_2());
                    				

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlterTable"


    // $ANTLR start "entryRuleIndex"
    // InternalSQLParser.g:625:1: entryRuleIndex returns [EObject current=null] : iv_ruleIndex= ruleIndex EOF ;
    public final EObject entryRuleIndex() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIndex = null;


        try {
            // InternalSQLParser.g:625:46: (iv_ruleIndex= ruleIndex EOF )
            // InternalSQLParser.g:626:2: iv_ruleIndex= ruleIndex EOF
            {
             newCompositeNode(grammarAccess.getIndexRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIndex=ruleIndex();

            state._fsp--;

             current =iv_ruleIndex; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIndex"


    // $ANTLR start "ruleIndex"
    // InternalSQLParser.g:632:1: ruleIndex returns [EObject current=null] : (otherlv_0= CREATE ( (lv_unique_1_0= UNIQUE ) )? otherlv_2= INDEX ( ( (lv_ifNotExists_3_0= IF ) ) otherlv_4= NOT otherlv_5= EXISTS )? ( (lv_name_6_0= ruleName ) )? otherlv_7= ON ( (lv_only_8_0= ONLY ) )? ( ( (lv_schemaName_9_0= ruleName ) ) otherlv_10= FullStop )? ( ( ruleName ) ) (otherlv_12= USING ( (lv_method_13_0= ruleName ) ) )? otherlv_14= LeftParenthesis ( (lv_parts_15_0= ruleIndexPart ) ) (otherlv_16= Comma ( (lv_parts_17_0= ruleIndexPart ) ) )* otherlv_18= RightParenthesis ( (lv_tail_19_0= ruleIndexTail ) )? ) ;
    public final EObject ruleIndex() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_unique_1_0=null;
        Token otherlv_2=null;
        Token lv_ifNotExists_3_0=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token lv_only_8_0=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        AntlrDatatypeRuleToken lv_name_6_0 = null;

        AntlrDatatypeRuleToken lv_schemaName_9_0 = null;

        AntlrDatatypeRuleToken lv_method_13_0 = null;

        EObject lv_parts_15_0 = null;

        EObject lv_parts_17_0 = null;

        AntlrDatatypeRuleToken lv_tail_19_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:638:2: ( (otherlv_0= CREATE ( (lv_unique_1_0= UNIQUE ) )? otherlv_2= INDEX ( ( (lv_ifNotExists_3_0= IF ) ) otherlv_4= NOT otherlv_5= EXISTS )? ( (lv_name_6_0= ruleName ) )? otherlv_7= ON ( (lv_only_8_0= ONLY ) )? ( ( (lv_schemaName_9_0= ruleName ) ) otherlv_10= FullStop )? ( ( ruleName ) ) (otherlv_12= USING ( (lv_method_13_0= ruleName ) ) )? otherlv_14= LeftParenthesis ( (lv_parts_15_0= ruleIndexPart ) ) (otherlv_16= Comma ( (lv_parts_17_0= ruleIndexPart ) ) )* otherlv_18= RightParenthesis ( (lv_tail_19_0= ruleIndexTail ) )? ) )
            // InternalSQLParser.g:639:2: (otherlv_0= CREATE ( (lv_unique_1_0= UNIQUE ) )? otherlv_2= INDEX ( ( (lv_ifNotExists_3_0= IF ) ) otherlv_4= NOT otherlv_5= EXISTS )? ( (lv_name_6_0= ruleName ) )? otherlv_7= ON ( (lv_only_8_0= ONLY ) )? ( ( (lv_schemaName_9_0= ruleName ) ) otherlv_10= FullStop )? ( ( ruleName ) ) (otherlv_12= USING ( (lv_method_13_0= ruleName ) ) )? otherlv_14= LeftParenthesis ( (lv_parts_15_0= ruleIndexPart ) ) (otherlv_16= Comma ( (lv_parts_17_0= ruleIndexPart ) ) )* otherlv_18= RightParenthesis ( (lv_tail_19_0= ruleIndexTail ) )? )
            {
            // InternalSQLParser.g:639:2: (otherlv_0= CREATE ( (lv_unique_1_0= UNIQUE ) )? otherlv_2= INDEX ( ( (lv_ifNotExists_3_0= IF ) ) otherlv_4= NOT otherlv_5= EXISTS )? ( (lv_name_6_0= ruleName ) )? otherlv_7= ON ( (lv_only_8_0= ONLY ) )? ( ( (lv_schemaName_9_0= ruleName ) ) otherlv_10= FullStop )? ( ( ruleName ) ) (otherlv_12= USING ( (lv_method_13_0= ruleName ) ) )? otherlv_14= LeftParenthesis ( (lv_parts_15_0= ruleIndexPart ) ) (otherlv_16= Comma ( (lv_parts_17_0= ruleIndexPart ) ) )* otherlv_18= RightParenthesis ( (lv_tail_19_0= ruleIndexTail ) )? )
            // InternalSQLParser.g:640:3: otherlv_0= CREATE ( (lv_unique_1_0= UNIQUE ) )? otherlv_2= INDEX ( ( (lv_ifNotExists_3_0= IF ) ) otherlv_4= NOT otherlv_5= EXISTS )? ( (lv_name_6_0= ruleName ) )? otherlv_7= ON ( (lv_only_8_0= ONLY ) )? ( ( (lv_schemaName_9_0= ruleName ) ) otherlv_10= FullStop )? ( ( ruleName ) ) (otherlv_12= USING ( (lv_method_13_0= ruleName ) ) )? otherlv_14= LeftParenthesis ( (lv_parts_15_0= ruleIndexPart ) ) (otherlv_16= Comma ( (lv_parts_17_0= ruleIndexPart ) ) )* otherlv_18= RightParenthesis ( (lv_tail_19_0= ruleIndexTail ) )?
            {
            otherlv_0=(Token)match(input,CREATE,FOLLOW_17); 

            			newLeafNode(otherlv_0, grammarAccess.getIndexAccess().getCREATEKeyword_0());
            		
            // InternalSQLParser.g:644:3: ( (lv_unique_1_0= UNIQUE ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==UNIQUE) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalSQLParser.g:645:4: (lv_unique_1_0= UNIQUE )
                    {
                    // InternalSQLParser.g:645:4: (lv_unique_1_0= UNIQUE )
                    // InternalSQLParser.g:646:5: lv_unique_1_0= UNIQUE
                    {
                    lv_unique_1_0=(Token)match(input,UNIQUE,FOLLOW_18); 

                    					newLeafNode(lv_unique_1_0, grammarAccess.getIndexAccess().getUniqueUNIQUEKeyword_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getIndexRule());
                    					}
                    					setWithLastConsumed(current, "unique", lv_unique_1_0 != null, "UNIQUE");
                    				

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,INDEX,FOLLOW_19); 

            			newLeafNode(otherlv_2, grammarAccess.getIndexAccess().getINDEXKeyword_2());
            		
            // InternalSQLParser.g:662:3: ( ( (lv_ifNotExists_3_0= IF ) ) otherlv_4= NOT otherlv_5= EXISTS )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==IF) ) {
                int LA15_1 = input.LA(2);

                if ( (LA15_1==NOT) ) {
                    alt15=1;
                }
            }
            switch (alt15) {
                case 1 :
                    // InternalSQLParser.g:663:4: ( (lv_ifNotExists_3_0= IF ) ) otherlv_4= NOT otherlv_5= EXISTS
                    {
                    // InternalSQLParser.g:663:4: ( (lv_ifNotExists_3_0= IF ) )
                    // InternalSQLParser.g:664:5: (lv_ifNotExists_3_0= IF )
                    {
                    // InternalSQLParser.g:664:5: (lv_ifNotExists_3_0= IF )
                    // InternalSQLParser.g:665:6: lv_ifNotExists_3_0= IF
                    {
                    lv_ifNotExists_3_0=(Token)match(input,IF,FOLLOW_6); 

                    						newLeafNode(lv_ifNotExists_3_0, grammarAccess.getIndexAccess().getIfNotExistsIFKeyword_3_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getIndexRule());
                    						}
                    						setWithLastConsumed(current, "ifNotExists", lv_ifNotExists_3_0 != null, "IF");
                    					

                    }


                    }

                    otherlv_4=(Token)match(input,NOT,FOLLOW_7); 

                    				newLeafNode(otherlv_4, grammarAccess.getIndexAccess().getNOTKeyword_3_1());
                    			
                    otherlv_5=(Token)match(input,EXISTS,FOLLOW_19); 

                    				newLeafNode(otherlv_5, grammarAccess.getIndexAccess().getEXISTSKeyword_3_2());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:686:3: ( (lv_name_6_0= ruleName ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==TIMESTAMPTZ||(LA16_0>=BIGSERIAL && LA16_0<=IMMEDIATE)||(LA16_0>=PRECISION && LA16_0<=DECIMAL)||(LA16_0>=INCLUDE && LA16_0<=NUMERIC)||(LA16_0>=VARCHAR && LA16_0<=BIGINT)||(LA16_0>=DELETE && LA16_0<=STORED)||(LA16_0>=UPDATE && LA16_0<=BYTEA)||(LA16_0>=FLOAT && LA16_0<=MATCH)||(LA16_0>=CHAR && LA16_0<=DATE)||(LA16_0>=REAL && LA16_0<=UUID)||(LA16_0>=ZONE && LA16_0<=KEY)||LA16_0==SET||(LA16_0>=BY && LA16_0<=NO)||(LA16_0>=RULE_ID && LA16_0<=RULE_QUOTED_ID)) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalSQLParser.g:687:4: (lv_name_6_0= ruleName )
                    {
                    // InternalSQLParser.g:687:4: (lv_name_6_0= ruleName )
                    // InternalSQLParser.g:688:5: lv_name_6_0= ruleName
                    {

                    					newCompositeNode(grammarAccess.getIndexAccess().getNameNameParserRuleCall_4_0());
                    				
                    pushFollow(FOLLOW_20);
                    lv_name_6_0=ruleName();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getIndexRule());
                    					}
                    					set(
                    						current,
                    						"name",
                    						lv_name_6_0,
                    						"io.shardml.SQL.Name");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_7=(Token)match(input,ON,FOLLOW_13); 

            			newLeafNode(otherlv_7, grammarAccess.getIndexAccess().getONKeyword_5());
            		
            // InternalSQLParser.g:709:3: ( (lv_only_8_0= ONLY ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==ONLY) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalSQLParser.g:710:4: (lv_only_8_0= ONLY )
                    {
                    // InternalSQLParser.g:710:4: (lv_only_8_0= ONLY )
                    // InternalSQLParser.g:711:5: lv_only_8_0= ONLY
                    {
                    lv_only_8_0=(Token)match(input,ONLY,FOLLOW_5); 

                    					newLeafNode(lv_only_8_0, grammarAccess.getIndexAccess().getOnlyONLYKeyword_6_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getIndexRule());
                    					}
                    					setWithLastConsumed(current, "only", lv_only_8_0 != null, "ONLY");
                    				

                    }


                    }
                    break;

            }

            // InternalSQLParser.g:723:3: ( ( (lv_schemaName_9_0= ruleName ) ) otherlv_10= FullStop )?
            int alt18=2;
            alt18 = dfa18.predict(input);
            switch (alt18) {
                case 1 :
                    // InternalSQLParser.g:724:4: ( (lv_schemaName_9_0= ruleName ) ) otherlv_10= FullStop
                    {
                    // InternalSQLParser.g:724:4: ( (lv_schemaName_9_0= ruleName ) )
                    // InternalSQLParser.g:725:5: (lv_schemaName_9_0= ruleName )
                    {
                    // InternalSQLParser.g:725:5: (lv_schemaName_9_0= ruleName )
                    // InternalSQLParser.g:726:6: lv_schemaName_9_0= ruleName
                    {

                    						newCompositeNode(grammarAccess.getIndexAccess().getSchemaNameNameParserRuleCall_7_0_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_schemaName_9_0=ruleName();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIndexRule());
                    						}
                    						set(
                    							current,
                    							"schemaName",
                    							lv_schemaName_9_0,
                    							"io.shardml.SQL.Name");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_10=(Token)match(input,FullStop,FOLLOW_5); 

                    				newLeafNode(otherlv_10, grammarAccess.getIndexAccess().getFullStopKeyword_7_1());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:748:3: ( ( ruleName ) )
            // InternalSQLParser.g:749:4: ( ruleName )
            {
            // InternalSQLParser.g:749:4: ( ruleName )
            // InternalSQLParser.g:750:5: ruleName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getIndexRule());
            					}
            				

            					newCompositeNode(grammarAccess.getIndexAccess().getTableTableCrossReference_8_0());
            				
            pushFollow(FOLLOW_21);
            ruleName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSQLParser.g:764:3: (otherlv_12= USING ( (lv_method_13_0= ruleName ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==USING) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalSQLParser.g:765:4: otherlv_12= USING ( (lv_method_13_0= ruleName ) )
                    {
                    otherlv_12=(Token)match(input,USING,FOLLOW_5); 

                    				newLeafNode(otherlv_12, grammarAccess.getIndexAccess().getUSINGKeyword_9_0());
                    			
                    // InternalSQLParser.g:769:4: ( (lv_method_13_0= ruleName ) )
                    // InternalSQLParser.g:770:5: (lv_method_13_0= ruleName )
                    {
                    // InternalSQLParser.g:770:5: (lv_method_13_0= ruleName )
                    // InternalSQLParser.g:771:6: lv_method_13_0= ruleName
                    {

                    						newCompositeNode(grammarAccess.getIndexAccess().getMethodNameParserRuleCall_9_1_0());
                    					
                    pushFollow(FOLLOW_9);
                    lv_method_13_0=ruleName();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIndexRule());
                    						}
                    						set(
                    							current,
                    							"method",
                    							lv_method_13_0,
                    							"io.shardml.SQL.Name");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_14=(Token)match(input,LeftParenthesis,FOLLOW_22); 

            			newLeafNode(otherlv_14, grammarAccess.getIndexAccess().getLeftParenthesisKeyword_10());
            		
            // InternalSQLParser.g:793:3: ( (lv_parts_15_0= ruleIndexPart ) )
            // InternalSQLParser.g:794:4: (lv_parts_15_0= ruleIndexPart )
            {
            // InternalSQLParser.g:794:4: (lv_parts_15_0= ruleIndexPart )
            // InternalSQLParser.g:795:5: lv_parts_15_0= ruleIndexPart
            {

            					newCompositeNode(grammarAccess.getIndexAccess().getPartsIndexPartParserRuleCall_11_0());
            				
            pushFollow(FOLLOW_11);
            lv_parts_15_0=ruleIndexPart();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getIndexRule());
            					}
            					add(
            						current,
            						"parts",
            						lv_parts_15_0,
            						"io.shardml.SQL.IndexPart");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSQLParser.g:812:3: (otherlv_16= Comma ( (lv_parts_17_0= ruleIndexPart ) ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==Comma) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalSQLParser.g:813:4: otherlv_16= Comma ( (lv_parts_17_0= ruleIndexPart ) )
            	    {
            	    otherlv_16=(Token)match(input,Comma,FOLLOW_22); 

            	    				newLeafNode(otherlv_16, grammarAccess.getIndexAccess().getCommaKeyword_12_0());
            	    			
            	    // InternalSQLParser.g:817:4: ( (lv_parts_17_0= ruleIndexPart ) )
            	    // InternalSQLParser.g:818:5: (lv_parts_17_0= ruleIndexPart )
            	    {
            	    // InternalSQLParser.g:818:5: (lv_parts_17_0= ruleIndexPart )
            	    // InternalSQLParser.g:819:6: lv_parts_17_0= ruleIndexPart
            	    {

            	    						newCompositeNode(grammarAccess.getIndexAccess().getPartsIndexPartParserRuleCall_12_1_0());
            	    					
            	    pushFollow(FOLLOW_11);
            	    lv_parts_17_0=ruleIndexPart();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getIndexRule());
            	    						}
            	    						add(
            	    							current,
            	    							"parts",
            	    							lv_parts_17_0,
            	    							"io.shardml.SQL.IndexPart");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            otherlv_18=(Token)match(input,RightParenthesis,FOLLOW_23); 

            			newLeafNode(otherlv_18, grammarAccess.getIndexAccess().getRightParenthesisKeyword_13());
            		
            // InternalSQLParser.g:841:3: ( (lv_tail_19_0= ruleIndexTail ) )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==INCLUDE||LA21_0==WHERE||LA21_0==WITH) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalSQLParser.g:842:4: (lv_tail_19_0= ruleIndexTail )
                    {
                    // InternalSQLParser.g:842:4: (lv_tail_19_0= ruleIndexTail )
                    // InternalSQLParser.g:843:5: lv_tail_19_0= ruleIndexTail
                    {

                    					newCompositeNode(grammarAccess.getIndexAccess().getTailIndexTailParserRuleCall_14_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_tail_19_0=ruleIndexTail();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getIndexRule());
                    					}
                    					set(
                    						current,
                    						"tail",
                    						lv_tail_19_0,
                    						"io.shardml.SQL.IndexTail");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIndex"


    // $ANTLR start "entryRuleIndexPart"
    // InternalSQLParser.g:864:1: entryRuleIndexPart returns [EObject current=null] : iv_ruleIndexPart= ruleIndexPart EOF ;
    public final EObject entryRuleIndexPart() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIndexPart = null;


        try {
            // InternalSQLParser.g:864:50: (iv_ruleIndexPart= ruleIndexPart EOF )
            // InternalSQLParser.g:865:2: iv_ruleIndexPart= ruleIndexPart EOF
            {
             newCompositeNode(grammarAccess.getIndexPartRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIndexPart=ruleIndexPart();

            state._fsp--;

             current =iv_ruleIndexPart; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIndexPart"


    // $ANTLR start "ruleIndexPart"
    // InternalSQLParser.g:871:1: ruleIndexPart returns [EObject current=null] : ( ( ( ( ruleName ) ) | ( (lv_expression_1_0= ruleParenthesized ) ) ) ( (lv_options_2_0= ruleIndexOptions ) )? ) ;
    public final EObject ruleIndexPart() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_expression_1_0 = null;

        AntlrDatatypeRuleToken lv_options_2_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:877:2: ( ( ( ( ( ruleName ) ) | ( (lv_expression_1_0= ruleParenthesized ) ) ) ( (lv_options_2_0= ruleIndexOptions ) )? ) )
            // InternalSQLParser.g:878:2: ( ( ( ( ruleName ) ) | ( (lv_expression_1_0= ruleParenthesized ) ) ) ( (lv_options_2_0= ruleIndexOptions ) )? )
            {
            // InternalSQLParser.g:878:2: ( ( ( ( ruleName ) ) | ( (lv_expression_1_0= ruleParenthesized ) ) ) ( (lv_options_2_0= ruleIndexOptions ) )? )
            // InternalSQLParser.g:879:3: ( ( ( ruleName ) ) | ( (lv_expression_1_0= ruleParenthesized ) ) ) ( (lv_options_2_0= ruleIndexOptions ) )?
            {
            // InternalSQLParser.g:879:3: ( ( ( ruleName ) ) | ( (lv_expression_1_0= ruleParenthesized ) ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==TIMESTAMPTZ||(LA22_0>=BIGSERIAL && LA22_0<=IMMEDIATE)||(LA22_0>=PRECISION && LA22_0<=DECIMAL)||(LA22_0>=INCLUDE && LA22_0<=NUMERIC)||(LA22_0>=VARCHAR && LA22_0<=BIGINT)||(LA22_0>=DELETE && LA22_0<=STORED)||(LA22_0>=UPDATE && LA22_0<=BYTEA)||(LA22_0>=FLOAT && LA22_0<=MATCH)||(LA22_0>=CHAR && LA22_0<=DATE)||(LA22_0>=REAL && LA22_0<=UUID)||(LA22_0>=ZONE && LA22_0<=KEY)||LA22_0==SET||(LA22_0>=BY && LA22_0<=NO)||(LA22_0>=RULE_ID && LA22_0<=RULE_QUOTED_ID)) ) {
                alt22=1;
            }
            else if ( (LA22_0==LeftParenthesis) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalSQLParser.g:880:4: ( ( ruleName ) )
                    {
                    // InternalSQLParser.g:880:4: ( ( ruleName ) )
                    // InternalSQLParser.g:881:5: ( ruleName )
                    {
                    // InternalSQLParser.g:881:5: ( ruleName )
                    // InternalSQLParser.g:882:6: ruleName
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getIndexPartRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getIndexPartAccess().getColumnColumnCrossReference_0_0_0());
                    					
                    pushFollow(FOLLOW_24);
                    ruleName();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:897:4: ( (lv_expression_1_0= ruleParenthesized ) )
                    {
                    // InternalSQLParser.g:897:4: ( (lv_expression_1_0= ruleParenthesized ) )
                    // InternalSQLParser.g:898:5: (lv_expression_1_0= ruleParenthesized )
                    {
                    // InternalSQLParser.g:898:5: (lv_expression_1_0= ruleParenthesized )
                    // InternalSQLParser.g:899:6: lv_expression_1_0= ruleParenthesized
                    {

                    						newCompositeNode(grammarAccess.getIndexPartAccess().getExpressionParenthesizedParserRuleCall_0_1_0());
                    					
                    pushFollow(FOLLOW_24);
                    lv_expression_1_0=ruleParenthesized();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getIndexPartRule());
                    						}
                    						set(
                    							current,
                    							"expression",
                    							lv_expression_1_0,
                    							"io.shardml.SQL.Parenthesized");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalSQLParser.g:917:3: ( (lv_options_2_0= ruleIndexOptions ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==TIMESTAMPTZ||(LA23_0>=BIGSERIAL && LA23_0<=IMMEDIATE)||(LA23_0>=PRECISION && LA23_0<=DECIMAL)||(LA23_0>=INCLUDE && LA23_0<=NUMERIC)||(LA23_0>=VARCHAR && LA23_0<=BIGINT)||(LA23_0>=DELETE && LA23_0<=STORED)||(LA23_0>=UPDATE && LA23_0<=BYTEA)||(LA23_0>=FLOAT && LA23_0<=MATCH)||(LA23_0>=CHAR && LA23_0<=DATE)||(LA23_0>=REAL && LA23_0<=UUID)||(LA23_0>=ZONE && LA23_0<=KEY)||LA23_0==SET||(LA23_0>=BY && LA23_0<=NO)||LA23_0==LeftParenthesis||LA23_0==FullStop||(LA23_0>=RULE_ID && LA23_0<=RULE_STRING)) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSQLParser.g:918:4: (lv_options_2_0= ruleIndexOptions )
                    {
                    // InternalSQLParser.g:918:4: (lv_options_2_0= ruleIndexOptions )
                    // InternalSQLParser.g:919:5: lv_options_2_0= ruleIndexOptions
                    {

                    					newCompositeNode(grammarAccess.getIndexPartAccess().getOptionsIndexOptionsParserRuleCall_1_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_options_2_0=ruleIndexOptions();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getIndexPartRule());
                    					}
                    					set(
                    						current,
                    						"options",
                    						lv_options_2_0,
                    						"io.shardml.SQL.IndexOptions");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIndexPart"


    // $ANTLR start "entryRuleSchemaDecl"
    // InternalSQLParser.g:940:1: entryRuleSchemaDecl returns [EObject current=null] : iv_ruleSchemaDecl= ruleSchemaDecl EOF ;
    public final EObject entryRuleSchemaDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSchemaDecl = null;


        try {
            // InternalSQLParser.g:940:51: (iv_ruleSchemaDecl= ruleSchemaDecl EOF )
            // InternalSQLParser.g:941:2: iv_ruleSchemaDecl= ruleSchemaDecl EOF
            {
             newCompositeNode(grammarAccess.getSchemaDeclRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSchemaDecl=ruleSchemaDecl();

            state._fsp--;

             current =iv_ruleSchemaDecl; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSchemaDecl"


    // $ANTLR start "ruleSchemaDecl"
    // InternalSQLParser.g:947:1: ruleSchemaDecl returns [EObject current=null] : (otherlv_0= CREATE otherlv_1= SCHEMA ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( (lv_name_5_0= ruleName ) ) ) ;
    public final EObject ruleSchemaDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_ifNotExists_2_0=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_name_5_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:953:2: ( (otherlv_0= CREATE otherlv_1= SCHEMA ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( (lv_name_5_0= ruleName ) ) ) )
            // InternalSQLParser.g:954:2: (otherlv_0= CREATE otherlv_1= SCHEMA ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( (lv_name_5_0= ruleName ) ) )
            {
            // InternalSQLParser.g:954:2: (otherlv_0= CREATE otherlv_1= SCHEMA ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( (lv_name_5_0= ruleName ) ) )
            // InternalSQLParser.g:955:3: otherlv_0= CREATE otherlv_1= SCHEMA ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( (lv_name_5_0= ruleName ) )
            {
            otherlv_0=(Token)match(input,CREATE,FOLLOW_25); 

            			newLeafNode(otherlv_0, grammarAccess.getSchemaDeclAccess().getCREATEKeyword_0());
            		
            otherlv_1=(Token)match(input,SCHEMA,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getSchemaDeclAccess().getSCHEMAKeyword_1());
            		
            // InternalSQLParser.g:963:3: ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==IF) ) {
                int LA24_1 = input.LA(2);

                if ( (LA24_1==NOT) ) {
                    alt24=1;
                }
            }
            switch (alt24) {
                case 1 :
                    // InternalSQLParser.g:964:4: ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS
                    {
                    // InternalSQLParser.g:964:4: ( (lv_ifNotExists_2_0= IF ) )
                    // InternalSQLParser.g:965:5: (lv_ifNotExists_2_0= IF )
                    {
                    // InternalSQLParser.g:965:5: (lv_ifNotExists_2_0= IF )
                    // InternalSQLParser.g:966:6: lv_ifNotExists_2_0= IF
                    {
                    lv_ifNotExists_2_0=(Token)match(input,IF,FOLLOW_6); 

                    						newLeafNode(lv_ifNotExists_2_0, grammarAccess.getSchemaDeclAccess().getIfNotExistsIFKeyword_2_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getSchemaDeclRule());
                    						}
                    						setWithLastConsumed(current, "ifNotExists", lv_ifNotExists_2_0 != null, "IF");
                    					

                    }


                    }

                    otherlv_3=(Token)match(input,NOT,FOLLOW_7); 

                    				newLeafNode(otherlv_3, grammarAccess.getSchemaDeclAccess().getNOTKeyword_2_1());
                    			
                    otherlv_4=(Token)match(input,EXISTS,FOLLOW_5); 

                    				newLeafNode(otherlv_4, grammarAccess.getSchemaDeclAccess().getEXISTSKeyword_2_2());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:987:3: ( (lv_name_5_0= ruleName ) )
            // InternalSQLParser.g:988:4: (lv_name_5_0= ruleName )
            {
            // InternalSQLParser.g:988:4: (lv_name_5_0= ruleName )
            // InternalSQLParser.g:989:5: lv_name_5_0= ruleName
            {

            					newCompositeNode(grammarAccess.getSchemaDeclAccess().getNameNameParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_name_5_0=ruleName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSchemaDeclRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_5_0,
            						"io.shardml.SQL.Name");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSchemaDecl"


    // $ANTLR start "entryRuleStatement"
    // InternalSQLParser.g:1010:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalSQLParser.g:1010:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalSQLParser.g:1011:2: iv_ruleStatement= ruleStatement EOF
            {
             newCompositeNode(grammarAccess.getStatementRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStatement=ruleStatement();

            state._fsp--;

             current =iv_ruleStatement; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatement"


    // $ANTLR start "ruleStatement"
    // InternalSQLParser.g:1017:1: ruleStatement returns [EObject current=null] : ( ( ( ( ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) ) ) | ( ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) ) ) | ( ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )? ) ) otherlv_6= Semicolon ) | ( (lv_meta_7_0= RULE_PSQL_META ) ) ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        Token lv_verb_0_0=null;
        Token lv_verb_2_0=null;
        Token otherlv_6=null;
        Token lv_meta_7_0=null;
        AntlrDatatypeRuleToken lv_body_1_0 = null;

        AntlrDatatypeRuleToken lv_body_3_0 = null;

        AntlrDatatypeRuleToken lv_verb_4_0 = null;

        AntlrDatatypeRuleToken lv_body_5_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1023:2: ( ( ( ( ( ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) ) ) | ( ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) ) ) | ( ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )? ) ) otherlv_6= Semicolon ) | ( (lv_meta_7_0= RULE_PSQL_META ) ) ) )
            // InternalSQLParser.g:1024:2: ( ( ( ( ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) ) ) | ( ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) ) ) | ( ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )? ) ) otherlv_6= Semicolon ) | ( (lv_meta_7_0= RULE_PSQL_META ) ) )
            {
            // InternalSQLParser.g:1024:2: ( ( ( ( ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) ) ) | ( ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) ) ) | ( ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )? ) ) otherlv_6= Semicolon ) | ( (lv_meta_7_0= RULE_PSQL_META ) ) )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=CREATE && LA27_0<=DELETE)||(LA27_0>=UPDATE && LA27_0<=ALTER)||LA27_0==SET||LA27_0==RULE_ID||LA27_0==RULE_ANY_OTHER) ) {
                alt27=1;
            }
            else if ( (LA27_0==RULE_PSQL_META) ) {
                alt27=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // InternalSQLParser.g:1025:3: ( ( ( ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) ) ) | ( ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) ) ) | ( ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )? ) ) otherlv_6= Semicolon )
                    {
                    // InternalSQLParser.g:1025:3: ( ( ( ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) ) ) | ( ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) ) ) | ( ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )? ) ) otherlv_6= Semicolon )
                    // InternalSQLParser.g:1026:4: ( ( ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) ) ) | ( ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) ) ) | ( ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )? ) ) otherlv_6= Semicolon
                    {
                    // InternalSQLParser.g:1026:4: ( ( ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) ) ) | ( ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) ) ) | ( ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )? ) )
                    int alt26=3;
                    switch ( input.LA(1) ) {
                    case CREATE:
                        {
                        alt26=1;
                        }
                        break;
                    case ALTER:
                        {
                        alt26=2;
                        }
                        break;
                    case DELETE:
                    case UPDATE:
                    case SET:
                    case RULE_ID:
                    case RULE_ANY_OTHER:
                        {
                        alt26=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 26, 0, input);

                        throw nvae;
                    }

                    switch (alt26) {
                        case 1 :
                            // InternalSQLParser.g:1027:5: ( ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) ) )
                            {
                            // InternalSQLParser.g:1027:5: ( ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) ) )
                            // InternalSQLParser.g:1028:6: ( (lv_verb_0_0= CREATE ) ) ( (lv_body_1_0= ruleCreateBody ) )
                            {
                            // InternalSQLParser.g:1028:6: ( (lv_verb_0_0= CREATE ) )
                            // InternalSQLParser.g:1029:7: (lv_verb_0_0= CREATE )
                            {
                            // InternalSQLParser.g:1029:7: (lv_verb_0_0= CREATE )
                            // InternalSQLParser.g:1030:8: lv_verb_0_0= CREATE
                            {
                            lv_verb_0_0=(Token)match(input,CREATE,FOLLOW_26); 

                            								newLeafNode(lv_verb_0_0, grammarAccess.getStatementAccess().getVerbCREATEKeyword_0_0_0_0_0());
                            							

                            								if (current==null) {
                            									current = createModelElement(grammarAccess.getStatementRule());
                            								}
                            								setWithLastConsumed(current, "verb", lv_verb_0_0, "CREATE");
                            							

                            }


                            }

                            // InternalSQLParser.g:1042:6: ( (lv_body_1_0= ruleCreateBody ) )
                            // InternalSQLParser.g:1043:7: (lv_body_1_0= ruleCreateBody )
                            {
                            // InternalSQLParser.g:1043:7: (lv_body_1_0= ruleCreateBody )
                            // InternalSQLParser.g:1044:8: lv_body_1_0= ruleCreateBody
                            {

                            								newCompositeNode(grammarAccess.getStatementAccess().getBodyCreateBodyParserRuleCall_0_0_0_1_0());
                            							
                            pushFollow(FOLLOW_16);
                            lv_body_1_0=ruleCreateBody();

                            state._fsp--;


                            								if (current==null) {
                            									current = createModelElementForParent(grammarAccess.getStatementRule());
                            								}
                            								set(
                            									current,
                            									"body",
                            									lv_body_1_0,
                            									"io.shardml.SQL.CreateBody");
                            								afterParserOrEnumRuleCall();
                            							

                            }


                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalSQLParser.g:1063:5: ( ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) ) )
                            {
                            // InternalSQLParser.g:1063:5: ( ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) ) )
                            // InternalSQLParser.g:1064:6: ( (lv_verb_2_0= ALTER ) ) ( (lv_body_3_0= ruleAlterBody ) )
                            {
                            // InternalSQLParser.g:1064:6: ( (lv_verb_2_0= ALTER ) )
                            // InternalSQLParser.g:1065:7: (lv_verb_2_0= ALTER )
                            {
                            // InternalSQLParser.g:1065:7: (lv_verb_2_0= ALTER )
                            // InternalSQLParser.g:1066:8: lv_verb_2_0= ALTER
                            {
                            lv_verb_2_0=(Token)match(input,ALTER,FOLLOW_27); 

                            								newLeafNode(lv_verb_2_0, grammarAccess.getStatementAccess().getVerbALTERKeyword_0_0_1_0_0());
                            							

                            								if (current==null) {
                            									current = createModelElement(grammarAccess.getStatementRule());
                            								}
                            								setWithLastConsumed(current, "verb", lv_verb_2_0, "ALTER");
                            							

                            }


                            }

                            // InternalSQLParser.g:1078:6: ( (lv_body_3_0= ruleAlterBody ) )
                            // InternalSQLParser.g:1079:7: (lv_body_3_0= ruleAlterBody )
                            {
                            // InternalSQLParser.g:1079:7: (lv_body_3_0= ruleAlterBody )
                            // InternalSQLParser.g:1080:8: lv_body_3_0= ruleAlterBody
                            {

                            								newCompositeNode(grammarAccess.getStatementAccess().getBodyAlterBodyParserRuleCall_0_0_1_1_0());
                            							
                            pushFollow(FOLLOW_16);
                            lv_body_3_0=ruleAlterBody();

                            state._fsp--;


                            								if (current==null) {
                            									current = createModelElementForParent(grammarAccess.getStatementRule());
                            								}
                            								set(
                            									current,
                            									"body",
                            									lv_body_3_0,
                            									"io.shardml.SQL.AlterBody");
                            								afterParserOrEnumRuleCall();
                            							

                            }


                            }


                            }


                            }
                            break;
                        case 3 :
                            // InternalSQLParser.g:1099:5: ( ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )? )
                            {
                            // InternalSQLParser.g:1099:5: ( ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )? )
                            // InternalSQLParser.g:1100:6: ( (lv_verb_4_0= ruleVerb ) ) ( (lv_body_5_0= ruleRawTokens ) )?
                            {
                            // InternalSQLParser.g:1100:6: ( (lv_verb_4_0= ruleVerb ) )
                            // InternalSQLParser.g:1101:7: (lv_verb_4_0= ruleVerb )
                            {
                            // InternalSQLParser.g:1101:7: (lv_verb_4_0= ruleVerb )
                            // InternalSQLParser.g:1102:8: lv_verb_4_0= ruleVerb
                            {

                            								newCompositeNode(grammarAccess.getStatementAccess().getVerbVerbParserRuleCall_0_0_2_0_0());
                            							
                            pushFollow(FOLLOW_28);
                            lv_verb_4_0=ruleVerb();

                            state._fsp--;


                            								if (current==null) {
                            									current = createModelElementForParent(grammarAccess.getStatementRule());
                            								}
                            								set(
                            									current,
                            									"verb",
                            									lv_verb_4_0,
                            									"io.shardml.SQL.Verb");
                            								afterParserOrEnumRuleCall();
                            							

                            }


                            }

                            // InternalSQLParser.g:1119:6: ( (lv_body_5_0= ruleRawTokens ) )?
                            int alt25=2;
                            int LA25_0 = input.LA(1);

                            if ( ((LA25_0>=TIMESTAMPTZ && LA25_0<=FullStop)||(LA25_0>=RULE_ID && LA25_0<=RULE_DOLLAR_STRING)||LA25_0==RULE_ANY_OTHER) ) {
                                alt25=1;
                            }
                            switch (alt25) {
                                case 1 :
                                    // InternalSQLParser.g:1120:7: (lv_body_5_0= ruleRawTokens )
                                    {
                                    // InternalSQLParser.g:1120:7: (lv_body_5_0= ruleRawTokens )
                                    // InternalSQLParser.g:1121:8: lv_body_5_0= ruleRawTokens
                                    {

                                    								newCompositeNode(grammarAccess.getStatementAccess().getBodyRawTokensParserRuleCall_0_0_2_1_0());
                                    							
                                    pushFollow(FOLLOW_16);
                                    lv_body_5_0=ruleRawTokens();

                                    state._fsp--;


                                    								if (current==null) {
                                    									current = createModelElementForParent(grammarAccess.getStatementRule());
                                    								}
                                    								set(
                                    									current,
                                    									"body",
                                    									lv_body_5_0,
                                    									"io.shardml.SQL.RawTokens");
                                    								afterParserOrEnumRuleCall();
                                    							

                                    }


                                    }
                                    break;

                            }


                            }


                            }
                            break;

                    }

                    otherlv_6=(Token)match(input,Semicolon,FOLLOW_2); 

                    				newLeafNode(otherlv_6, grammarAccess.getStatementAccess().getSemicolonKeyword_0_1());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:1146:3: ( (lv_meta_7_0= RULE_PSQL_META ) )
                    {
                    // InternalSQLParser.g:1146:3: ( (lv_meta_7_0= RULE_PSQL_META ) )
                    // InternalSQLParser.g:1147:4: (lv_meta_7_0= RULE_PSQL_META )
                    {
                    // InternalSQLParser.g:1147:4: (lv_meta_7_0= RULE_PSQL_META )
                    // InternalSQLParser.g:1148:5: lv_meta_7_0= RULE_PSQL_META
                    {
                    lv_meta_7_0=(Token)match(input,RULE_PSQL_META,FOLLOW_2); 

                    					newLeafNode(lv_meta_7_0, grammarAccess.getStatementAccess().getMetaPSQL_METATerminalRuleCall_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getStatementRule());
                    					}
                    					setWithLastConsumed(
                    						current,
                    						"meta",
                    						lv_meta_7_0,
                    						"io.shardml.SQL.PSQL_META");
                    				

                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatement"


    // $ANTLR start "entryRuleColumn"
    // InternalSQLParser.g:1168:1: entryRuleColumn returns [EObject current=null] : iv_ruleColumn= ruleColumn EOF ;
    public final EObject entryRuleColumn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumn = null;


        try {
            // InternalSQLParser.g:1168:47: (iv_ruleColumn= ruleColumn EOF )
            // InternalSQLParser.g:1169:2: iv_ruleColumn= ruleColumn EOF
            {
             newCompositeNode(grammarAccess.getColumnRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleColumn=ruleColumn();

            state._fsp--;

             current =iv_ruleColumn; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleColumn"


    // $ANTLR start "ruleColumn"
    // InternalSQLParser.g:1175:1: ruleColumn returns [EObject current=null] : ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* ) ;
    public final EObject ruleColumn() throws RecognitionException {
        EObject current = null;

        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        Enumerator lv_simpleType_1_0 = null;

        EObject lv_varchar_2_0 = null;

        EObject lv_numeric_3_0 = null;

        EObject lv_char_4_0 = null;

        Enumerator lv_simpleType_6_0 = null;

        Enumerator lv_simpleType_10_0 = null;

        Enumerator lv_simpleType_14_0 = null;

        EObject lv_constraint_15_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1181:2: ( ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* ) )
            // InternalSQLParser.g:1182:2: ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* )
            {
            // InternalSQLParser.g:1182:2: ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* )
            // InternalSQLParser.g:1183:3: ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )*
            {
            // InternalSQLParser.g:1183:3: ( (lv_name_0_0= ruleName ) )
            // InternalSQLParser.g:1184:4: (lv_name_0_0= ruleName )
            {
            // InternalSQLParser.g:1184:4: (lv_name_0_0= ruleName )
            // InternalSQLParser.g:1185:5: lv_name_0_0= ruleName
            {

            					newCompositeNode(grammarAccess.getColumnAccess().getNameNameParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_29);
            lv_name_0_0=ruleName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getColumnRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_0_0,
            						"io.shardml.SQL.Name");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSQLParser.g:1202:3: ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) )
            int alt28=7;
            alt28 = dfa28.predict(input);
            switch (alt28) {
                case 1 :
                    // InternalSQLParser.g:1203:4: ( (lv_simpleType_1_0= ruleAllTypes ) )
                    {
                    // InternalSQLParser.g:1203:4: ( (lv_simpleType_1_0= ruleAllTypes ) )
                    // InternalSQLParser.g:1204:5: (lv_simpleType_1_0= ruleAllTypes )
                    {
                    // InternalSQLParser.g:1204:5: (lv_simpleType_1_0= ruleAllTypes )
                    // InternalSQLParser.g:1205:6: lv_simpleType_1_0= ruleAllTypes
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeAllTypesEnumRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_30);
                    lv_simpleType_1_0=ruleAllTypes();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getColumnRule());
                    						}
                    						set(
                    							current,
                    							"simpleType",
                    							lv_simpleType_1_0,
                    							"io.shardml.SQL.AllTypes");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:1223:4: ( (lv_varchar_2_0= ruleVarchar ) )
                    {
                    // InternalSQLParser.g:1223:4: ( (lv_varchar_2_0= ruleVarchar ) )
                    // InternalSQLParser.g:1224:5: (lv_varchar_2_0= ruleVarchar )
                    {
                    // InternalSQLParser.g:1224:5: (lv_varchar_2_0= ruleVarchar )
                    // InternalSQLParser.g:1225:6: lv_varchar_2_0= ruleVarchar
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getVarcharVarcharParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_30);
                    lv_varchar_2_0=ruleVarchar();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getColumnRule());
                    						}
                    						set(
                    							current,
                    							"varchar",
                    							lv_varchar_2_0,
                    							"io.shardml.SQL.Varchar");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:1243:4: ( (lv_numeric_3_0= ruleNumeric ) )
                    {
                    // InternalSQLParser.g:1243:4: ( (lv_numeric_3_0= ruleNumeric ) )
                    // InternalSQLParser.g:1244:5: (lv_numeric_3_0= ruleNumeric )
                    {
                    // InternalSQLParser.g:1244:5: (lv_numeric_3_0= ruleNumeric )
                    // InternalSQLParser.g:1245:6: lv_numeric_3_0= ruleNumeric
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getNumericNumericParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_30);
                    lv_numeric_3_0=ruleNumeric();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getColumnRule());
                    						}
                    						set(
                    							current,
                    							"numeric",
                    							lv_numeric_3_0,
                    							"io.shardml.SQL.Numeric");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:1263:4: ( (lv_char_4_0= ruleChar ) )
                    {
                    // InternalSQLParser.g:1263:4: ( (lv_char_4_0= ruleChar ) )
                    // InternalSQLParser.g:1264:5: (lv_char_4_0= ruleChar )
                    {
                    // InternalSQLParser.g:1264:5: (lv_char_4_0= ruleChar )
                    // InternalSQLParser.g:1265:6: lv_char_4_0= ruleChar
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getCharCharParserRuleCall_1_3_0());
                    					
                    pushFollow(FOLLOW_30);
                    lv_char_4_0=ruleChar();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getColumnRule());
                    						}
                    						set(
                    							current,
                    							"char",
                    							lv_char_4_0,
                    							"io.shardml.SQL.Char");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:1283:4: (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) )
                    {
                    // InternalSQLParser.g:1283:4: (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) )
                    // InternalSQLParser.g:1284:5: otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) )
                    {
                    otherlv_5=(Token)match(input,DOUBLE,FOLLOW_31); 

                    					newLeafNode(otherlv_5, grammarAccess.getColumnAccess().getDOUBLEKeyword_1_4_0());
                    				
                    // InternalSQLParser.g:1288:5: ( (lv_simpleType_6_0= ruleDoublePrecision ) )
                    // InternalSQLParser.g:1289:6: (lv_simpleType_6_0= ruleDoublePrecision )
                    {
                    // InternalSQLParser.g:1289:6: (lv_simpleType_6_0= ruleDoublePrecision )
                    // InternalSQLParser.g:1290:7: lv_simpleType_6_0= ruleDoublePrecision
                    {

                    							newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeDoublePrecisionEnumRuleCall_1_4_1_0());
                    						
                    pushFollow(FOLLOW_30);
                    lv_simpleType_6_0=ruleDoublePrecision();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getColumnRule());
                    							}
                    							set(
                    								current,
                    								"simpleType",
                    								lv_simpleType_6_0,
                    								"io.shardml.SQL.DoublePrecision");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:1309:4: (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) )
                    {
                    // InternalSQLParser.g:1309:4: (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) )
                    // InternalSQLParser.g:1310:5: otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) )
                    {
                    otherlv_7=(Token)match(input,TIMESTAMP,FOLLOW_32); 

                    					newLeafNode(otherlv_7, grammarAccess.getColumnAccess().getTIMESTAMPKeyword_1_5_0());
                    				
                    otherlv_8=(Token)match(input,WITHOUT,FOLLOW_33); 

                    					newLeafNode(otherlv_8, grammarAccess.getColumnAccess().getWITHOUTKeyword_1_5_1());
                    				
                    otherlv_9=(Token)match(input,TIME,FOLLOW_34); 

                    					newLeafNode(otherlv_9, grammarAccess.getColumnAccess().getTIMEKeyword_1_5_2());
                    				
                    // InternalSQLParser.g:1322:5: ( (lv_simpleType_10_0= ruleZoneNoTz ) )
                    // InternalSQLParser.g:1323:6: (lv_simpleType_10_0= ruleZoneNoTz )
                    {
                    // InternalSQLParser.g:1323:6: (lv_simpleType_10_0= ruleZoneNoTz )
                    // InternalSQLParser.g:1324:7: lv_simpleType_10_0= ruleZoneNoTz
                    {

                    							newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeZoneNoTzEnumRuleCall_1_5_3_0());
                    						
                    pushFollow(FOLLOW_30);
                    lv_simpleType_10_0=ruleZoneNoTz();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getColumnRule());
                    							}
                    							set(
                    								current,
                    								"simpleType",
                    								lv_simpleType_10_0,
                    								"io.shardml.SQL.ZoneNoTz");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:1343:4: (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) )
                    {
                    // InternalSQLParser.g:1343:4: (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) )
                    // InternalSQLParser.g:1344:5: otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) )
                    {
                    otherlv_11=(Token)match(input,TIMESTAMP,FOLLOW_35); 

                    					newLeafNode(otherlv_11, grammarAccess.getColumnAccess().getTIMESTAMPKeyword_1_6_0());
                    				
                    otherlv_12=(Token)match(input,WITH,FOLLOW_33); 

                    					newLeafNode(otherlv_12, grammarAccess.getColumnAccess().getWITHKeyword_1_6_1());
                    				
                    otherlv_13=(Token)match(input,TIME,FOLLOW_34); 

                    					newLeafNode(otherlv_13, grammarAccess.getColumnAccess().getTIMEKeyword_1_6_2());
                    				
                    // InternalSQLParser.g:1356:5: ( (lv_simpleType_14_0= ruleZoneTz ) )
                    // InternalSQLParser.g:1357:6: (lv_simpleType_14_0= ruleZoneTz )
                    {
                    // InternalSQLParser.g:1357:6: (lv_simpleType_14_0= ruleZoneTz )
                    // InternalSQLParser.g:1358:7: lv_simpleType_14_0= ruleZoneTz
                    {

                    							newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeZoneTzEnumRuleCall_1_6_3_0());
                    						
                    pushFollow(FOLLOW_30);
                    lv_simpleType_14_0=ruleZoneTz();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getColumnRule());
                    							}
                    							set(
                    								current,
                    								"simpleType",
                    								lv_simpleType_14_0,
                    								"io.shardml.SQL.ZoneTz");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalSQLParser.g:1377:3: ( (lv_constraint_15_0= ruleConstraint ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==CONSTRAINT||LA29_0==REFERENCES||LA29_0==GENERATED||(LA29_0>=DEFAULT && LA29_0<=FOREIGN)||LA29_0==PRIMARY||LA29_0==UNIQUE||LA29_0==CHECK||LA29_0==NULL||LA29_0==NOT) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // InternalSQLParser.g:1378:4: (lv_constraint_15_0= ruleConstraint )
            	    {
            	    // InternalSQLParser.g:1378:4: (lv_constraint_15_0= ruleConstraint )
            	    // InternalSQLParser.g:1379:5: lv_constraint_15_0= ruleConstraint
            	    {

            	    					newCompositeNode(grammarAccess.getColumnAccess().getConstraintConstraintParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_30);
            	    lv_constraint_15_0=ruleConstraint();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getColumnRule());
            	    					}
            	    					add(
            	    						current,
            	    						"constraint",
            	    						lv_constraint_15_0,
            	    						"io.shardml.SQL.Constraint");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColumn"


    // $ANTLR start "entryRuleVarchar"
    // InternalSQLParser.g:1400:1: entryRuleVarchar returns [EObject current=null] : iv_ruleVarchar= ruleVarchar EOF ;
    public final EObject entryRuleVarchar() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarchar = null;


        try {
            // InternalSQLParser.g:1400:48: (iv_ruleVarchar= ruleVarchar EOF )
            // InternalSQLParser.g:1401:2: iv_ruleVarchar= ruleVarchar EOF
            {
             newCompositeNode(grammarAccess.getVarcharRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVarchar=ruleVarchar();

            state._fsp--;

             current =iv_ruleVarchar; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVarchar"


    // $ANTLR start "ruleVarchar"
    // InternalSQLParser.g:1407:1: ruleVarchar returns [EObject current=null] : ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis ) ;
    public final EObject ruleVarchar() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_varying_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_size_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalSQLParser.g:1413:2: ( ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis ) )
            // InternalSQLParser.g:1414:2: ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis )
            {
            // InternalSQLParser.g:1414:2: ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis )
            // InternalSQLParser.g:1415:3: (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis
            {
            // InternalSQLParser.g:1415:3: (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==VARCHAR) ) {
                alt30=1;
            }
            else if ( (LA30_0==CHARACTER) ) {
                alt30=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // InternalSQLParser.g:1416:4: otherlv_0= VARCHAR
                    {
                    otherlv_0=(Token)match(input,VARCHAR,FOLLOW_9); 

                    				newLeafNode(otherlv_0, grammarAccess.getVarcharAccess().getVARCHARKeyword_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:1421:4: ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING )
                    {
                    // InternalSQLParser.g:1421:4: ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING )
                    // InternalSQLParser.g:1422:5: ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING
                    {
                    // InternalSQLParser.g:1422:5: ( (lv_varying_1_0= CHARACTER ) )
                    // InternalSQLParser.g:1423:6: (lv_varying_1_0= CHARACTER )
                    {
                    // InternalSQLParser.g:1423:6: (lv_varying_1_0= CHARACTER )
                    // InternalSQLParser.g:1424:7: lv_varying_1_0= CHARACTER
                    {
                    lv_varying_1_0=(Token)match(input,CHARACTER,FOLLOW_36); 

                    							newLeafNode(lv_varying_1_0, grammarAccess.getVarcharAccess().getVaryingCHARACTERKeyword_0_1_0_0());
                    						

                    							if (current==null) {
                    								current = createModelElement(grammarAccess.getVarcharRule());
                    							}
                    							setWithLastConsumed(current, "varying", lv_varying_1_0 != null, "CHARACTER");
                    						

                    }


                    }

                    otherlv_2=(Token)match(input,VARYING,FOLLOW_9); 

                    					newLeafNode(otherlv_2, grammarAccess.getVarcharAccess().getVARYINGKeyword_0_1_1());
                    				

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_37); 

            			newLeafNode(otherlv_3, grammarAccess.getVarcharAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:1446:3: ( (lv_size_4_0= RULE_INT ) )
            // InternalSQLParser.g:1447:4: (lv_size_4_0= RULE_INT )
            {
            // InternalSQLParser.g:1447:4: (lv_size_4_0= RULE_INT )
            // InternalSQLParser.g:1448:5: lv_size_4_0= RULE_INT
            {
            lv_size_4_0=(Token)match(input,RULE_INT,FOLLOW_38); 

            					newLeafNode(lv_size_4_0, grammarAccess.getVarcharAccess().getSizeINTTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVarcharRule());
            					}
            					setWithLastConsumed(
            						current,
            						"size",
            						lv_size_4_0,
            						"io.shardml.SQL.INT");
            				

            }


            }

            otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getVarcharAccess().getRightParenthesisKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVarchar"


    // $ANTLR start "entryRuleNumeric"
    // InternalSQLParser.g:1472:1: entryRuleNumeric returns [EObject current=null] : iv_ruleNumeric= ruleNumeric EOF ;
    public final EObject entryRuleNumeric() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumeric = null;


        try {
            // InternalSQLParser.g:1472:48: (iv_ruleNumeric= ruleNumeric EOF )
            // InternalSQLParser.g:1473:2: iv_ruleNumeric= ruleNumeric EOF
            {
             newCompositeNode(grammarAccess.getNumericRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNumeric=ruleNumeric();

            state._fsp--;

             current =iv_ruleNumeric; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNumeric"


    // $ANTLR start "ruleNumeric"
    // InternalSQLParser.g:1479:1: ruleNumeric returns [EObject current=null] : ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis ) ;
    public final EObject ruleNumeric() throws RecognitionException {
        EObject current = null;

        Token lv_decimal_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_precision_3_0=null;
        Token otherlv_4=null;
        Token lv_scale_5_0=null;
        Token otherlv_6=null;


        	enterRule();

        try {
            // InternalSQLParser.g:1485:2: ( ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis ) )
            // InternalSQLParser.g:1486:2: ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis )
            {
            // InternalSQLParser.g:1486:2: ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis )
            // InternalSQLParser.g:1487:3: ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis
            {
            // InternalSQLParser.g:1487:3: ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==DECIMAL) ) {
                alt31=1;
            }
            else if ( (LA31_0==NUMERIC) ) {
                alt31=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // InternalSQLParser.g:1488:4: ( (lv_decimal_0_0= DECIMAL ) )
                    {
                    // InternalSQLParser.g:1488:4: ( (lv_decimal_0_0= DECIMAL ) )
                    // InternalSQLParser.g:1489:5: (lv_decimal_0_0= DECIMAL )
                    {
                    // InternalSQLParser.g:1489:5: (lv_decimal_0_0= DECIMAL )
                    // InternalSQLParser.g:1490:6: lv_decimal_0_0= DECIMAL
                    {
                    lv_decimal_0_0=(Token)match(input,DECIMAL,FOLLOW_9); 

                    						newLeafNode(lv_decimal_0_0, grammarAccess.getNumericAccess().getDecimalDECIMALKeyword_0_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getNumericRule());
                    						}
                    						setWithLastConsumed(current, "decimal", lv_decimal_0_0 != null, "DECIMAL");
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:1503:4: otherlv_1= NUMERIC
                    {
                    otherlv_1=(Token)match(input,NUMERIC,FOLLOW_9); 

                    				newLeafNode(otherlv_1, grammarAccess.getNumericAccess().getNUMERICKeyword_0_1());
                    			

                    }
                    break;

            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_37); 

            			newLeafNode(otherlv_2, grammarAccess.getNumericAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:1512:3: ( (lv_precision_3_0= RULE_INT ) )
            // InternalSQLParser.g:1513:4: (lv_precision_3_0= RULE_INT )
            {
            // InternalSQLParser.g:1513:4: (lv_precision_3_0= RULE_INT )
            // InternalSQLParser.g:1514:5: lv_precision_3_0= RULE_INT
            {
            lv_precision_3_0=(Token)match(input,RULE_INT,FOLLOW_11); 

            					newLeafNode(lv_precision_3_0, grammarAccess.getNumericAccess().getPrecisionINTTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNumericRule());
            					}
            					setWithLastConsumed(
            						current,
            						"precision",
            						lv_precision_3_0,
            						"io.shardml.SQL.INT");
            				

            }


            }

            // InternalSQLParser.g:1530:3: (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==Comma) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalSQLParser.g:1531:4: otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) )
                    {
                    otherlv_4=(Token)match(input,Comma,FOLLOW_37); 

                    				newLeafNode(otherlv_4, grammarAccess.getNumericAccess().getCommaKeyword_3_0());
                    			
                    // InternalSQLParser.g:1535:4: ( (lv_scale_5_0= RULE_INT ) )
                    // InternalSQLParser.g:1536:5: (lv_scale_5_0= RULE_INT )
                    {
                    // InternalSQLParser.g:1536:5: (lv_scale_5_0= RULE_INT )
                    // InternalSQLParser.g:1537:6: lv_scale_5_0= RULE_INT
                    {
                    lv_scale_5_0=(Token)match(input,RULE_INT,FOLLOW_38); 

                    						newLeafNode(lv_scale_5_0, grammarAccess.getNumericAccess().getScaleINTTerminalRuleCall_3_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getNumericRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"scale",
                    							lv_scale_5_0,
                    							"io.shardml.SQL.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getNumericAccess().getRightParenthesisKeyword_4());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNumeric"


    // $ANTLR start "entryRuleChar"
    // InternalSQLParser.g:1562:1: entryRuleChar returns [EObject current=null] : iv_ruleChar= ruleChar EOF ;
    public final EObject entryRuleChar() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleChar = null;


        try {
            // InternalSQLParser.g:1562:45: (iv_ruleChar= ruleChar EOF )
            // InternalSQLParser.g:1563:2: iv_ruleChar= ruleChar EOF
            {
             newCompositeNode(grammarAccess.getCharRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleChar=ruleChar();

            state._fsp--;

             current =iv_ruleChar; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleChar"


    // $ANTLR start "ruleChar"
    // InternalSQLParser.g:1569:1: ruleChar returns [EObject current=null] : ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis ) ;
    public final EObject ruleChar() throws RecognitionException {
        EObject current = null;

        Token lv_character_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_size_3_0=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalSQLParser.g:1575:2: ( ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis ) )
            // InternalSQLParser.g:1576:2: ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis )
            {
            // InternalSQLParser.g:1576:2: ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis )
            // InternalSQLParser.g:1577:3: ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis
            {
            // InternalSQLParser.g:1577:3: ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR )
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==CHARACTER) ) {
                alt33=1;
            }
            else if ( (LA33_0==CHAR) ) {
                alt33=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }
            switch (alt33) {
                case 1 :
                    // InternalSQLParser.g:1578:4: ( (lv_character_0_0= CHARACTER ) )
                    {
                    // InternalSQLParser.g:1578:4: ( (lv_character_0_0= CHARACTER ) )
                    // InternalSQLParser.g:1579:5: (lv_character_0_0= CHARACTER )
                    {
                    // InternalSQLParser.g:1579:5: (lv_character_0_0= CHARACTER )
                    // InternalSQLParser.g:1580:6: lv_character_0_0= CHARACTER
                    {
                    lv_character_0_0=(Token)match(input,CHARACTER,FOLLOW_9); 

                    						newLeafNode(lv_character_0_0, grammarAccess.getCharAccess().getCharacterCHARACTERKeyword_0_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getCharRule());
                    						}
                    						setWithLastConsumed(current, "character", lv_character_0_0 != null, "CHARACTER");
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:1593:4: otherlv_1= CHAR
                    {
                    otherlv_1=(Token)match(input,CHAR,FOLLOW_9); 

                    				newLeafNode(otherlv_1, grammarAccess.getCharAccess().getCHARKeyword_0_1());
                    			

                    }
                    break;

            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_37); 

            			newLeafNode(otherlv_2, grammarAccess.getCharAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:1602:3: ( (lv_size_3_0= RULE_INT ) )
            // InternalSQLParser.g:1603:4: (lv_size_3_0= RULE_INT )
            {
            // InternalSQLParser.g:1603:4: (lv_size_3_0= RULE_INT )
            // InternalSQLParser.g:1604:5: lv_size_3_0= RULE_INT
            {
            lv_size_3_0=(Token)match(input,RULE_INT,FOLLOW_38); 

            					newLeafNode(lv_size_3_0, grammarAccess.getCharAccess().getSizeINTTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCharRule());
            					}
            					setWithLastConsumed(
            						current,
            						"size",
            						lv_size_3_0,
            						"io.shardml.SQL.INT");
            				

            }


            }

            otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getCharAccess().getRightParenthesisKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleChar"


    // $ANTLR start "ruleConstraintName"
    // InternalSQLParser.g:1629:1: ruleConstraintName[EObject in_current] returns [EObject current=in_current] : (otherlv_0= CONSTRAINT ( (lv_name_1_0= ruleName ) ) )? ;
    public final EObject ruleConstraintName(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1635:2: ( (otherlv_0= CONSTRAINT ( (lv_name_1_0= ruleName ) ) )? )
            // InternalSQLParser.g:1636:2: (otherlv_0= CONSTRAINT ( (lv_name_1_0= ruleName ) ) )?
            {
            // InternalSQLParser.g:1636:2: (otherlv_0= CONSTRAINT ( (lv_name_1_0= ruleName ) ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==CONSTRAINT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalSQLParser.g:1637:3: otherlv_0= CONSTRAINT ( (lv_name_1_0= ruleName ) )
                    {
                    otherlv_0=(Token)match(input,CONSTRAINT,FOLLOW_5); 

                    			newLeafNode(otherlv_0, grammarAccess.getConstraintNameAccess().getCONSTRAINTKeyword_0());
                    		
                    // InternalSQLParser.g:1641:3: ( (lv_name_1_0= ruleName ) )
                    // InternalSQLParser.g:1642:4: (lv_name_1_0= ruleName )
                    {
                    // InternalSQLParser.g:1642:4: (lv_name_1_0= ruleName )
                    // InternalSQLParser.g:1643:5: lv_name_1_0= ruleName
                    {

                    					newCompositeNode(grammarAccess.getConstraintNameAccess().getNameNameParserRuleCall_1_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_name_1_0=ruleName();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getConstraintNameRule());
                    					}
                    					set(
                    						current,
                    						"name",
                    						lv_name_1_0,
                    						"io.shardml.SQL.Name");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConstraintName"


    // $ANTLR start "entryRuleConstraint"
    // InternalSQLParser.g:1664:1: entryRuleConstraint returns [EObject current=null] : iv_ruleConstraint= ruleConstraint EOF ;
    public final EObject entryRuleConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstraint = null;


        try {
            // InternalSQLParser.g:1664:51: (iv_ruleConstraint= ruleConstraint EOF )
            // InternalSQLParser.g:1665:2: iv_ruleConstraint= ruleConstraint EOF
            {
             newCompositeNode(grammarAccess.getConstraintRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleConstraint=ruleConstraint();

            state._fsp--;

             current =iv_ruleConstraint; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleConstraint"


    // $ANTLR start "ruleConstraint"
    // InternalSQLParser.g:1671:1: ruleConstraint returns [EObject current=null] : (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull | this_Nullable_3= ruleNullable | this_ColumnReferences_4= ruleColumnReferences | this_ColumnRaw_5= ruleColumnRaw ) ;
    public final EObject ruleConstraint() throws RecognitionException {
        EObject current = null;

        EObject this_PrimaryAnnotation_0 = null;

        EObject this_UniqueAnnotation_1 = null;

        EObject this_NotNull_2 = null;

        EObject this_Nullable_3 = null;

        EObject this_ColumnReferences_4 = null;

        EObject this_ColumnRaw_5 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1677:2: ( (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull | this_Nullable_3= ruleNullable | this_ColumnReferences_4= ruleColumnReferences | this_ColumnRaw_5= ruleColumnRaw ) )
            // InternalSQLParser.g:1678:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull | this_Nullable_3= ruleNullable | this_ColumnReferences_4= ruleColumnReferences | this_ColumnRaw_5= ruleColumnRaw )
            {
            // InternalSQLParser.g:1678:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull | this_Nullable_3= ruleNullable | this_ColumnReferences_4= ruleColumnReferences | this_ColumnRaw_5= ruleColumnRaw )
            int alt35=6;
            alt35 = dfa35.predict(input);
            switch (alt35) {
                case 1 :
                    // InternalSQLParser.g:1679:3: this_PrimaryAnnotation_0= rulePrimaryAnnotation
                    {

                    			newCompositeNode(grammarAccess.getConstraintAccess().getPrimaryAnnotationParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_PrimaryAnnotation_0=rulePrimaryAnnotation();

                    state._fsp--;


                    			current = this_PrimaryAnnotation_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:1688:3: this_UniqueAnnotation_1= ruleUniqueAnnotation
                    {

                    			newCompositeNode(grammarAccess.getConstraintAccess().getUniqueAnnotationParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_UniqueAnnotation_1=ruleUniqueAnnotation();

                    state._fsp--;


                    			current = this_UniqueAnnotation_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:1697:3: this_NotNull_2= ruleNotNull
                    {

                    			newCompositeNode(grammarAccess.getConstraintAccess().getNotNullParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_NotNull_2=ruleNotNull();

                    state._fsp--;


                    			current = this_NotNull_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:1706:3: this_Nullable_3= ruleNullable
                    {

                    			newCompositeNode(grammarAccess.getConstraintAccess().getNullableParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_Nullable_3=ruleNullable();

                    state._fsp--;


                    			current = this_Nullable_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:1715:3: this_ColumnReferences_4= ruleColumnReferences
                    {

                    			newCompositeNode(grammarAccess.getConstraintAccess().getColumnReferencesParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_ColumnReferences_4=ruleColumnReferences();

                    state._fsp--;


                    			current = this_ColumnReferences_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:1724:3: this_ColumnRaw_5= ruleColumnRaw
                    {

                    			newCompositeNode(grammarAccess.getConstraintAccess().getColumnRawParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_ColumnRaw_5=ruleColumnRaw();

                    state._fsp--;


                    			current = this_ColumnRaw_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleConstraint"


    // $ANTLR start "entryRulePrimaryAnnotation"
    // InternalSQLParser.g:1736:1: entryRulePrimaryAnnotation returns [EObject current=null] : iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF ;
    public final EObject entryRulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryAnnotation = null;


        try {
            // InternalSQLParser.g:1736:58: (iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF )
            // InternalSQLParser.g:1737:2: iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF
            {
             newCompositeNode(grammarAccess.getPrimaryAnnotationRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePrimaryAnnotation=rulePrimaryAnnotation();

            state._fsp--;

             current =iv_rulePrimaryAnnotation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimaryAnnotation"


    // $ANTLR start "rulePrimaryAnnotation"
    // InternalSQLParser.g:1743:1: rulePrimaryAnnotation returns [EObject current=null] : ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= PRIMARY otherlv_3= KEY ) ;
    public final EObject rulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject this_ConstraintName_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1749:2: ( ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= PRIMARY otherlv_3= KEY ) )
            // InternalSQLParser.g:1750:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= PRIMARY otherlv_3= KEY )
            {
            // InternalSQLParser.g:1750:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= PRIMARY otherlv_3= KEY )
            // InternalSQLParser.g:1751:3: () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= PRIMARY otherlv_3= KEY
            {
            // InternalSQLParser.g:1751:3: ()
            // InternalSQLParser.g:1752:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getPrimaryAnnotationAccess().getPrimaryAnnotationAction_0(),
            					current);
            			

            }


            			if (current==null) {
            				current = createModelElement(grammarAccess.getPrimaryAnnotationRule());
            			}
            			newCompositeNode(grammarAccess.getPrimaryAnnotationAccess().getConstraintNameParserRuleCall_1());
            		
            pushFollow(FOLLOW_39);
            this_ConstraintName_1=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_1;
            			afterParserOrEnumRuleCall();
            		
            otherlv_2=(Token)match(input,PRIMARY,FOLLOW_40); 

            			newLeafNode(otherlv_2, grammarAccess.getPrimaryAnnotationAccess().getPRIMARYKeyword_2());
            		
            otherlv_3=(Token)match(input,KEY,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getPrimaryAnnotationAccess().getKEYKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimaryAnnotation"


    // $ANTLR start "entryRuleUniqueAnnotation"
    // InternalSQLParser.g:1781:1: entryRuleUniqueAnnotation returns [EObject current=null] : iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF ;
    public final EObject entryRuleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueAnnotation = null;


        try {
            // InternalSQLParser.g:1781:57: (iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF )
            // InternalSQLParser.g:1782:2: iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF
            {
             newCompositeNode(grammarAccess.getUniqueAnnotationRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUniqueAnnotation=ruleUniqueAnnotation();

            state._fsp--;

             current =iv_ruleUniqueAnnotation; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUniqueAnnotation"


    // $ANTLR start "ruleUniqueAnnotation"
    // InternalSQLParser.g:1788:1: ruleUniqueAnnotation returns [EObject current=null] : ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= UNIQUE ) ;
    public final EObject ruleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_ConstraintName_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1794:2: ( ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= UNIQUE ) )
            // InternalSQLParser.g:1795:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= UNIQUE )
            {
            // InternalSQLParser.g:1795:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= UNIQUE )
            // InternalSQLParser.g:1796:3: () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= UNIQUE
            {
            // InternalSQLParser.g:1796:3: ()
            // InternalSQLParser.g:1797:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getUniqueAnnotationAccess().getUniqueAnnotationAction_0(),
            					current);
            			

            }


            			if (current==null) {
            				current = createModelElement(grammarAccess.getUniqueAnnotationRule());
            			}
            			newCompositeNode(grammarAccess.getUniqueAnnotationAccess().getConstraintNameParserRuleCall_1());
            		
            pushFollow(FOLLOW_41);
            this_ConstraintName_1=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_1;
            			afterParserOrEnumRuleCall();
            		
            otherlv_2=(Token)match(input,UNIQUE,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getUniqueAnnotationAccess().getUNIQUEKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUniqueAnnotation"


    // $ANTLR start "entryRuleNotNull"
    // InternalSQLParser.g:1822:1: entryRuleNotNull returns [EObject current=null] : iv_ruleNotNull= ruleNotNull EOF ;
    public final EObject entryRuleNotNull() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNotNull = null;


        try {
            // InternalSQLParser.g:1822:48: (iv_ruleNotNull= ruleNotNull EOF )
            // InternalSQLParser.g:1823:2: iv_ruleNotNull= ruleNotNull EOF
            {
             newCompositeNode(grammarAccess.getNotNullRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNotNull=ruleNotNull();

            state._fsp--;

             current =iv_ruleNotNull; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNotNull"


    // $ANTLR start "ruleNotNull"
    // InternalSQLParser.g:1829:1: ruleNotNull returns [EObject current=null] : ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NOT otherlv_3= NULL ) ;
    public final EObject ruleNotNull() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject this_ConstraintName_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1835:2: ( ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NOT otherlv_3= NULL ) )
            // InternalSQLParser.g:1836:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NOT otherlv_3= NULL )
            {
            // InternalSQLParser.g:1836:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NOT otherlv_3= NULL )
            // InternalSQLParser.g:1837:3: () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NOT otherlv_3= NULL
            {
            // InternalSQLParser.g:1837:3: ()
            // InternalSQLParser.g:1838:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getNotNullAccess().getNotNullAction_0(),
            					current);
            			

            }


            			if (current==null) {
            				current = createModelElement(grammarAccess.getNotNullRule());
            			}
            			newCompositeNode(grammarAccess.getNotNullAccess().getConstraintNameParserRuleCall_1());
            		
            pushFollow(FOLLOW_6);
            this_ConstraintName_1=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_1;
            			afterParserOrEnumRuleCall();
            		
            otherlv_2=(Token)match(input,NOT,FOLLOW_42); 

            			newLeafNode(otherlv_2, grammarAccess.getNotNullAccess().getNOTKeyword_2());
            		
            otherlv_3=(Token)match(input,NULL,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getNotNullAccess().getNULLKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNotNull"


    // $ANTLR start "entryRuleNullable"
    // InternalSQLParser.g:1867:1: entryRuleNullable returns [EObject current=null] : iv_ruleNullable= ruleNullable EOF ;
    public final EObject entryRuleNullable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNullable = null;


        try {
            // InternalSQLParser.g:1867:49: (iv_ruleNullable= ruleNullable EOF )
            // InternalSQLParser.g:1868:2: iv_ruleNullable= ruleNullable EOF
            {
             newCompositeNode(grammarAccess.getNullableRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNullable=ruleNullable();

            state._fsp--;

             current =iv_ruleNullable; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNullable"


    // $ANTLR start "ruleNullable"
    // InternalSQLParser.g:1874:1: ruleNullable returns [EObject current=null] : ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NULL ) ;
    public final EObject ruleNullable() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_ConstraintName_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1880:2: ( ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NULL ) )
            // InternalSQLParser.g:1881:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NULL )
            {
            // InternalSQLParser.g:1881:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NULL )
            // InternalSQLParser.g:1882:3: () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NULL
            {
            // InternalSQLParser.g:1882:3: ()
            // InternalSQLParser.g:1883:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getNullableAccess().getNullableAction_0(),
            					current);
            			

            }


            			if (current==null) {
            				current = createModelElement(grammarAccess.getNullableRule());
            			}
            			newCompositeNode(grammarAccess.getNullableAccess().getConstraintNameParserRuleCall_1());
            		
            pushFollow(FOLLOW_42);
            this_ConstraintName_1=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_1;
            			afterParserOrEnumRuleCall();
            		
            otherlv_2=(Token)match(input,NULL,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getNullableAccess().getNULLKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNullable"


    // $ANTLR start "entryRuleColumnReferences"
    // InternalSQLParser.g:1908:1: entryRuleColumnReferences returns [EObject current=null] : iv_ruleColumnReferences= ruleColumnReferences EOF ;
    public final EObject entryRuleColumnReferences() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumnReferences = null;


        try {
            // InternalSQLParser.g:1908:57: (iv_ruleColumnReferences= ruleColumnReferences EOF )
            // InternalSQLParser.g:1909:2: iv_ruleColumnReferences= ruleColumnReferences EOF
            {
             newCompositeNode(grammarAccess.getColumnReferencesRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleColumnReferences=ruleColumnReferences();

            state._fsp--;

             current =iv_ruleColumnReferences; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleColumnReferences"


    // $ANTLR start "ruleColumnReferences"
    // InternalSQLParser.g:1915:1: ruleColumnReferences returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= REFERENCES this_References_2= ruleReferences[$current] ) ;
    public final EObject ruleColumnReferences() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject this_ConstraintName_0 = null;

        EObject this_References_2 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1921:2: ( (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= REFERENCES this_References_2= ruleReferences[$current] ) )
            // InternalSQLParser.g:1922:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= REFERENCES this_References_2= ruleReferences[$current] )
            {
            // InternalSQLParser.g:1922:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= REFERENCES this_References_2= ruleReferences[$current] )
            // InternalSQLParser.g:1923:3: this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= REFERENCES this_References_2= ruleReferences[$current]
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getColumnReferencesRule());
            			}
            			newCompositeNode(grammarAccess.getColumnReferencesAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_43);
            this_ConstraintName_0=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_0;
            			afterParserOrEnumRuleCall();
            		
            otherlv_1=(Token)match(input,REFERENCES,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getColumnReferencesAccess().getREFERENCESKeyword_1());
            		

            			if (current==null) {
            				current = createModelElement(grammarAccess.getColumnReferencesRule());
            			}
            			newCompositeNode(grammarAccess.getColumnReferencesAccess().getReferencesParserRuleCall_2());
            		
            pushFollow(FOLLOW_2);
            this_References_2=ruleReferences(current);

            state._fsp--;


            			current = this_References_2;
            			afterParserOrEnumRuleCall();
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColumnReferences"


    // $ANTLR start "entryRuleColumnRaw"
    // InternalSQLParser.g:1953:1: entryRuleColumnRaw returns [EObject current=null] : iv_ruleColumnRaw= ruleColumnRaw EOF ;
    public final EObject entryRuleColumnRaw() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumnRaw = null;


        try {
            // InternalSQLParser.g:1953:50: (iv_ruleColumnRaw= ruleColumnRaw EOF )
            // InternalSQLParser.g:1954:2: iv_ruleColumnRaw= ruleColumnRaw EOF
            {
             newCompositeNode(grammarAccess.getColumnRawRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleColumnRaw=ruleColumnRaw();

            state._fsp--;

             current =iv_ruleColumnRaw; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleColumnRaw"


    // $ANTLR start "ruleColumnRaw"
    // InternalSQLParser.g:1960:1: ruleColumnRaw returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) ) ) ;
    public final EObject ruleColumnRaw() throws RecognitionException {
        EObject current = null;

        EObject this_ConstraintName_0 = null;

        Enumerator lv_kind_1_0 = null;

        AntlrDatatypeRuleToken lv_text_2_0 = null;

        Enumerator lv_kind_3_0 = null;

        AntlrDatatypeRuleToken lv_text_4_0 = null;

        Enumerator lv_kind_5_0 = null;

        AntlrDatatypeRuleToken lv_text_6_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1966:2: ( (this_ConstraintName_0= ruleConstraintName[$current] ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) ) ) )
            // InternalSQLParser.g:1967:2: (this_ConstraintName_0= ruleConstraintName[$current] ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) ) )
            {
            // InternalSQLParser.g:1967:2: (this_ConstraintName_0= ruleConstraintName[$current] ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) ) )
            // InternalSQLParser.g:1968:3: this_ConstraintName_0= ruleConstraintName[$current] ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) )
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getColumnRawRule());
            			}
            			newCompositeNode(grammarAccess.getColumnRawAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_44);
            this_ConstraintName_0=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalSQLParser.g:1979:3: ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) )
            int alt36=3;
            switch ( input.LA(1) ) {
            case DEFAULT:
                {
                alt36=1;
                }
                break;
            case CHECK:
                {
                alt36=2;
                }
                break;
            case GENERATED:
                {
                alt36=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }

            switch (alt36) {
                case 1 :
                    // InternalSQLParser.g:1980:4: ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) )
                    {
                    // InternalSQLParser.g:1980:4: ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) )
                    // InternalSQLParser.g:1981:5: ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) )
                    {
                    // InternalSQLParser.g:1981:5: ( (lv_kind_1_0= ruleDefaultKind ) )
                    // InternalSQLParser.g:1982:6: (lv_kind_1_0= ruleDefaultKind )
                    {
                    // InternalSQLParser.g:1982:6: (lv_kind_1_0= ruleDefaultKind )
                    // InternalSQLParser.g:1983:7: lv_kind_1_0= ruleDefaultKind
                    {

                    							newCompositeNode(grammarAccess.getColumnRawAccess().getKindDefaultKindEnumRuleCall_1_0_0_0());
                    						
                    pushFollow(FOLLOW_45);
                    lv_kind_1_0=ruleDefaultKind();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getColumnRawRule());
                    							}
                    							set(
                    								current,
                    								"kind",
                    								lv_kind_1_0,
                    								"io.shardml.SQL.DefaultKind");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }

                    // InternalSQLParser.g:2000:5: ( (lv_text_2_0= ruleDefaultExpr ) )
                    // InternalSQLParser.g:2001:6: (lv_text_2_0= ruleDefaultExpr )
                    {
                    // InternalSQLParser.g:2001:6: (lv_text_2_0= ruleDefaultExpr )
                    // InternalSQLParser.g:2002:7: lv_text_2_0= ruleDefaultExpr
                    {

                    							newCompositeNode(grammarAccess.getColumnRawAccess().getTextDefaultExprParserRuleCall_1_0_1_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_text_2_0=ruleDefaultExpr();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getColumnRawRule());
                    							}
                    							set(
                    								current,
                    								"text",
                    								lv_text_2_0,
                    								"io.shardml.SQL.DefaultExpr");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2021:4: ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) )
                    {
                    // InternalSQLParser.g:2021:4: ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) )
                    // InternalSQLParser.g:2022:5: ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) )
                    {
                    // InternalSQLParser.g:2022:5: ( (lv_kind_3_0= ruleCheckKind ) )
                    // InternalSQLParser.g:2023:6: (lv_kind_3_0= ruleCheckKind )
                    {
                    // InternalSQLParser.g:2023:6: (lv_kind_3_0= ruleCheckKind )
                    // InternalSQLParser.g:2024:7: lv_kind_3_0= ruleCheckKind
                    {

                    							newCompositeNode(grammarAccess.getColumnRawAccess().getKindCheckKindEnumRuleCall_1_1_0_0());
                    						
                    pushFollow(FOLLOW_9);
                    lv_kind_3_0=ruleCheckKind();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getColumnRawRule());
                    							}
                    							set(
                    								current,
                    								"kind",
                    								lv_kind_3_0,
                    								"io.shardml.SQL.CheckKind");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }

                    // InternalSQLParser.g:2041:5: ( (lv_text_4_0= ruleParenthesized ) )
                    // InternalSQLParser.g:2042:6: (lv_text_4_0= ruleParenthesized )
                    {
                    // InternalSQLParser.g:2042:6: (lv_text_4_0= ruleParenthesized )
                    // InternalSQLParser.g:2043:7: lv_text_4_0= ruleParenthesized
                    {

                    							newCompositeNode(grammarAccess.getColumnRawAccess().getTextParenthesizedParserRuleCall_1_1_1_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_text_4_0=ruleParenthesized();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getColumnRawRule());
                    							}
                    							set(
                    								current,
                    								"text",
                    								lv_text_4_0,
                    								"io.shardml.SQL.Parenthesized");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:2062:4: ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) )
                    {
                    // InternalSQLParser.g:2062:4: ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) )
                    // InternalSQLParser.g:2063:5: ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) )
                    {
                    // InternalSQLParser.g:2063:5: ( (lv_kind_5_0= ruleGeneratedKind ) )
                    // InternalSQLParser.g:2064:6: (lv_kind_5_0= ruleGeneratedKind )
                    {
                    // InternalSQLParser.g:2064:6: (lv_kind_5_0= ruleGeneratedKind )
                    // InternalSQLParser.g:2065:7: lv_kind_5_0= ruleGeneratedKind
                    {

                    							newCompositeNode(grammarAccess.getColumnRawAccess().getKindGeneratedKindEnumRuleCall_1_2_0_0());
                    						
                    pushFollow(FOLLOW_46);
                    lv_kind_5_0=ruleGeneratedKind();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getColumnRawRule());
                    							}
                    							set(
                    								current,
                    								"kind",
                    								lv_kind_5_0,
                    								"io.shardml.SQL.GeneratedKind");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }

                    // InternalSQLParser.g:2082:5: ( (lv_text_6_0= ruleGeneratedTail ) )
                    // InternalSQLParser.g:2083:6: (lv_text_6_0= ruleGeneratedTail )
                    {
                    // InternalSQLParser.g:2083:6: (lv_text_6_0= ruleGeneratedTail )
                    // InternalSQLParser.g:2084:7: lv_text_6_0= ruleGeneratedTail
                    {

                    							newCompositeNode(grammarAccess.getColumnRawAccess().getTextGeneratedTailParserRuleCall_1_2_1_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_text_6_0=ruleGeneratedTail();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getColumnRawRule());
                    							}
                    							set(
                    								current,
                    								"text",
                    								lv_text_6_0,
                    								"io.shardml.SQL.GeneratedTail");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleColumnRaw"


    // $ANTLR start "entryRuleTableConstraint"
    // InternalSQLParser.g:2107:1: entryRuleTableConstraint returns [EObject current=null] : iv_ruleTableConstraint= ruleTableConstraint EOF ;
    public final EObject entryRuleTableConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableConstraint = null;


        try {
            // InternalSQLParser.g:2107:56: (iv_ruleTableConstraint= ruleTableConstraint EOF )
            // InternalSQLParser.g:2108:2: iv_ruleTableConstraint= ruleTableConstraint EOF
            {
             newCompositeNode(grammarAccess.getTableConstraintRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTableConstraint=ruleTableConstraint();

            state._fsp--;

             current =iv_ruleTableConstraint; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTableConstraint"


    // $ANTLR start "ruleTableConstraint"
    // InternalSQLParser.g:2114:1: ruleTableConstraint returns [EObject current=null] : (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey | this_ForeignKey_2= ruleForeignKey | this_TableCheck_3= ruleTableCheck ) ;
    public final EObject ruleTableConstraint() throws RecognitionException {
        EObject current = null;

        EObject this_PrimaryKey_0 = null;

        EObject this_UniqueKey_1 = null;

        EObject this_ForeignKey_2 = null;

        EObject this_TableCheck_3 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2120:2: ( (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey | this_ForeignKey_2= ruleForeignKey | this_TableCheck_3= ruleTableCheck ) )
            // InternalSQLParser.g:2121:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey | this_ForeignKey_2= ruleForeignKey | this_TableCheck_3= ruleTableCheck )
            {
            // InternalSQLParser.g:2121:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey | this_ForeignKey_2= ruleForeignKey | this_TableCheck_3= ruleTableCheck )
            int alt37=4;
            alt37 = dfa37.predict(input);
            switch (alt37) {
                case 1 :
                    // InternalSQLParser.g:2122:3: this_PrimaryKey_0= rulePrimaryKey
                    {

                    			newCompositeNode(grammarAccess.getTableConstraintAccess().getPrimaryKeyParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_PrimaryKey_0=rulePrimaryKey();

                    state._fsp--;


                    			current = this_PrimaryKey_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2131:3: this_UniqueKey_1= ruleUniqueKey
                    {

                    			newCompositeNode(grammarAccess.getTableConstraintAccess().getUniqueKeyParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_UniqueKey_1=ruleUniqueKey();

                    state._fsp--;


                    			current = this_UniqueKey_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:2140:3: this_ForeignKey_2= ruleForeignKey
                    {

                    			newCompositeNode(grammarAccess.getTableConstraintAccess().getForeignKeyParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_ForeignKey_2=ruleForeignKey();

                    state._fsp--;


                    			current = this_ForeignKey_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:2149:3: this_TableCheck_3= ruleTableCheck
                    {

                    			newCompositeNode(grammarAccess.getTableConstraintAccess().getTableCheckParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_TableCheck_3=ruleTableCheck();

                    state._fsp--;


                    			current = this_TableCheck_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTableConstraint"


    // $ANTLR start "ruleKeyColumns"
    // InternalSQLParser.g:2162:1: ruleKeyColumns[EObject in_current] returns [EObject current=in_current] : (otherlv_0= LeftParenthesis ( ( ruleName ) ) (otherlv_2= Comma ( ( ruleName ) ) )* otherlv_4= RightParenthesis ) ;
    public final EObject ruleKeyColumns(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalSQLParser.g:2168:2: ( (otherlv_0= LeftParenthesis ( ( ruleName ) ) (otherlv_2= Comma ( ( ruleName ) ) )* otherlv_4= RightParenthesis ) )
            // InternalSQLParser.g:2169:2: (otherlv_0= LeftParenthesis ( ( ruleName ) ) (otherlv_2= Comma ( ( ruleName ) ) )* otherlv_4= RightParenthesis )
            {
            // InternalSQLParser.g:2169:2: (otherlv_0= LeftParenthesis ( ( ruleName ) ) (otherlv_2= Comma ( ( ruleName ) ) )* otherlv_4= RightParenthesis )
            // InternalSQLParser.g:2170:3: otherlv_0= LeftParenthesis ( ( ruleName ) ) (otherlv_2= Comma ( ( ruleName ) ) )* otherlv_4= RightParenthesis
            {
            otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getKeyColumnsAccess().getLeftParenthesisKeyword_0());
            		
            // InternalSQLParser.g:2174:3: ( ( ruleName ) )
            // InternalSQLParser.g:2175:4: ( ruleName )
            {
            // InternalSQLParser.g:2175:4: ( ruleName )
            // InternalSQLParser.g:2176:5: ruleName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getKeyColumnsRule());
            					}
            				

            					newCompositeNode(grammarAccess.getKeyColumnsAccess().getColumnsColumnCrossReference_1_0());
            				
            pushFollow(FOLLOW_11);
            ruleName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSQLParser.g:2190:3: (otherlv_2= Comma ( ( ruleName ) ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==Comma) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalSQLParser.g:2191:4: otherlv_2= Comma ( ( ruleName ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_5); 

            	    				newLeafNode(otherlv_2, grammarAccess.getKeyColumnsAccess().getCommaKeyword_2_0());
            	    			
            	    // InternalSQLParser.g:2195:4: ( ( ruleName ) )
            	    // InternalSQLParser.g:2196:5: ( ruleName )
            	    {
            	    // InternalSQLParser.g:2196:5: ( ruleName )
            	    // InternalSQLParser.g:2197:6: ruleName
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getKeyColumnsRule());
            	    						}
            	    					

            	    						newCompositeNode(grammarAccess.getKeyColumnsAccess().getColumnsColumnCrossReference_2_1_0());
            	    					
            	    pushFollow(FOLLOW_11);
            	    ruleName();

            	    state._fsp--;


            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);

            otherlv_4=(Token)match(input,RightParenthesis,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getKeyColumnsAccess().getRightParenthesisKeyword_3());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleKeyColumns"


    // $ANTLR start "entryRulePrimaryKey"
    // InternalSQLParser.g:2220:1: entryRulePrimaryKey returns [EObject current=null] : iv_rulePrimaryKey= rulePrimaryKey EOF ;
    public final EObject entryRulePrimaryKey() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryKey = null;


        try {
            // InternalSQLParser.g:2220:51: (iv_rulePrimaryKey= rulePrimaryKey EOF )
            // InternalSQLParser.g:2221:2: iv_rulePrimaryKey= rulePrimaryKey EOF
            {
             newCompositeNode(grammarAccess.getPrimaryKeyRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePrimaryKey=rulePrimaryKey();

            state._fsp--;

             current =iv_rulePrimaryKey; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimaryKey"


    // $ANTLR start "rulePrimaryKey"
    // InternalSQLParser.g:2227:1: rulePrimaryKey returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= PRIMARY otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] ) ;
    public final EObject rulePrimaryKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject this_ConstraintName_0 = null;

        EObject this_KeyColumns_3 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2233:2: ( (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= PRIMARY otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] ) )
            // InternalSQLParser.g:2234:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= PRIMARY otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] )
            {
            // InternalSQLParser.g:2234:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= PRIMARY otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] )
            // InternalSQLParser.g:2235:3: this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= PRIMARY otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current]
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getPrimaryKeyRule());
            			}
            			newCompositeNode(grammarAccess.getPrimaryKeyAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_39);
            this_ConstraintName_0=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_0;
            			afterParserOrEnumRuleCall();
            		
            otherlv_1=(Token)match(input,PRIMARY,FOLLOW_40); 

            			newLeafNode(otherlv_1, grammarAccess.getPrimaryKeyAccess().getPRIMARYKeyword_1());
            		
            otherlv_2=(Token)match(input,KEY,FOLLOW_9); 

            			newLeafNode(otherlv_2, grammarAccess.getPrimaryKeyAccess().getKEYKeyword_2());
            		

            			if (current==null) {
            				current = createModelElement(grammarAccess.getPrimaryKeyRule());
            			}
            			newCompositeNode(grammarAccess.getPrimaryKeyAccess().getKeyColumnsParserRuleCall_3());
            		
            pushFollow(FOLLOW_2);
            this_KeyColumns_3=ruleKeyColumns(current);

            state._fsp--;


            			current = this_KeyColumns_3;
            			afterParserOrEnumRuleCall();
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimaryKey"


    // $ANTLR start "entryRuleUniqueKey"
    // InternalSQLParser.g:2269:1: entryRuleUniqueKey returns [EObject current=null] : iv_ruleUniqueKey= ruleUniqueKey EOF ;
    public final EObject entryRuleUniqueKey() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueKey = null;


        try {
            // InternalSQLParser.g:2269:50: (iv_ruleUniqueKey= ruleUniqueKey EOF )
            // InternalSQLParser.g:2270:2: iv_ruleUniqueKey= ruleUniqueKey EOF
            {
             newCompositeNode(grammarAccess.getUniqueKeyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleUniqueKey=ruleUniqueKey();

            state._fsp--;

             current =iv_ruleUniqueKey; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUniqueKey"


    // $ANTLR start "ruleUniqueKey"
    // InternalSQLParser.g:2276:1: ruleUniqueKey returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= UNIQUE this_KeyColumns_2= ruleKeyColumns[$current] ) ;
    public final EObject ruleUniqueKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject this_ConstraintName_0 = null;

        EObject this_KeyColumns_2 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2282:2: ( (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= UNIQUE this_KeyColumns_2= ruleKeyColumns[$current] ) )
            // InternalSQLParser.g:2283:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= UNIQUE this_KeyColumns_2= ruleKeyColumns[$current] )
            {
            // InternalSQLParser.g:2283:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= UNIQUE this_KeyColumns_2= ruleKeyColumns[$current] )
            // InternalSQLParser.g:2284:3: this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= UNIQUE this_KeyColumns_2= ruleKeyColumns[$current]
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getUniqueKeyRule());
            			}
            			newCompositeNode(grammarAccess.getUniqueKeyAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_41);
            this_ConstraintName_0=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_0;
            			afterParserOrEnumRuleCall();
            		
            otherlv_1=(Token)match(input,UNIQUE,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getUniqueKeyAccess().getUNIQUEKeyword_1());
            		

            			if (current==null) {
            				current = createModelElement(grammarAccess.getUniqueKeyRule());
            			}
            			newCompositeNode(grammarAccess.getUniqueKeyAccess().getKeyColumnsParserRuleCall_2());
            		
            pushFollow(FOLLOW_2);
            this_KeyColumns_2=ruleKeyColumns(current);

            state._fsp--;


            			current = this_KeyColumns_2;
            			afterParserOrEnumRuleCall();
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUniqueKey"


    // $ANTLR start "entryRuleForeignKey"
    // InternalSQLParser.g:2314:1: entryRuleForeignKey returns [EObject current=null] : iv_ruleForeignKey= ruleForeignKey EOF ;
    public final EObject entryRuleForeignKey() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleForeignKey = null;


        try {
            // InternalSQLParser.g:2314:51: (iv_ruleForeignKey= ruleForeignKey EOF )
            // InternalSQLParser.g:2315:2: iv_ruleForeignKey= ruleForeignKey EOF
            {
             newCompositeNode(grammarAccess.getForeignKeyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleForeignKey=ruleForeignKey();

            state._fsp--;

             current =iv_ruleForeignKey; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleForeignKey"


    // $ANTLR start "ruleForeignKey"
    // InternalSQLParser.g:2321:1: ruleForeignKey returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= FOREIGN otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] otherlv_4= REFERENCES this_References_5= ruleReferences[$current] ) ;
    public final EObject ruleForeignKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject this_ConstraintName_0 = null;

        EObject this_KeyColumns_3 = null;

        EObject this_References_5 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2327:2: ( (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= FOREIGN otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] otherlv_4= REFERENCES this_References_5= ruleReferences[$current] ) )
            // InternalSQLParser.g:2328:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= FOREIGN otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] otherlv_4= REFERENCES this_References_5= ruleReferences[$current] )
            {
            // InternalSQLParser.g:2328:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= FOREIGN otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] otherlv_4= REFERENCES this_References_5= ruleReferences[$current] )
            // InternalSQLParser.g:2329:3: this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= FOREIGN otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] otherlv_4= REFERENCES this_References_5= ruleReferences[$current]
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getForeignKeyRule());
            			}
            			newCompositeNode(grammarAccess.getForeignKeyAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_47);
            this_ConstraintName_0=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_0;
            			afterParserOrEnumRuleCall();
            		
            otherlv_1=(Token)match(input,FOREIGN,FOLLOW_40); 

            			newLeafNode(otherlv_1, grammarAccess.getForeignKeyAccess().getFOREIGNKeyword_1());
            		
            otherlv_2=(Token)match(input,KEY,FOLLOW_9); 

            			newLeafNode(otherlv_2, grammarAccess.getForeignKeyAccess().getKEYKeyword_2());
            		

            			if (current==null) {
            				current = createModelElement(grammarAccess.getForeignKeyRule());
            			}
            			newCompositeNode(grammarAccess.getForeignKeyAccess().getKeyColumnsParserRuleCall_3());
            		
            pushFollow(FOLLOW_43);
            this_KeyColumns_3=ruleKeyColumns(current);

            state._fsp--;


            			current = this_KeyColumns_3;
            			afterParserOrEnumRuleCall();
            		
            otherlv_4=(Token)match(input,REFERENCES,FOLLOW_5); 

            			newLeafNode(otherlv_4, grammarAccess.getForeignKeyAccess().getREFERENCESKeyword_4());
            		

            			if (current==null) {
            				current = createModelElement(grammarAccess.getForeignKeyRule());
            			}
            			newCompositeNode(grammarAccess.getForeignKeyAccess().getReferencesParserRuleCall_5());
            		
            pushFollow(FOLLOW_2);
            this_References_5=ruleReferences(current);

            state._fsp--;


            			current = this_References_5;
            			afterParserOrEnumRuleCall();
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleForeignKey"


    // $ANTLR start "entryRuleTableCheck"
    // InternalSQLParser.g:2378:1: entryRuleTableCheck returns [EObject current=null] : iv_ruleTableCheck= ruleTableCheck EOF ;
    public final EObject entryRuleTableCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableCheck = null;


        try {
            // InternalSQLParser.g:2378:51: (iv_ruleTableCheck= ruleTableCheck EOF )
            // InternalSQLParser.g:2379:2: iv_ruleTableCheck= ruleTableCheck EOF
            {
             newCompositeNode(grammarAccess.getTableCheckRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTableCheck=ruleTableCheck();

            state._fsp--;

             current =iv_ruleTableCheck; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTableCheck"


    // $ANTLR start "ruleTableCheck"
    // InternalSQLParser.g:2385:1: ruleTableCheck returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] ( (lv_check_1_0= ruleCheckClause ) ) ) ;
    public final EObject ruleTableCheck() throws RecognitionException {
        EObject current = null;

        EObject this_ConstraintName_0 = null;

        EObject lv_check_1_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2391:2: ( (this_ConstraintName_0= ruleConstraintName[$current] ( (lv_check_1_0= ruleCheckClause ) ) ) )
            // InternalSQLParser.g:2392:2: (this_ConstraintName_0= ruleConstraintName[$current] ( (lv_check_1_0= ruleCheckClause ) ) )
            {
            // InternalSQLParser.g:2392:2: (this_ConstraintName_0= ruleConstraintName[$current] ( (lv_check_1_0= ruleCheckClause ) ) )
            // InternalSQLParser.g:2393:3: this_ConstraintName_0= ruleConstraintName[$current] ( (lv_check_1_0= ruleCheckClause ) )
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getTableCheckRule());
            			}
            			newCompositeNode(grammarAccess.getTableCheckAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_10);
            this_ConstraintName_0=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalSQLParser.g:2404:3: ( (lv_check_1_0= ruleCheckClause ) )
            // InternalSQLParser.g:2405:4: (lv_check_1_0= ruleCheckClause )
            {
            // InternalSQLParser.g:2405:4: (lv_check_1_0= ruleCheckClause )
            // InternalSQLParser.g:2406:5: lv_check_1_0= ruleCheckClause
            {

            					newCompositeNode(grammarAccess.getTableCheckAccess().getCheckCheckClauseParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_check_1_0=ruleCheckClause();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTableCheckRule());
            					}
            					set(
            						current,
            						"check",
            						lv_check_1_0,
            						"io.shardml.SQL.CheckClause");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTableCheck"


    // $ANTLR start "entryRuleCheckClause"
    // InternalSQLParser.g:2427:1: entryRuleCheckClause returns [EObject current=null] : iv_ruleCheckClause= ruleCheckClause EOF ;
    public final EObject entryRuleCheckClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCheckClause = null;


        try {
            // InternalSQLParser.g:2427:52: (iv_ruleCheckClause= ruleCheckClause EOF )
            // InternalSQLParser.g:2428:2: iv_ruleCheckClause= ruleCheckClause EOF
            {
             newCompositeNode(grammarAccess.getCheckClauseRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCheckClause=ruleCheckClause();

            state._fsp--;

             current =iv_ruleCheckClause; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCheckClause"


    // $ANTLR start "ruleCheckClause"
    // InternalSQLParser.g:2434:1: ruleCheckClause returns [EObject current=null] : ( ( (lv_kind_0_0= ruleCheckKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) ) ;
    public final EObject ruleCheckClause() throws RecognitionException {
        EObject current = null;

        Enumerator lv_kind_0_0 = null;

        AntlrDatatypeRuleToken lv_text_1_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2440:2: ( ( ( (lv_kind_0_0= ruleCheckKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) ) )
            // InternalSQLParser.g:2441:2: ( ( (lv_kind_0_0= ruleCheckKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) )
            {
            // InternalSQLParser.g:2441:2: ( ( (lv_kind_0_0= ruleCheckKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) )
            // InternalSQLParser.g:2442:3: ( (lv_kind_0_0= ruleCheckKind ) ) ( (lv_text_1_0= ruleParenthesized ) )
            {
            // InternalSQLParser.g:2442:3: ( (lv_kind_0_0= ruleCheckKind ) )
            // InternalSQLParser.g:2443:4: (lv_kind_0_0= ruleCheckKind )
            {
            // InternalSQLParser.g:2443:4: (lv_kind_0_0= ruleCheckKind )
            // InternalSQLParser.g:2444:5: lv_kind_0_0= ruleCheckKind
            {

            					newCompositeNode(grammarAccess.getCheckClauseAccess().getKindCheckKindEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_9);
            lv_kind_0_0=ruleCheckKind();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCheckClauseRule());
            					}
            					set(
            						current,
            						"kind",
            						lv_kind_0_0,
            						"io.shardml.SQL.CheckKind");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSQLParser.g:2461:3: ( (lv_text_1_0= ruleParenthesized ) )
            // InternalSQLParser.g:2462:4: (lv_text_1_0= ruleParenthesized )
            {
            // InternalSQLParser.g:2462:4: (lv_text_1_0= ruleParenthesized )
            // InternalSQLParser.g:2463:5: lv_text_1_0= ruleParenthesized
            {

            					newCompositeNode(grammarAccess.getCheckClauseAccess().getTextParenthesizedParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_text_1_0=ruleParenthesized();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCheckClauseRule());
            					}
            					set(
            						current,
            						"text",
            						lv_text_1_0,
            						"io.shardml.SQL.Parenthesized");
            					afterParserOrEnumRuleCall();
            				

            }


            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCheckClause"


    // $ANTLR start "ruleReferences"
    // InternalSQLParser.g:2485:1: ruleReferences[EObject in_current] returns [EObject current=in_current] : ( ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )? ( ( ruleName ) ) (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )? (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )? ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )? ( (lv_deferrable_14_0= ruleDeferrability ) )? ) ;
    public final EObject ruleReferences(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_refSchema_0_0 = null;

        Enumerator lv_match_9_0 = null;

        EObject this_OnDelete_10 = null;

        EObject this_OnUpdate_11 = null;

        EObject this_OnUpdate_12 = null;

        EObject this_OnDelete_13 = null;

        AntlrDatatypeRuleToken lv_deferrable_14_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2491:2: ( ( ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )? ( ( ruleName ) ) (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )? (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )? ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )? ( (lv_deferrable_14_0= ruleDeferrability ) )? ) )
            // InternalSQLParser.g:2492:2: ( ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )? ( ( ruleName ) ) (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )? (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )? ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )? ( (lv_deferrable_14_0= ruleDeferrability ) )? )
            {
            // InternalSQLParser.g:2492:2: ( ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )? ( ( ruleName ) ) (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )? (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )? ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )? ( (lv_deferrable_14_0= ruleDeferrability ) )? )
            // InternalSQLParser.g:2493:3: ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )? ( ( ruleName ) ) (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )? (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )? ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )? ( (lv_deferrable_14_0= ruleDeferrability ) )?
            {
            // InternalSQLParser.g:2493:3: ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )?
            int alt39=2;
            alt39 = dfa39.predict(input);
            switch (alt39) {
                case 1 :
                    // InternalSQLParser.g:2494:4: ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop
                    {
                    // InternalSQLParser.g:2494:4: ( (lv_refSchema_0_0= ruleName ) )
                    // InternalSQLParser.g:2495:5: (lv_refSchema_0_0= ruleName )
                    {
                    // InternalSQLParser.g:2495:5: (lv_refSchema_0_0= ruleName )
                    // InternalSQLParser.g:2496:6: lv_refSchema_0_0= ruleName
                    {

                    						newCompositeNode(grammarAccess.getReferencesAccess().getRefSchemaNameParserRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_refSchema_0_0=ruleName();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getReferencesRule());
                    						}
                    						set(
                    							current,
                    							"refSchema",
                    							lv_refSchema_0_0,
                    							"io.shardml.SQL.Name");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,FullStop,FOLLOW_5); 

                    				newLeafNode(otherlv_1, grammarAccess.getReferencesAccess().getFullStopKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:2518:3: ( ( ruleName ) )
            // InternalSQLParser.g:2519:4: ( ruleName )
            {
            // InternalSQLParser.g:2519:4: ( ruleName )
            // InternalSQLParser.g:2520:5: ruleName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReferencesRule());
            					}
            				

            					newCompositeNode(grammarAccess.getReferencesAccess().getRefTableTableCrossReference_1_0());
            				
            pushFollow(FOLLOW_48);
            ruleName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSQLParser.g:2534:3: (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==LeftParenthesis) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalSQLParser.g:2535:4: otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_5); 

                    				newLeafNode(otherlv_3, grammarAccess.getReferencesAccess().getLeftParenthesisKeyword_2_0());
                    			
                    // InternalSQLParser.g:2539:4: ( ( ruleName ) )
                    // InternalSQLParser.g:2540:5: ( ruleName )
                    {
                    // InternalSQLParser.g:2540:5: ( ruleName )
                    // InternalSQLParser.g:2541:6: ruleName
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getReferencesRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getReferencesAccess().getRefColumnsColumnCrossReference_2_1_0());
                    					
                    pushFollow(FOLLOW_11);
                    ruleName();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalSQLParser.g:2555:4: (otherlv_5= Comma ( ( ruleName ) ) )*
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( (LA40_0==Comma) ) {
                            alt40=1;
                        }


                        switch (alt40) {
                    	case 1 :
                    	    // InternalSQLParser.g:2556:5: otherlv_5= Comma ( ( ruleName ) )
                    	    {
                    	    otherlv_5=(Token)match(input,Comma,FOLLOW_5); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getReferencesAccess().getCommaKeyword_2_2_0());
                    	    				
                    	    // InternalSQLParser.g:2560:5: ( ( ruleName ) )
                    	    // InternalSQLParser.g:2561:6: ( ruleName )
                    	    {
                    	    // InternalSQLParser.g:2561:6: ( ruleName )
                    	    // InternalSQLParser.g:2562:7: ruleName
                    	    {

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getReferencesRule());
                    	    							}
                    	    						

                    	    							newCompositeNode(grammarAccess.getReferencesAccess().getRefColumnsColumnCrossReference_2_2_1_0());
                    	    						
                    	    pushFollow(FOLLOW_11);
                    	    ruleName();

                    	    state._fsp--;


                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop40;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,RightParenthesis,FOLLOW_49); 

                    				newLeafNode(otherlv_7, grammarAccess.getReferencesAccess().getRightParenthesisKeyword_2_3());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:2582:3: (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==MATCH) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalSQLParser.g:2583:4: otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) )
                    {
                    otherlv_8=(Token)match(input,MATCH,FOLLOW_50); 

                    				newLeafNode(otherlv_8, grammarAccess.getReferencesAccess().getMATCHKeyword_3_0());
                    			
                    // InternalSQLParser.g:2587:4: ( (lv_match_9_0= ruleMatchKind ) )
                    // InternalSQLParser.g:2588:5: (lv_match_9_0= ruleMatchKind )
                    {
                    // InternalSQLParser.g:2588:5: (lv_match_9_0= ruleMatchKind )
                    // InternalSQLParser.g:2589:6: lv_match_9_0= ruleMatchKind
                    {

                    						newCompositeNode(grammarAccess.getReferencesAccess().getMatchMatchKindEnumRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_51);
                    lv_match_9_0=ruleMatchKind();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getReferencesRule());
                    						}
                    						set(
                    							current,
                    							"match",
                    							lv_match_9_0,
                    							"io.shardml.SQL.MatchKind");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalSQLParser.g:2607:3: ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )?
            int alt45=3;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==ON) ) {
                int LA45_1 = input.LA(2);

                if ( (LA45_1==UPDATE) ) {
                    alt45=2;
                }
                else if ( (LA45_1==DELETE) ) {
                    alt45=1;
                }
            }
            switch (alt45) {
                case 1 :
                    // InternalSQLParser.g:2608:4: (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? )
                    {
                    // InternalSQLParser.g:2608:4: (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? )
                    // InternalSQLParser.g:2609:5: this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )?
                    {

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getReferencesRule());
                    					}
                    					newCompositeNode(grammarAccess.getReferencesAccess().getOnDeleteParserRuleCall_4_0_0());
                    				
                    pushFollow(FOLLOW_51);
                    this_OnDelete_10=ruleOnDelete(current);

                    state._fsp--;


                    					current = this_OnDelete_10;
                    					afterParserOrEnumRuleCall();
                    				
                    // InternalSQLParser.g:2620:5: (this_OnUpdate_11= ruleOnUpdate[$current] )?
                    int alt43=2;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==ON) ) {
                        alt43=1;
                    }
                    switch (alt43) {
                        case 1 :
                            // InternalSQLParser.g:2621:6: this_OnUpdate_11= ruleOnUpdate[$current]
                            {

                            						if (current==null) {
                            							current = createModelElement(grammarAccess.getReferencesRule());
                            						}
                            						newCompositeNode(grammarAccess.getReferencesAccess().getOnUpdateParserRuleCall_4_0_1());
                            					
                            pushFollow(FOLLOW_52);
                            this_OnUpdate_11=ruleOnUpdate(current);

                            state._fsp--;


                            						current = this_OnUpdate_11;
                            						afterParserOrEnumRuleCall();
                            					

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2635:4: (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? )
                    {
                    // InternalSQLParser.g:2635:4: (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? )
                    // InternalSQLParser.g:2636:5: this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )?
                    {

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getReferencesRule());
                    					}
                    					newCompositeNode(grammarAccess.getReferencesAccess().getOnUpdateParserRuleCall_4_1_0());
                    				
                    pushFollow(FOLLOW_51);
                    this_OnUpdate_12=ruleOnUpdate(current);

                    state._fsp--;


                    					current = this_OnUpdate_12;
                    					afterParserOrEnumRuleCall();
                    				
                    // InternalSQLParser.g:2647:5: (this_OnDelete_13= ruleOnDelete[$current] )?
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==ON) ) {
                        alt44=1;
                    }
                    switch (alt44) {
                        case 1 :
                            // InternalSQLParser.g:2648:6: this_OnDelete_13= ruleOnDelete[$current]
                            {

                            						if (current==null) {
                            							current = createModelElement(grammarAccess.getReferencesRule());
                            						}
                            						newCompositeNode(grammarAccess.getReferencesAccess().getOnDeleteParserRuleCall_4_1_1());
                            					
                            pushFollow(FOLLOW_52);
                            this_OnDelete_13=ruleOnDelete(current);

                            state._fsp--;


                            						current = this_OnDelete_13;
                            						afterParserOrEnumRuleCall();
                            					

                            }
                            break;

                    }


                    }


                    }
                    break;

            }

            // InternalSQLParser.g:2662:3: ( (lv_deferrable_14_0= ruleDeferrability ) )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==NOT) ) {
                int LA46_1 = input.LA(2);

                if ( (LA46_1==DEFERRABLE) ) {
                    alt46=1;
                }
            }
            else if ( (LA46_0==DEFERRABLE||LA46_0==INITIALLY) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalSQLParser.g:2663:4: (lv_deferrable_14_0= ruleDeferrability )
                    {
                    // InternalSQLParser.g:2663:4: (lv_deferrable_14_0= ruleDeferrability )
                    // InternalSQLParser.g:2664:5: lv_deferrable_14_0= ruleDeferrability
                    {

                    					newCompositeNode(grammarAccess.getReferencesAccess().getDeferrableDeferrabilityParserRuleCall_5_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_deferrable_14_0=ruleDeferrability();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getReferencesRule());
                    					}
                    					set(
                    						current,
                    						"deferrable",
                    						lv_deferrable_14_0,
                    						"io.shardml.SQL.Deferrability");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleReferences"


    // $ANTLR start "ruleOnDelete"
    // InternalSQLParser.g:2686:1: ruleOnDelete[EObject in_current] returns [EObject current=in_current] : (otherlv_0= ON otherlv_1= DELETE ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) ) ) ;
    public final EObject ruleOnDelete(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_onDelete_2_0 = null;

        Enumerator lv_onDelete_4_0 = null;

        Enumerator lv_onDelete_6_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2692:2: ( (otherlv_0= ON otherlv_1= DELETE ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) ) ) )
            // InternalSQLParser.g:2693:2: (otherlv_0= ON otherlv_1= DELETE ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) ) )
            {
            // InternalSQLParser.g:2693:2: (otherlv_0= ON otherlv_1= DELETE ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) ) )
            // InternalSQLParser.g:2694:3: otherlv_0= ON otherlv_1= DELETE ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) )
            {
            otherlv_0=(Token)match(input,ON,FOLLOW_53); 

            			newLeafNode(otherlv_0, grammarAccess.getOnDeleteAccess().getONKeyword_0());
            		
            otherlv_1=(Token)match(input,DELETE,FOLLOW_54); 

            			newLeafNode(otherlv_1, grammarAccess.getOnDeleteAccess().getDELETEKeyword_1());
            		
            // InternalSQLParser.g:2702:3: ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) )
            int alt47=3;
            switch ( input.LA(1) ) {
            case RESTRICT:
            case CASCADE:
                {
                alt47=1;
                }
                break;
            case NO:
                {
                alt47=2;
                }
                break;
            case SET:
                {
                alt47=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }

            switch (alt47) {
                case 1 :
                    // InternalSQLParser.g:2703:4: ( (lv_onDelete_2_0= ruleRefAction ) )
                    {
                    // InternalSQLParser.g:2703:4: ( (lv_onDelete_2_0= ruleRefAction ) )
                    // InternalSQLParser.g:2704:5: (lv_onDelete_2_0= ruleRefAction )
                    {
                    // InternalSQLParser.g:2704:5: (lv_onDelete_2_0= ruleRefAction )
                    // InternalSQLParser.g:2705:6: lv_onDelete_2_0= ruleRefAction
                    {

                    						newCompositeNode(grammarAccess.getOnDeleteAccess().getOnDeleteRefActionEnumRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_onDelete_2_0=ruleRefAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOnDeleteRule());
                    						}
                    						set(
                    							current,
                    							"onDelete",
                    							lv_onDelete_2_0,
                    							"io.shardml.SQL.RefAction");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2723:4: (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) )
                    {
                    // InternalSQLParser.g:2723:4: (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) )
                    // InternalSQLParser.g:2724:5: otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) )
                    {
                    otherlv_3=(Token)match(input,NO,FOLLOW_55); 

                    					newLeafNode(otherlv_3, grammarAccess.getOnDeleteAccess().getNOKeyword_2_1_0());
                    				
                    // InternalSQLParser.g:2728:5: ( (lv_onDelete_4_0= ruleNoAction ) )
                    // InternalSQLParser.g:2729:6: (lv_onDelete_4_0= ruleNoAction )
                    {
                    // InternalSQLParser.g:2729:6: (lv_onDelete_4_0= ruleNoAction )
                    // InternalSQLParser.g:2730:7: lv_onDelete_4_0= ruleNoAction
                    {

                    							newCompositeNode(grammarAccess.getOnDeleteAccess().getOnDeleteNoActionEnumRuleCall_2_1_1_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_onDelete_4_0=ruleNoAction();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getOnDeleteRule());
                    							}
                    							set(
                    								current,
                    								"onDelete",
                    								lv_onDelete_4_0,
                    								"io.shardml.SQL.NoAction");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:2749:4: (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) )
                    {
                    // InternalSQLParser.g:2749:4: (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) )
                    // InternalSQLParser.g:2750:5: otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) )
                    {
                    otherlv_5=(Token)match(input,SET,FOLLOW_56); 

                    					newLeafNode(otherlv_5, grammarAccess.getOnDeleteAccess().getSETKeyword_2_2_0());
                    				
                    // InternalSQLParser.g:2754:5: ( (lv_onDelete_6_0= ruleSetAction ) )
                    // InternalSQLParser.g:2755:6: (lv_onDelete_6_0= ruleSetAction )
                    {
                    // InternalSQLParser.g:2755:6: (lv_onDelete_6_0= ruleSetAction )
                    // InternalSQLParser.g:2756:7: lv_onDelete_6_0= ruleSetAction
                    {

                    							newCompositeNode(grammarAccess.getOnDeleteAccess().getOnDeleteSetActionEnumRuleCall_2_2_1_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_onDelete_6_0=ruleSetAction();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getOnDeleteRule());
                    							}
                    							set(
                    								current,
                    								"onDelete",
                    								lv_onDelete_6_0,
                    								"io.shardml.SQL.SetAction");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOnDelete"


    // $ANTLR start "ruleOnUpdate"
    // InternalSQLParser.g:2780:1: ruleOnUpdate[EObject in_current] returns [EObject current=in_current] : (otherlv_0= ON otherlv_1= UPDATE ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) ) ) ;
    public final EObject ruleOnUpdate(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Enumerator lv_onUpdate_2_0 = null;

        Enumerator lv_onUpdate_4_0 = null;

        Enumerator lv_onUpdate_6_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2786:2: ( (otherlv_0= ON otherlv_1= UPDATE ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) ) ) )
            // InternalSQLParser.g:2787:2: (otherlv_0= ON otherlv_1= UPDATE ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) ) )
            {
            // InternalSQLParser.g:2787:2: (otherlv_0= ON otherlv_1= UPDATE ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) ) )
            // InternalSQLParser.g:2788:3: otherlv_0= ON otherlv_1= UPDATE ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) )
            {
            otherlv_0=(Token)match(input,ON,FOLLOW_57); 

            			newLeafNode(otherlv_0, grammarAccess.getOnUpdateAccess().getONKeyword_0());
            		
            otherlv_1=(Token)match(input,UPDATE,FOLLOW_54); 

            			newLeafNode(otherlv_1, grammarAccess.getOnUpdateAccess().getUPDATEKeyword_1());
            		
            // InternalSQLParser.g:2796:3: ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) )
            int alt48=3;
            switch ( input.LA(1) ) {
            case RESTRICT:
            case CASCADE:
                {
                alt48=1;
                }
                break;
            case NO:
                {
                alt48=2;
                }
                break;
            case SET:
                {
                alt48=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }

            switch (alt48) {
                case 1 :
                    // InternalSQLParser.g:2797:4: ( (lv_onUpdate_2_0= ruleRefAction ) )
                    {
                    // InternalSQLParser.g:2797:4: ( (lv_onUpdate_2_0= ruleRefAction ) )
                    // InternalSQLParser.g:2798:5: (lv_onUpdate_2_0= ruleRefAction )
                    {
                    // InternalSQLParser.g:2798:5: (lv_onUpdate_2_0= ruleRefAction )
                    // InternalSQLParser.g:2799:6: lv_onUpdate_2_0= ruleRefAction
                    {

                    						newCompositeNode(grammarAccess.getOnUpdateAccess().getOnUpdateRefActionEnumRuleCall_2_0_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_onUpdate_2_0=ruleRefAction();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOnUpdateRule());
                    						}
                    						set(
                    							current,
                    							"onUpdate",
                    							lv_onUpdate_2_0,
                    							"io.shardml.SQL.RefAction");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2817:4: (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) )
                    {
                    // InternalSQLParser.g:2817:4: (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) )
                    // InternalSQLParser.g:2818:5: otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) )
                    {
                    otherlv_3=(Token)match(input,NO,FOLLOW_55); 

                    					newLeafNode(otherlv_3, grammarAccess.getOnUpdateAccess().getNOKeyword_2_1_0());
                    				
                    // InternalSQLParser.g:2822:5: ( (lv_onUpdate_4_0= ruleNoAction ) )
                    // InternalSQLParser.g:2823:6: (lv_onUpdate_4_0= ruleNoAction )
                    {
                    // InternalSQLParser.g:2823:6: (lv_onUpdate_4_0= ruleNoAction )
                    // InternalSQLParser.g:2824:7: lv_onUpdate_4_0= ruleNoAction
                    {

                    							newCompositeNode(grammarAccess.getOnUpdateAccess().getOnUpdateNoActionEnumRuleCall_2_1_1_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_onUpdate_4_0=ruleNoAction();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getOnUpdateRule());
                    							}
                    							set(
                    								current,
                    								"onUpdate",
                    								lv_onUpdate_4_0,
                    								"io.shardml.SQL.NoAction");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:2843:4: (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) )
                    {
                    // InternalSQLParser.g:2843:4: (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) )
                    // InternalSQLParser.g:2844:5: otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) )
                    {
                    otherlv_5=(Token)match(input,SET,FOLLOW_56); 

                    					newLeafNode(otherlv_5, grammarAccess.getOnUpdateAccess().getSETKeyword_2_2_0());
                    				
                    // InternalSQLParser.g:2848:5: ( (lv_onUpdate_6_0= ruleSetAction ) )
                    // InternalSQLParser.g:2849:6: (lv_onUpdate_6_0= ruleSetAction )
                    {
                    // InternalSQLParser.g:2849:6: (lv_onUpdate_6_0= ruleSetAction )
                    // InternalSQLParser.g:2850:7: lv_onUpdate_6_0= ruleSetAction
                    {

                    							newCompositeNode(grammarAccess.getOnUpdateAccess().getOnUpdateSetActionEnumRuleCall_2_2_1_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_onUpdate_6_0=ruleSetAction();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getOnUpdateRule());
                    							}
                    							set(
                    								current,
                    								"onUpdate",
                    								lv_onUpdate_6_0,
                    								"io.shardml.SQL.SetAction");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOnUpdate"


    // $ANTLR start "entryRuleDeferrability"
    // InternalSQLParser.g:2873:1: entryRuleDeferrability returns [String current=null] : iv_ruleDeferrability= ruleDeferrability EOF ;
    public final String entryRuleDeferrability() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDeferrability = null;


        try {
            // InternalSQLParser.g:2873:53: (iv_ruleDeferrability= ruleDeferrability EOF )
            // InternalSQLParser.g:2874:2: iv_ruleDeferrability= ruleDeferrability EOF
            {
             newCompositeNode(grammarAccess.getDeferrabilityRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDeferrability=ruleDeferrability();

            state._fsp--;

             current =iv_ruleDeferrability.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDeferrability"


    // $ANTLR start "ruleDeferrability"
    // InternalSQLParser.g:2880:1: ruleDeferrability returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? ) | (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? ) ) ;
    public final AntlrDatatypeRuleToken ruleDeferrability() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSQLParser.g:2886:2: ( ( ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? ) | (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? ) ) )
            // InternalSQLParser.g:2887:2: ( ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? ) | (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? ) )
            {
            // InternalSQLParser.g:2887:2: ( ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? ) | (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? ) )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==DEFERRABLE||LA55_0==NOT) ) {
                alt55=1;
            }
            else if ( (LA55_0==INITIALLY) ) {
                alt55=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    // InternalSQLParser.g:2888:3: ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? )
                    {
                    // InternalSQLParser.g:2888:3: ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? )
                    // InternalSQLParser.g:2889:4: (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )?
                    {
                    // InternalSQLParser.g:2889:4: (kw= NOT )?
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==NOT) ) {
                        alt49=1;
                    }
                    switch (alt49) {
                        case 1 :
                            // InternalSQLParser.g:2890:5: kw= NOT
                            {
                            kw=(Token)match(input,NOT,FOLLOW_58); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getNOTKeyword_0_0());
                            				

                            }
                            break;

                    }

                    kw=(Token)match(input,DEFERRABLE,FOLLOW_59); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getDEFERRABLEKeyword_0_1());
                    			
                    // InternalSQLParser.g:2901:4: (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )?
                    int alt51=2;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==INITIALLY) ) {
                        alt51=1;
                    }
                    switch (alt51) {
                        case 1 :
                            // InternalSQLParser.g:2902:5: kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE )
                            {
                            kw=(Token)match(input,INITIALLY,FOLLOW_60); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getINITIALLYKeyword_0_2_0());
                            				
                            // InternalSQLParser.g:2907:5: (kw= DEFERRED | kw= IMMEDIATE )
                            int alt50=2;
                            int LA50_0 = input.LA(1);

                            if ( (LA50_0==DEFERRED) ) {
                                alt50=1;
                            }
                            else if ( (LA50_0==IMMEDIATE) ) {
                                alt50=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 50, 0, input);

                                throw nvae;
                            }
                            switch (alt50) {
                                case 1 :
                                    // InternalSQLParser.g:2908:6: kw= DEFERRED
                                    {
                                    kw=(Token)match(input,DEFERRED,FOLLOW_2); 

                                    						current.merge(kw);
                                    						newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getDEFERREDKeyword_0_2_1_0());
                                    					

                                    }
                                    break;
                                case 2 :
                                    // InternalSQLParser.g:2914:6: kw= IMMEDIATE
                                    {
                                    kw=(Token)match(input,IMMEDIATE,FOLLOW_2); 

                                    						current.merge(kw);
                                    						newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getIMMEDIATEKeyword_0_2_1_1());
                                    					

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2923:3: (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? )
                    {
                    // InternalSQLParser.g:2923:3: (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? )
                    // InternalSQLParser.g:2924:4: kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )?
                    {
                    kw=(Token)match(input,INITIALLY,FOLLOW_60); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getINITIALLYKeyword_1_0());
                    			
                    // InternalSQLParser.g:2929:4: (kw= DEFERRED | kw= IMMEDIATE )
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==DEFERRED) ) {
                        alt52=1;
                    }
                    else if ( (LA52_0==IMMEDIATE) ) {
                        alt52=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 52, 0, input);

                        throw nvae;
                    }
                    switch (alt52) {
                        case 1 :
                            // InternalSQLParser.g:2930:5: kw= DEFERRED
                            {
                            kw=(Token)match(input,DEFERRED,FOLLOW_61); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getDEFERREDKeyword_1_1_0());
                            				

                            }
                            break;
                        case 2 :
                            // InternalSQLParser.g:2936:5: kw= IMMEDIATE
                            {
                            kw=(Token)match(input,IMMEDIATE,FOLLOW_61); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getIMMEDIATEKeyword_1_1_1());
                            				

                            }
                            break;

                    }

                    // InternalSQLParser.g:2942:4: ( (kw= NOT )? kw= DEFERRABLE )?
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( (LA54_0==NOT) ) {
                        int LA54_1 = input.LA(2);

                        if ( (LA54_1==DEFERRABLE) ) {
                            alt54=1;
                        }
                    }
                    else if ( (LA54_0==DEFERRABLE) ) {
                        alt54=1;
                    }
                    switch (alt54) {
                        case 1 :
                            // InternalSQLParser.g:2943:5: (kw= NOT )? kw= DEFERRABLE
                            {
                            // InternalSQLParser.g:2943:5: (kw= NOT )?
                            int alt53=2;
                            int LA53_0 = input.LA(1);

                            if ( (LA53_0==NOT) ) {
                                alt53=1;
                            }
                            switch (alt53) {
                                case 1 :
                                    // InternalSQLParser.g:2944:6: kw= NOT
                                    {
                                    kw=(Token)match(input,NOT,FOLLOW_58); 

                                    						current.merge(kw);
                                    						newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getNOTKeyword_1_2_0());
                                    					

                                    }
                                    break;

                            }

                            kw=(Token)match(input,DEFERRABLE,FOLLOW_2); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getDEFERRABLEKeyword_1_2_1());
                            				

                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDeferrability"


    // $ANTLR start "entryRuleGeneratedTail"
    // InternalSQLParser.g:2961:1: entryRuleGeneratedTail returns [String current=null] : iv_ruleGeneratedTail= ruleGeneratedTail EOF ;
    public final String entryRuleGeneratedTail() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleGeneratedTail = null;


        try {
            // InternalSQLParser.g:2961:53: (iv_ruleGeneratedTail= ruleGeneratedTail EOF )
            // InternalSQLParser.g:2962:2: iv_ruleGeneratedTail= ruleGeneratedTail EOF
            {
             newCompositeNode(grammarAccess.getGeneratedTailRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGeneratedTail=ruleGeneratedTail();

            state._fsp--;

             current =iv_ruleGeneratedTail.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGeneratedTail"


    // $ANTLR start "ruleGeneratedTail"
    // InternalSQLParser.g:2968:1: ruleGeneratedTail returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= ALWAYS | (kw= BY kw= DEFAULT ) ) kw= AS ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) ) ) ;
    public final AntlrDatatypeRuleToken ruleGeneratedTail() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Parenthesized_5 = null;

        AntlrDatatypeRuleToken this_Parenthesized_6 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2974:2: ( ( (kw= ALWAYS | (kw= BY kw= DEFAULT ) ) kw= AS ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) ) ) )
            // InternalSQLParser.g:2975:2: ( (kw= ALWAYS | (kw= BY kw= DEFAULT ) ) kw= AS ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) ) )
            {
            // InternalSQLParser.g:2975:2: ( (kw= ALWAYS | (kw= BY kw= DEFAULT ) ) kw= AS ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) ) )
            // InternalSQLParser.g:2976:3: (kw= ALWAYS | (kw= BY kw= DEFAULT ) ) kw= AS ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) )
            {
            // InternalSQLParser.g:2976:3: (kw= ALWAYS | (kw= BY kw= DEFAULT ) )
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==ALWAYS) ) {
                alt56=1;
            }
            else if ( (LA56_0==BY) ) {
                alt56=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }
            switch (alt56) {
                case 1 :
                    // InternalSQLParser.g:2977:4: kw= ALWAYS
                    {
                    kw=(Token)match(input,ALWAYS,FOLLOW_62); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getALWAYSKeyword_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2983:4: (kw= BY kw= DEFAULT )
                    {
                    // InternalSQLParser.g:2983:4: (kw= BY kw= DEFAULT )
                    // InternalSQLParser.g:2984:5: kw= BY kw= DEFAULT
                    {
                    kw=(Token)match(input,BY,FOLLOW_63); 

                    					current.merge(kw);
                    					newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getBYKeyword_0_1_0());
                    				
                    kw=(Token)match(input,DEFAULT,FOLLOW_62); 

                    					current.merge(kw);
                    					newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getDEFAULTKeyword_0_1_1());
                    				

                    }


                    }
                    break;

            }

            kw=(Token)match(input,AS,FOLLOW_64); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getASKeyword_1());
            		
            // InternalSQLParser.g:3001:3: ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==IDENTITY) ) {
                alt58=1;
            }
            else if ( (LA58_0==LeftParenthesis) ) {
                alt58=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // InternalSQLParser.g:3002:4: (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? )
                    {
                    // InternalSQLParser.g:3002:4: (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? )
                    // InternalSQLParser.g:3003:5: kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )?
                    {
                    kw=(Token)match(input,IDENTITY,FOLLOW_65); 

                    					current.merge(kw);
                    					newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getIDENTITYKeyword_2_0_0());
                    				
                    // InternalSQLParser.g:3008:5: (this_Parenthesized_5= ruleParenthesized )?
                    int alt57=2;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==LeftParenthesis) ) {
                        alt57=1;
                    }
                    switch (alt57) {
                        case 1 :
                            // InternalSQLParser.g:3009:6: this_Parenthesized_5= ruleParenthesized
                            {

                            						newCompositeNode(grammarAccess.getGeneratedTailAccess().getParenthesizedParserRuleCall_2_0_1());
                            					
                            pushFollow(FOLLOW_2);
                            this_Parenthesized_5=ruleParenthesized();

                            state._fsp--;


                            						current.merge(this_Parenthesized_5);
                            					

                            						afterParserOrEnumRuleCall();
                            					

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3022:4: (this_Parenthesized_6= ruleParenthesized kw= STORED )
                    {
                    // InternalSQLParser.g:3022:4: (this_Parenthesized_6= ruleParenthesized kw= STORED )
                    // InternalSQLParser.g:3023:5: this_Parenthesized_6= ruleParenthesized kw= STORED
                    {

                    					newCompositeNode(grammarAccess.getGeneratedTailAccess().getParenthesizedParserRuleCall_2_1_0());
                    				
                    pushFollow(FOLLOW_66);
                    this_Parenthesized_6=ruleParenthesized();

                    state._fsp--;


                    					current.merge(this_Parenthesized_6);
                    				

                    					afterParserOrEnumRuleCall();
                    				
                    kw=(Token)match(input,STORED,FOLLOW_2); 

                    					current.merge(kw);
                    					newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getSTOREDKeyword_2_1_1());
                    				

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGeneratedTail"


    // $ANTLR start "entryRuleDefaultExpr"
    // InternalSQLParser.g:3044:1: entryRuleDefaultExpr returns [String current=null] : iv_ruleDefaultExpr= ruleDefaultExpr EOF ;
    public final String entryRuleDefaultExpr() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDefaultExpr = null;


        try {
            // InternalSQLParser.g:3044:51: (iv_ruleDefaultExpr= ruleDefaultExpr EOF )
            // InternalSQLParser.g:3045:2: iv_ruleDefaultExpr= ruleDefaultExpr EOF
            {
             newCompositeNode(grammarAccess.getDefaultExprRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDefaultExpr=ruleDefaultExpr();

            state._fsp--;

             current =iv_ruleDefaultExpr.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDefaultExpr"


    // $ANTLR start "ruleDefaultExpr"
    // InternalSQLParser.g:3051:1: ruleDefaultExpr returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ExprToken_0= ruleExprToken | kw= NULL ) (this_ExprToken_2= ruleExprToken )* ) ;
    public final AntlrDatatypeRuleToken ruleDefaultExpr() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ExprToken_0 = null;

        AntlrDatatypeRuleToken this_ExprToken_2 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3057:2: ( ( (this_ExprToken_0= ruleExprToken | kw= NULL ) (this_ExprToken_2= ruleExprToken )* ) )
            // InternalSQLParser.g:3058:2: ( (this_ExprToken_0= ruleExprToken | kw= NULL ) (this_ExprToken_2= ruleExprToken )* )
            {
            // InternalSQLParser.g:3058:2: ( (this_ExprToken_0= ruleExprToken | kw= NULL ) (this_ExprToken_2= ruleExprToken )* )
            // InternalSQLParser.g:3059:3: (this_ExprToken_0= ruleExprToken | kw= NULL ) (this_ExprToken_2= ruleExprToken )*
            {
            // InternalSQLParser.g:3059:3: (this_ExprToken_0= ruleExprToken | kw= NULL )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==TIMESTAMPTZ||(LA59_0>=BIGSERIAL && LA59_0<=CHARACTER)||LA59_0==IMMEDIATE||(LA59_0>=PRECISION && LA59_0<=DECIMAL)||(LA59_0>=INCLUDE && LA59_0<=NUMERIC)||(LA59_0>=VARCHAR && LA59_0<=BIGINT)||(LA59_0>=DELETE && LA59_0<=STORED)||(LA59_0>=UPDATE && LA59_0<=BYTEA)||(LA59_0>=FLOAT && LA59_0<=MATCH)||(LA59_0>=CHAR && LA59_0<=DATE)||(LA59_0>=REAL && LA59_0<=KEY)||LA59_0==SET||(LA59_0>=BY && LA59_0<=NO)||LA59_0==LeftParenthesis||LA59_0==FullStop||(LA59_0>=RULE_ID && LA59_0<=RULE_INT)||LA59_0==RULE_ANY_OTHER) ) {
                alt59=1;
            }
            else if ( (LA59_0==NULL) ) {
                alt59=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // InternalSQLParser.g:3060:4: this_ExprToken_0= ruleExprToken
                    {

                    				newCompositeNode(grammarAccess.getDefaultExprAccess().getExprTokenParserRuleCall_0_0());
                    			
                    pushFollow(FOLLOW_67);
                    this_ExprToken_0=ruleExprToken();

                    state._fsp--;


                    				current.merge(this_ExprToken_0);
                    			

                    				afterParserOrEnumRuleCall();
                    			

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3071:4: kw= NULL
                    {
                    kw=(Token)match(input,NULL,FOLLOW_67); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDefaultExprAccess().getNULLKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:3077:3: (this_ExprToken_2= ruleExprToken )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==TIMESTAMPTZ||(LA60_0>=BIGSERIAL && LA60_0<=CHARACTER)||LA60_0==IMMEDIATE||(LA60_0>=PRECISION && LA60_0<=DECIMAL)||(LA60_0>=INCLUDE && LA60_0<=NUMERIC)||(LA60_0>=VARCHAR && LA60_0<=BIGINT)||(LA60_0>=DELETE && LA60_0<=STORED)||(LA60_0>=UPDATE && LA60_0<=BYTEA)||(LA60_0>=FLOAT && LA60_0<=MATCH)||(LA60_0>=CHAR && LA60_0<=DATE)||(LA60_0>=REAL && LA60_0<=KEY)||LA60_0==SET||(LA60_0>=BY && LA60_0<=NO)||LA60_0==LeftParenthesis||LA60_0==FullStop||(LA60_0>=RULE_ID && LA60_0<=RULE_INT)||LA60_0==RULE_ANY_OTHER) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // InternalSQLParser.g:3078:4: this_ExprToken_2= ruleExprToken
            	    {

            	    				newCompositeNode(grammarAccess.getDefaultExprAccess().getExprTokenParserRuleCall_1());
            	    			
            	    pushFollow(FOLLOW_67);
            	    this_ExprToken_2=ruleExprToken();

            	    state._fsp--;


            	    				current.merge(this_ExprToken_2);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDefaultExpr"


    // $ANTLR start "entryRuleExprToken"
    // InternalSQLParser.g:3093:1: entryRuleExprToken returns [String current=null] : iv_ruleExprToken= ruleExprToken EOF ;
    public final String entryRuleExprToken() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleExprToken = null;


        try {
            // InternalSQLParser.g:3093:49: (iv_ruleExprToken= ruleExprToken EOF )
            // InternalSQLParser.g:3094:2: iv_ruleExprToken= ruleExprToken EOF
            {
             newCompositeNode(grammarAccess.getExprTokenRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExprToken=ruleExprToken();

            state._fsp--;

             current =iv_ruleExprToken.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExprToken"


    // $ANTLR start "ruleExprToken"
    // InternalSQLParser.g:3100:1: ruleExprToken returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ExprName_0= ruleExprName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= WITH | this_Parenthesized_6= ruleParenthesized ) ;
    public final AntlrDatatypeRuleToken ruleExprToken() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_1=null;
        Token this_INT_2=null;
        Token this_ANY_OTHER_3=null;
        Token kw=null;
        AntlrDatatypeRuleToken this_ExprName_0 = null;

        AntlrDatatypeRuleToken this_Parenthesized_6 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3106:2: ( (this_ExprName_0= ruleExprName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= WITH | this_Parenthesized_6= ruleParenthesized ) )
            // InternalSQLParser.g:3107:2: (this_ExprName_0= ruleExprName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= WITH | this_Parenthesized_6= ruleParenthesized )
            {
            // InternalSQLParser.g:3107:2: (this_ExprName_0= ruleExprName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= WITH | this_Parenthesized_6= ruleParenthesized )
            int alt61=7;
            switch ( input.LA(1) ) {
            case TIMESTAMPTZ:
            case BIGSERIAL:
            case CHARACTER:
            case IMMEDIATE:
            case PRECISION:
            case TIMESTAMP:
            case DEFERRED:
            case IDENTITY:
            case RESTRICT:
            case SMALLINT:
            case BOOLEAN:
            case CASCADE:
            case DECIMAL:
            case INCLUDE:
            case INTEGER:
            case NUMERIC:
            case VARCHAR:
            case VARYING:
            case WITHOUT:
            case ACTION:
            case ALWAYS:
            case BIGINT:
            case DELETE:
            case DOUBLE:
            case EXISTS:
            case SCHEMA:
            case SERIAL:
            case SIMPLE:
            case STORED:
            case UPDATE:
            case ALTER:
            case BYTEA:
            case FLOAT:
            case INDEX:
            case JSONB:
            case MATCH:
            case CHAR:
            case DATE:
            case REAL:
            case TEXT:
            case TIME:
            case UUID:
            case ZONE:
            case ADD:
            case INT:
            case KEY:
            case SET:
            case BY:
            case IF:
            case NO:
            case RULE_ID:
            case RULE_QUOTED_ID:
                {
                alt61=1;
                }
                break;
            case RULE_STRING:
                {
                alt61=2;
                }
                break;
            case RULE_INT:
                {
                alt61=3;
                }
                break;
            case RULE_ANY_OTHER:
                {
                alt61=4;
                }
                break;
            case FullStop:
                {
                alt61=5;
                }
                break;
            case WITH:
                {
                alt61=6;
                }
                break;
            case LeftParenthesis:
                {
                alt61=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }

            switch (alt61) {
                case 1 :
                    // InternalSQLParser.g:3108:3: this_ExprName_0= ruleExprName
                    {

                    			newCompositeNode(grammarAccess.getExprTokenAccess().getExprNameParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExprName_0=ruleExprName();

                    state._fsp--;


                    			current.merge(this_ExprName_0);
                    		

                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3119:3: this_STRING_1= RULE_STRING
                    {
                    this_STRING_1=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    			current.merge(this_STRING_1);
                    		

                    			newLeafNode(this_STRING_1, grammarAccess.getExprTokenAccess().getSTRINGTerminalRuleCall_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:3127:3: this_INT_2= RULE_INT
                    {
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_2); 

                    			current.merge(this_INT_2);
                    		

                    			newLeafNode(this_INT_2, grammarAccess.getExprTokenAccess().getINTTerminalRuleCall_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:3135:3: this_ANY_OTHER_3= RULE_ANY_OTHER
                    {
                    this_ANY_OTHER_3=(Token)match(input,RULE_ANY_OTHER,FOLLOW_2); 

                    			current.merge(this_ANY_OTHER_3);
                    		

                    			newLeafNode(this_ANY_OTHER_3, grammarAccess.getExprTokenAccess().getANY_OTHERTerminalRuleCall_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:3143:3: kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprTokenAccess().getFullStopKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:3149:3: kw= WITH
                    {
                    kw=(Token)match(input,WITH,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprTokenAccess().getWITHKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:3155:3: this_Parenthesized_6= ruleParenthesized
                    {

                    			newCompositeNode(grammarAccess.getExprTokenAccess().getParenthesizedParserRuleCall_6());
                    		
                    pushFollow(FOLLOW_2);
                    this_Parenthesized_6=ruleParenthesized();

                    state._fsp--;


                    			current.merge(this_Parenthesized_6);
                    		

                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExprToken"


    // $ANTLR start "entryRuleParenthesized"
    // InternalSQLParser.g:3169:1: entryRuleParenthesized returns [String current=null] : iv_ruleParenthesized= ruleParenthesized EOF ;
    public final String entryRuleParenthesized() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleParenthesized = null;


        try {
            // InternalSQLParser.g:3169:53: (iv_ruleParenthesized= ruleParenthesized EOF )
            // InternalSQLParser.g:3170:2: iv_ruleParenthesized= ruleParenthesized EOF
            {
             newCompositeNode(grammarAccess.getParenthesizedRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParenthesized=ruleParenthesized();

            state._fsp--;

             current =iv_ruleParenthesized.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParenthesized"


    // $ANTLR start "ruleParenthesized"
    // InternalSQLParser.g:3176:1: ruleParenthesized returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= LeftParenthesis (this_InnerToken_1= ruleInnerToken )* kw= RightParenthesis ) ;
    public final AntlrDatatypeRuleToken ruleParenthesized() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_InnerToken_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3182:2: ( (kw= LeftParenthesis (this_InnerToken_1= ruleInnerToken )* kw= RightParenthesis ) )
            // InternalSQLParser.g:3183:2: (kw= LeftParenthesis (this_InnerToken_1= ruleInnerToken )* kw= RightParenthesis )
            {
            // InternalSQLParser.g:3183:2: (kw= LeftParenthesis (this_InnerToken_1= ruleInnerToken )* kw= RightParenthesis )
            // InternalSQLParser.g:3184:3: kw= LeftParenthesis (this_InnerToken_1= ruleInnerToken )* kw= RightParenthesis
            {
            kw=(Token)match(input,LeftParenthesis,FOLLOW_68); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getParenthesizedAccess().getLeftParenthesisKeyword_0());
            		
            // InternalSQLParser.g:3189:3: (this_InnerToken_1= ruleInnerToken )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( ((LA62_0>=TIMESTAMPTZ && LA62_0<=LeftParenthesis)||(LA62_0>=Comma && LA62_0<=FullStop)||(LA62_0>=RULE_ID && LA62_0<=RULE_DOLLAR_STRING)||LA62_0==RULE_ANY_OTHER) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // InternalSQLParser.g:3190:4: this_InnerToken_1= ruleInnerToken
            	    {

            	    				newCompositeNode(grammarAccess.getParenthesizedAccess().getInnerTokenParserRuleCall_1());
            	    			
            	    pushFollow(FOLLOW_68);
            	    this_InnerToken_1=ruleInnerToken();

            	    state._fsp--;


            	    				current.merge(this_InnerToken_1);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;

            	default :
            	    break loop62;
                }
            } while (true);

            kw=(Token)match(input,RightParenthesis,FOLLOW_2); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getParenthesizedAccess().getRightParenthesisKeyword_2());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParenthesized"


    // $ANTLR start "entryRuleInnerToken"
    // InternalSQLParser.g:3210:1: entryRuleInnerToken returns [String current=null] : iv_ruleInnerToken= ruleInnerToken EOF ;
    public final String entryRuleInnerToken() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleInnerToken = null;


        try {
            // InternalSQLParser.g:3210:50: (iv_ruleInnerToken= ruleInnerToken EOF )
            // InternalSQLParser.g:3211:2: iv_ruleInnerToken= ruleInnerToken EOF
            {
             newCompositeNode(grammarAccess.getInnerTokenRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInnerToken=ruleInnerToken();

            state._fsp--;

             current =iv_ruleInnerToken.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInnerToken"


    // $ANTLR start "ruleInnerToken"
    // InternalSQLParser.g:3217:1: ruleInnerToken returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Atom_0= ruleAtom | this_Parenthesized_1= ruleParenthesized ) ;
    public final AntlrDatatypeRuleToken ruleInnerToken() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_Atom_0 = null;

        AntlrDatatypeRuleToken this_Parenthesized_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3223:2: ( (this_Atom_0= ruleAtom | this_Parenthesized_1= ruleParenthesized ) )
            // InternalSQLParser.g:3224:2: (this_Atom_0= ruleAtom | this_Parenthesized_1= ruleParenthesized )
            {
            // InternalSQLParser.g:3224:2: (this_Atom_0= ruleAtom | this_Parenthesized_1= ruleParenthesized )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( ((LA63_0>=TIMESTAMPTZ && LA63_0<=ON)||(LA63_0>=Comma && LA63_0<=FullStop)||(LA63_0>=RULE_ID && LA63_0<=RULE_DOLLAR_STRING)||LA63_0==RULE_ANY_OTHER) ) {
                alt63=1;
            }
            else if ( (LA63_0==LeftParenthesis) ) {
                alt63=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }
            switch (alt63) {
                case 1 :
                    // InternalSQLParser.g:3225:3: this_Atom_0= ruleAtom
                    {

                    			newCompositeNode(grammarAccess.getInnerTokenAccess().getAtomParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Atom_0=ruleAtom();

                    state._fsp--;


                    			current.merge(this_Atom_0);
                    		

                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3236:3: this_Parenthesized_1= ruleParenthesized
                    {

                    			newCompositeNode(grammarAccess.getInnerTokenAccess().getParenthesizedParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Parenthesized_1=ruleParenthesized();

                    state._fsp--;


                    			current.merge(this_Parenthesized_1);
                    		

                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInnerToken"


    // $ANTLR start "entryRuleAtom"
    // InternalSQLParser.g:3250:1: entryRuleAtom returns [String current=null] : iv_ruleAtom= ruleAtom EOF ;
    public final String entryRuleAtom() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAtom = null;


        try {
            // InternalSQLParser.g:3250:44: (iv_ruleAtom= ruleAtom EOF )
            // InternalSQLParser.g:3251:2: iv_ruleAtom= ruleAtom EOF
            {
             newCompositeNode(grammarAccess.getAtomRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAtom=ruleAtom();

            state._fsp--;

             current =iv_ruleAtom.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAtom"


    // $ANTLR start "ruleAtom"
    // InternalSQLParser.g:3257:1: ruleAtom returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Name_0= ruleName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | this_DOLLAR_STRING_4= RULE_DOLLAR_STRING | kw= FullStop | kw= Comma | kw= CREATE | kw= TABLE | kw= NOT | kw= NULL | kw= PRIMARY | kw= UNIQUE | kw= WITH | kw= CONSTRAINT | kw= FOREIGN | kw= REFERENCES | kw= ON | kw= DEFAULT | kw= FULL | kw= DEFERRABLE | kw= INITIALLY | kw= CHECK | kw= AS | kw= ONLY | kw= USING | kw= WHERE ) ;
    public final AntlrDatatypeRuleToken ruleAtom() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_1=null;
        Token this_INT_2=null;
        Token this_ANY_OTHER_3=null;
        Token this_DOLLAR_STRING_4=null;
        Token kw=null;
        AntlrDatatypeRuleToken this_Name_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3263:2: ( (this_Name_0= ruleName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | this_DOLLAR_STRING_4= RULE_DOLLAR_STRING | kw= FullStop | kw= Comma | kw= CREATE | kw= TABLE | kw= NOT | kw= NULL | kw= PRIMARY | kw= UNIQUE | kw= WITH | kw= CONSTRAINT | kw= FOREIGN | kw= REFERENCES | kw= ON | kw= DEFAULT | kw= FULL | kw= DEFERRABLE | kw= INITIALLY | kw= CHECK | kw= AS | kw= ONLY | kw= USING | kw= WHERE ) )
            // InternalSQLParser.g:3264:2: (this_Name_0= ruleName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | this_DOLLAR_STRING_4= RULE_DOLLAR_STRING | kw= FullStop | kw= Comma | kw= CREATE | kw= TABLE | kw= NOT | kw= NULL | kw= PRIMARY | kw= UNIQUE | kw= WITH | kw= CONSTRAINT | kw= FOREIGN | kw= REFERENCES | kw= ON | kw= DEFAULT | kw= FULL | kw= DEFERRABLE | kw= INITIALLY | kw= CHECK | kw= AS | kw= ONLY | kw= USING | kw= WHERE )
            {
            // InternalSQLParser.g:3264:2: (this_Name_0= ruleName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | this_DOLLAR_STRING_4= RULE_DOLLAR_STRING | kw= FullStop | kw= Comma | kw= CREATE | kw= TABLE | kw= NOT | kw= NULL | kw= PRIMARY | kw= UNIQUE | kw= WITH | kw= CONSTRAINT | kw= FOREIGN | kw= REFERENCES | kw= ON | kw= DEFAULT | kw= FULL | kw= DEFERRABLE | kw= INITIALLY | kw= CHECK | kw= AS | kw= ONLY | kw= USING | kw= WHERE )
            int alt64=27;
            switch ( input.LA(1) ) {
            case TIMESTAMPTZ:
            case BIGSERIAL:
            case CHARACTER:
            case GENERATED:
            case IMMEDIATE:
            case PRECISION:
            case TIMESTAMP:
            case DEFERRED:
            case IDENTITY:
            case RESTRICT:
            case SMALLINT:
            case BOOLEAN:
            case CASCADE:
            case DECIMAL:
            case INCLUDE:
            case INTEGER:
            case NUMERIC:
            case VARCHAR:
            case VARYING:
            case WITHOUT:
            case ACTION:
            case ALWAYS:
            case BIGINT:
            case DELETE:
            case DOUBLE:
            case EXISTS:
            case SCHEMA:
            case SERIAL:
            case SIMPLE:
            case STORED:
            case UPDATE:
            case ALTER:
            case BYTEA:
            case FLOAT:
            case INDEX:
            case JSONB:
            case MATCH:
            case CHAR:
            case DATE:
            case REAL:
            case TEXT:
            case TIME:
            case UUID:
            case ZONE:
            case ADD:
            case INT:
            case KEY:
            case SET:
            case BY:
            case IF:
            case NO:
            case RULE_ID:
            case RULE_QUOTED_ID:
                {
                alt64=1;
                }
                break;
            case RULE_STRING:
                {
                alt64=2;
                }
                break;
            case RULE_INT:
                {
                alt64=3;
                }
                break;
            case RULE_ANY_OTHER:
                {
                alt64=4;
                }
                break;
            case RULE_DOLLAR_STRING:
                {
                alt64=5;
                }
                break;
            case FullStop:
                {
                alt64=6;
                }
                break;
            case Comma:
                {
                alt64=7;
                }
                break;
            case CREATE:
                {
                alt64=8;
                }
                break;
            case TABLE:
                {
                alt64=9;
                }
                break;
            case NOT:
                {
                alt64=10;
                }
                break;
            case NULL:
                {
                alt64=11;
                }
                break;
            case PRIMARY:
                {
                alt64=12;
                }
                break;
            case UNIQUE:
                {
                alt64=13;
                }
                break;
            case WITH:
                {
                alt64=14;
                }
                break;
            case CONSTRAINT:
                {
                alt64=15;
                }
                break;
            case FOREIGN:
                {
                alt64=16;
                }
                break;
            case REFERENCES:
                {
                alt64=17;
                }
                break;
            case ON:
                {
                alt64=18;
                }
                break;
            case DEFAULT:
                {
                alt64=19;
                }
                break;
            case FULL:
                {
                alt64=20;
                }
                break;
            case DEFERRABLE:
                {
                alt64=21;
                }
                break;
            case INITIALLY:
                {
                alt64=22;
                }
                break;
            case CHECK:
                {
                alt64=23;
                }
                break;
            case AS:
                {
                alt64=24;
                }
                break;
            case ONLY:
                {
                alt64=25;
                }
                break;
            case USING:
                {
                alt64=26;
                }
                break;
            case WHERE:
                {
                alt64=27;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }

            switch (alt64) {
                case 1 :
                    // InternalSQLParser.g:3265:3: this_Name_0= ruleName
                    {

                    			newCompositeNode(grammarAccess.getAtomAccess().getNameParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Name_0=ruleName();

                    state._fsp--;


                    			current.merge(this_Name_0);
                    		

                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3276:3: this_STRING_1= RULE_STRING
                    {
                    this_STRING_1=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    			current.merge(this_STRING_1);
                    		

                    			newLeafNode(this_STRING_1, grammarAccess.getAtomAccess().getSTRINGTerminalRuleCall_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:3284:3: this_INT_2= RULE_INT
                    {
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_2); 

                    			current.merge(this_INT_2);
                    		

                    			newLeafNode(this_INT_2, grammarAccess.getAtomAccess().getINTTerminalRuleCall_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:3292:3: this_ANY_OTHER_3= RULE_ANY_OTHER
                    {
                    this_ANY_OTHER_3=(Token)match(input,RULE_ANY_OTHER,FOLLOW_2); 

                    			current.merge(this_ANY_OTHER_3);
                    		

                    			newLeafNode(this_ANY_OTHER_3, grammarAccess.getAtomAccess().getANY_OTHERTerminalRuleCall_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:3300:3: this_DOLLAR_STRING_4= RULE_DOLLAR_STRING
                    {
                    this_DOLLAR_STRING_4=(Token)match(input,RULE_DOLLAR_STRING,FOLLOW_2); 

                    			current.merge(this_DOLLAR_STRING_4);
                    		

                    			newLeafNode(this_DOLLAR_STRING_4, grammarAccess.getAtomAccess().getDOLLAR_STRINGTerminalRuleCall_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:3308:3: kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getFullStopKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:3314:3: kw= Comma
                    {
                    kw=(Token)match(input,Comma,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getCommaKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalSQLParser.g:3320:3: kw= CREATE
                    {
                    kw=(Token)match(input,CREATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getCREATEKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalSQLParser.g:3326:3: kw= TABLE
                    {
                    kw=(Token)match(input,TABLE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getTABLEKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalSQLParser.g:3332:3: kw= NOT
                    {
                    kw=(Token)match(input,NOT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getNOTKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalSQLParser.g:3338:3: kw= NULL
                    {
                    kw=(Token)match(input,NULL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getNULLKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalSQLParser.g:3344:3: kw= PRIMARY
                    {
                    kw=(Token)match(input,PRIMARY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getPRIMARYKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalSQLParser.g:3350:3: kw= UNIQUE
                    {
                    kw=(Token)match(input,UNIQUE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getUNIQUEKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalSQLParser.g:3356:3: kw= WITH
                    {
                    kw=(Token)match(input,WITH,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getWITHKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalSQLParser.g:3362:3: kw= CONSTRAINT
                    {
                    kw=(Token)match(input,CONSTRAINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getCONSTRAINTKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalSQLParser.g:3368:3: kw= FOREIGN
                    {
                    kw=(Token)match(input,FOREIGN,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getFOREIGNKeyword_15());
                    		

                    }
                    break;
                case 17 :
                    // InternalSQLParser.g:3374:3: kw= REFERENCES
                    {
                    kw=(Token)match(input,REFERENCES,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getREFERENCESKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalSQLParser.g:3380:3: kw= ON
                    {
                    kw=(Token)match(input,ON,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getONKeyword_17());
                    		

                    }
                    break;
                case 19 :
                    // InternalSQLParser.g:3386:3: kw= DEFAULT
                    {
                    kw=(Token)match(input,DEFAULT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getDEFAULTKeyword_18());
                    		

                    }
                    break;
                case 20 :
                    // InternalSQLParser.g:3392:3: kw= FULL
                    {
                    kw=(Token)match(input,FULL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getFULLKeyword_19());
                    		

                    }
                    break;
                case 21 :
                    // InternalSQLParser.g:3398:3: kw= DEFERRABLE
                    {
                    kw=(Token)match(input,DEFERRABLE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getDEFERRABLEKeyword_20());
                    		

                    }
                    break;
                case 22 :
                    // InternalSQLParser.g:3404:3: kw= INITIALLY
                    {
                    kw=(Token)match(input,INITIALLY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getINITIALLYKeyword_21());
                    		

                    }
                    break;
                case 23 :
                    // InternalSQLParser.g:3410:3: kw= CHECK
                    {
                    kw=(Token)match(input,CHECK,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getCHECKKeyword_22());
                    		

                    }
                    break;
                case 24 :
                    // InternalSQLParser.g:3416:3: kw= AS
                    {
                    kw=(Token)match(input,AS,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getASKeyword_23());
                    		

                    }
                    break;
                case 25 :
                    // InternalSQLParser.g:3422:3: kw= ONLY
                    {
                    kw=(Token)match(input,ONLY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getONLYKeyword_24());
                    		

                    }
                    break;
                case 26 :
                    // InternalSQLParser.g:3428:3: kw= USING
                    {
                    kw=(Token)match(input,USING,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getUSINGKeyword_25());
                    		

                    }
                    break;
                case 27 :
                    // InternalSQLParser.g:3434:3: kw= WHERE
                    {
                    kw=(Token)match(input,WHERE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getAtomAccess().getWHEREKeyword_26());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAtom"


    // $ANTLR start "entryRuleRawTokens"
    // InternalSQLParser.g:3443:1: entryRuleRawTokens returns [String current=null] : iv_ruleRawTokens= ruleRawTokens EOF ;
    public final String entryRuleRawTokens() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleRawTokens = null;


        try {
            // InternalSQLParser.g:3443:49: (iv_ruleRawTokens= ruleRawTokens EOF )
            // InternalSQLParser.g:3444:2: iv_ruleRawTokens= ruleRawTokens EOF
            {
             newCompositeNode(grammarAccess.getRawTokensRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRawTokens=ruleRawTokens();

            state._fsp--;

             current =iv_ruleRawTokens.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRawTokens"


    // $ANTLR start "ruleRawTokens"
    // InternalSQLParser.g:3450:1: ruleRawTokens returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_RawToken_0= ruleRawToken )+ ;
    public final AntlrDatatypeRuleToken ruleRawTokens() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        AntlrDatatypeRuleToken this_RawToken_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3456:2: ( (this_RawToken_0= ruleRawToken )+ )
            // InternalSQLParser.g:3457:2: (this_RawToken_0= ruleRawToken )+
            {
            // InternalSQLParser.g:3457:2: (this_RawToken_0= ruleRawToken )+
            int cnt65=0;
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( ((LA65_0>=TIMESTAMPTZ && LA65_0<=FullStop)||(LA65_0>=RULE_ID && LA65_0<=RULE_DOLLAR_STRING)||LA65_0==RULE_ANY_OTHER) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // InternalSQLParser.g:3458:3: this_RawToken_0= ruleRawToken
            	    {

            	    			newCompositeNode(grammarAccess.getRawTokensAccess().getRawTokenParserRuleCall());
            	    		
            	    pushFollow(FOLLOW_69);
            	    this_RawToken_0=ruleRawToken();

            	    state._fsp--;


            	    			current.merge(this_RawToken_0);
            	    		

            	    			afterParserOrEnumRuleCall();
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt65 >= 1 ) break loop65;
                        EarlyExitException eee =
                            new EarlyExitException(65, input);
                        throw eee;
                }
                cnt65++;
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRawTokens"


    // $ANTLR start "entryRuleRawToken"
    // InternalSQLParser.g:3472:1: entryRuleRawToken returns [String current=null] : iv_ruleRawToken= ruleRawToken EOF ;
    public final String entryRuleRawToken() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleRawToken = null;


        try {
            // InternalSQLParser.g:3472:48: (iv_ruleRawToken= ruleRawToken EOF )
            // InternalSQLParser.g:3473:2: iv_ruleRawToken= ruleRawToken EOF
            {
             newCompositeNode(grammarAccess.getRawTokenRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRawToken=ruleRawToken();

            state._fsp--;

             current =iv_ruleRawToken.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRawToken"


    // $ANTLR start "ruleRawToken"
    // InternalSQLParser.g:3479:1: ruleRawToken returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Atom_0= ruleAtom | kw= LeftParenthesis | kw= RightParenthesis ) ;
    public final AntlrDatatypeRuleToken ruleRawToken() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Atom_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3485:2: ( (this_Atom_0= ruleAtom | kw= LeftParenthesis | kw= RightParenthesis ) )
            // InternalSQLParser.g:3486:2: (this_Atom_0= ruleAtom | kw= LeftParenthesis | kw= RightParenthesis )
            {
            // InternalSQLParser.g:3486:2: (this_Atom_0= ruleAtom | kw= LeftParenthesis | kw= RightParenthesis )
            int alt66=3;
            switch ( input.LA(1) ) {
            case TIMESTAMPTZ:
            case CONSTRAINT:
            case DEFERRABLE:
            case REFERENCES:
            case BIGSERIAL:
            case CHARACTER:
            case GENERATED:
            case IMMEDIATE:
            case INITIALLY:
            case PRECISION:
            case TIMESTAMP:
            case DEFERRED:
            case IDENTITY:
            case RESTRICT:
            case SMALLINT:
            case BOOLEAN:
            case CASCADE:
            case DECIMAL:
            case DEFAULT:
            case FOREIGN:
            case INCLUDE:
            case INTEGER:
            case NUMERIC:
            case PRIMARY:
            case VARCHAR:
            case VARYING:
            case WITHOUT:
            case ACTION:
            case ALWAYS:
            case BIGINT:
            case CREATE:
            case DELETE:
            case DOUBLE:
            case EXISTS:
            case SCHEMA:
            case SERIAL:
            case SIMPLE:
            case STORED:
            case UNIQUE:
            case UPDATE:
            case ALTER:
            case BYTEA:
            case CHECK:
            case FLOAT:
            case INDEX:
            case JSONB:
            case MATCH:
            case TABLE:
            case USING:
            case WHERE:
            case CHAR:
            case DATE:
            case FULL:
            case NULL:
            case ONLY:
            case REAL:
            case TEXT:
            case TIME:
            case UUID:
            case WITH:
            case ZONE:
            case ADD:
            case INT:
            case KEY:
            case NOT:
            case SET:
            case AS:
            case BY:
            case IF:
            case NO:
            case ON:
            case Comma:
            case FullStop:
            case RULE_ID:
            case RULE_QUOTED_ID:
            case RULE_STRING:
            case RULE_INT:
            case RULE_DOLLAR_STRING:
            case RULE_ANY_OTHER:
                {
                alt66=1;
                }
                break;
            case LeftParenthesis:
                {
                alt66=2;
                }
                break;
            case RightParenthesis:
                {
                alt66=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }

            switch (alt66) {
                case 1 :
                    // InternalSQLParser.g:3487:3: this_Atom_0= ruleAtom
                    {

                    			newCompositeNode(grammarAccess.getRawTokenAccess().getAtomParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Atom_0=ruleAtom();

                    state._fsp--;


                    			current.merge(this_Atom_0);
                    		

                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3498:3: kw= LeftParenthesis
                    {
                    kw=(Token)match(input,LeftParenthesis,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getRawTokenAccess().getLeftParenthesisKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:3504:3: kw= RightParenthesis
                    {
                    kw=(Token)match(input,RightParenthesis,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getRawTokenAccess().getRightParenthesisKeyword_2());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRawToken"


    // $ANTLR start "entryRuleCreateBody"
    // InternalSQLParser.g:3513:1: entryRuleCreateBody returns [String current=null] : iv_ruleCreateBody= ruleCreateBody EOF ;
    public final String entryRuleCreateBody() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCreateBody = null;


        try {
            // InternalSQLParser.g:3513:50: (iv_ruleCreateBody= ruleCreateBody EOF )
            // InternalSQLParser.g:3514:2: iv_ruleCreateBody= ruleCreateBody EOF
            {
             newCompositeNode(grammarAccess.getCreateBodyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCreateBody=ruleCreateBody();

            state._fsp--;

             current =iv_ruleCreateBody.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCreateBody"


    // $ANTLR start "ruleCreateBody"
    // InternalSQLParser.g:3520:1: ruleCreateBody returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (this_RawToken_1= ruleRawToken )* ) ;
    public final AntlrDatatypeRuleToken ruleCreateBody() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        AntlrDatatypeRuleToken this_RawToken_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3526:2: ( (this_ID_0= RULE_ID (this_RawToken_1= ruleRawToken )* ) )
            // InternalSQLParser.g:3527:2: (this_ID_0= RULE_ID (this_RawToken_1= ruleRawToken )* )
            {
            // InternalSQLParser.g:3527:2: (this_ID_0= RULE_ID (this_RawToken_1= ruleRawToken )* )
            // InternalSQLParser.g:3528:3: this_ID_0= RULE_ID (this_RawToken_1= ruleRawToken )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_69); 

            			current.merge(this_ID_0);
            		

            			newLeafNode(this_ID_0, grammarAccess.getCreateBodyAccess().getIDTerminalRuleCall_0());
            		
            // InternalSQLParser.g:3535:3: (this_RawToken_1= ruleRawToken )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( ((LA67_0>=TIMESTAMPTZ && LA67_0<=FullStop)||(LA67_0>=RULE_ID && LA67_0<=RULE_DOLLAR_STRING)||LA67_0==RULE_ANY_OTHER) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // InternalSQLParser.g:3536:4: this_RawToken_1= ruleRawToken
            	    {

            	    				newCompositeNode(grammarAccess.getCreateBodyAccess().getRawTokenParserRuleCall_1());
            	    			
            	    pushFollow(FOLLOW_69);
            	    this_RawToken_1=ruleRawToken();

            	    state._fsp--;


            	    				current.merge(this_RawToken_1);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCreateBody"


    // $ANTLR start "entryRuleAlterBody"
    // InternalSQLParser.g:3551:1: entryRuleAlterBody returns [String current=null] : iv_ruleAlterBody= ruleAlterBody EOF ;
    public final String entryRuleAlterBody() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAlterBody = null;


        try {
            // InternalSQLParser.g:3551:49: (iv_ruleAlterBody= ruleAlterBody EOF )
            // InternalSQLParser.g:3552:2: iv_ruleAlterBody= ruleAlterBody EOF
            {
             newCompositeNode(grammarAccess.getAlterBodyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlterBody=ruleAlterBody();

            state._fsp--;

             current =iv_ruleAlterBody.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlterBody"


    // $ANTLR start "ruleAlterBody"
    // InternalSQLParser.g:3558:1: ruleAlterBody returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ID_0= RULE_ID | kw= SCHEMA | kw= INDEX | kw= DEFAULT ) (this_RawToken_4= ruleRawToken )* ) ;
    public final AntlrDatatypeRuleToken ruleAlterBody() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        AntlrDatatypeRuleToken this_RawToken_4 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3564:2: ( ( (this_ID_0= RULE_ID | kw= SCHEMA | kw= INDEX | kw= DEFAULT ) (this_RawToken_4= ruleRawToken )* ) )
            // InternalSQLParser.g:3565:2: ( (this_ID_0= RULE_ID | kw= SCHEMA | kw= INDEX | kw= DEFAULT ) (this_RawToken_4= ruleRawToken )* )
            {
            // InternalSQLParser.g:3565:2: ( (this_ID_0= RULE_ID | kw= SCHEMA | kw= INDEX | kw= DEFAULT ) (this_RawToken_4= ruleRawToken )* )
            // InternalSQLParser.g:3566:3: (this_ID_0= RULE_ID | kw= SCHEMA | kw= INDEX | kw= DEFAULT ) (this_RawToken_4= ruleRawToken )*
            {
            // InternalSQLParser.g:3566:3: (this_ID_0= RULE_ID | kw= SCHEMA | kw= INDEX | kw= DEFAULT )
            int alt68=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt68=1;
                }
                break;
            case SCHEMA:
                {
                alt68=2;
                }
                break;
            case INDEX:
                {
                alt68=3;
                }
                break;
            case DEFAULT:
                {
                alt68=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }

            switch (alt68) {
                case 1 :
                    // InternalSQLParser.g:3567:4: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_69); 

                    				current.merge(this_ID_0);
                    			

                    				newLeafNode(this_ID_0, grammarAccess.getAlterBodyAccess().getIDTerminalRuleCall_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3575:4: kw= SCHEMA
                    {
                    kw=(Token)match(input,SCHEMA,FOLLOW_69); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAlterBodyAccess().getSCHEMAKeyword_0_1());
                    			

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:3581:4: kw= INDEX
                    {
                    kw=(Token)match(input,INDEX,FOLLOW_69); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAlterBodyAccess().getINDEXKeyword_0_2());
                    			

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:3587:4: kw= DEFAULT
                    {
                    kw=(Token)match(input,DEFAULT,FOLLOW_69); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAlterBodyAccess().getDEFAULTKeyword_0_3());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:3593:3: (this_RawToken_4= ruleRawToken )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( ((LA69_0>=TIMESTAMPTZ && LA69_0<=FullStop)||(LA69_0>=RULE_ID && LA69_0<=RULE_DOLLAR_STRING)||LA69_0==RULE_ANY_OTHER) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // InternalSQLParser.g:3594:4: this_RawToken_4= ruleRawToken
            	    {

            	    				newCompositeNode(grammarAccess.getAlterBodyAccess().getRawTokenParserRuleCall_1());
            	    			
            	    pushFollow(FOLLOW_69);
            	    this_RawToken_4=ruleRawToken();

            	    state._fsp--;


            	    				current.merge(this_RawToken_4);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;

            	default :
            	    break loop69;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlterBody"


    // $ANTLR start "entryRuleAlterTableBody"
    // InternalSQLParser.g:3609:1: entryRuleAlterTableBody returns [String current=null] : iv_ruleAlterTableBody= ruleAlterTableBody EOF ;
    public final String entryRuleAlterTableBody() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleAlterTableBody = null;


        try {
            // InternalSQLParser.g:3609:54: (iv_ruleAlterTableBody= ruleAlterTableBody EOF )
            // InternalSQLParser.g:3610:2: iv_ruleAlterTableBody= ruleAlterTableBody EOF
            {
             newCompositeNode(grammarAccess.getAlterTableBodyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAlterTableBody=ruleAlterTableBody();

            state._fsp--;

             current =iv_ruleAlterTableBody.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAlterTableBody"


    // $ANTLR start "ruleAlterTableBody"
    // InternalSQLParser.g:3616:1: ruleAlterTableBody returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ID_0= RULE_ID | kw= ALTER | kw= SET ) (this_RawToken_3= ruleRawToken )* ) ;
    public final AntlrDatatypeRuleToken ruleAlterTableBody() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        AntlrDatatypeRuleToken this_RawToken_3 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3622:2: ( ( (this_ID_0= RULE_ID | kw= ALTER | kw= SET ) (this_RawToken_3= ruleRawToken )* ) )
            // InternalSQLParser.g:3623:2: ( (this_ID_0= RULE_ID | kw= ALTER | kw= SET ) (this_RawToken_3= ruleRawToken )* )
            {
            // InternalSQLParser.g:3623:2: ( (this_ID_0= RULE_ID | kw= ALTER | kw= SET ) (this_RawToken_3= ruleRawToken )* )
            // InternalSQLParser.g:3624:3: (this_ID_0= RULE_ID | kw= ALTER | kw= SET ) (this_RawToken_3= ruleRawToken )*
            {
            // InternalSQLParser.g:3624:3: (this_ID_0= RULE_ID | kw= ALTER | kw= SET )
            int alt70=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt70=1;
                }
                break;
            case ALTER:
                {
                alt70=2;
                }
                break;
            case SET:
                {
                alt70=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // InternalSQLParser.g:3625:4: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_69); 

                    				current.merge(this_ID_0);
                    			

                    				newLeafNode(this_ID_0, grammarAccess.getAlterTableBodyAccess().getIDTerminalRuleCall_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3633:4: kw= ALTER
                    {
                    kw=(Token)match(input,ALTER,FOLLOW_69); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAlterTableBodyAccess().getALTERKeyword_0_1());
                    			

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:3639:4: kw= SET
                    {
                    kw=(Token)match(input,SET,FOLLOW_69); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getAlterTableBodyAccess().getSETKeyword_0_2());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:3645:3: (this_RawToken_3= ruleRawToken )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( ((LA71_0>=TIMESTAMPTZ && LA71_0<=FullStop)||(LA71_0>=RULE_ID && LA71_0<=RULE_DOLLAR_STRING)||LA71_0==RULE_ANY_OTHER) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // InternalSQLParser.g:3646:4: this_RawToken_3= ruleRawToken
            	    {

            	    				newCompositeNode(grammarAccess.getAlterTableBodyAccess().getRawTokenParserRuleCall_1());
            	    			
            	    pushFollow(FOLLOW_69);
            	    this_RawToken_3=ruleRawToken();

            	    state._fsp--;


            	    				current.merge(this_RawToken_3);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAlterTableBody"


    // $ANTLR start "entryRuleVerb"
    // InternalSQLParser.g:3661:1: entryRuleVerb returns [String current=null] : iv_ruleVerb= ruleVerb EOF ;
    public final String entryRuleVerb() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleVerb = null;


        try {
            // InternalSQLParser.g:3661:44: (iv_ruleVerb= ruleVerb EOF )
            // InternalSQLParser.g:3662:2: iv_ruleVerb= ruleVerb EOF
            {
             newCompositeNode(grammarAccess.getVerbRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVerb=ruleVerb();

            state._fsp--;

             current =iv_ruleVerb.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVerb"


    // $ANTLR start "ruleVerb"
    // InternalSQLParser.g:3668:1: ruleVerb returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= SET | kw= UPDATE | kw= DELETE | this_ANY_OTHER_4= RULE_ANY_OTHER ) ;
    public final AntlrDatatypeRuleToken ruleVerb() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ANY_OTHER_4=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3674:2: ( (this_ID_0= RULE_ID | kw= SET | kw= UPDATE | kw= DELETE | this_ANY_OTHER_4= RULE_ANY_OTHER ) )
            // InternalSQLParser.g:3675:2: (this_ID_0= RULE_ID | kw= SET | kw= UPDATE | kw= DELETE | this_ANY_OTHER_4= RULE_ANY_OTHER )
            {
            // InternalSQLParser.g:3675:2: (this_ID_0= RULE_ID | kw= SET | kw= UPDATE | kw= DELETE | this_ANY_OTHER_4= RULE_ANY_OTHER )
            int alt72=5;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt72=1;
                }
                break;
            case SET:
                {
                alt72=2;
                }
                break;
            case UPDATE:
                {
                alt72=3;
                }
                break;
            case DELETE:
                {
                alt72=4;
                }
                break;
            case RULE_ANY_OTHER:
                {
                alt72=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // InternalSQLParser.g:3676:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getVerbAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3684:3: kw= SET
                    {
                    kw=(Token)match(input,SET,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVerbAccess().getSETKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:3690:3: kw= UPDATE
                    {
                    kw=(Token)match(input,UPDATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVerbAccess().getUPDATEKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:3696:3: kw= DELETE
                    {
                    kw=(Token)match(input,DELETE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getVerbAccess().getDELETEKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:3702:3: this_ANY_OTHER_4= RULE_ANY_OTHER
                    {
                    this_ANY_OTHER_4=(Token)match(input,RULE_ANY_OTHER,FOLLOW_2); 

                    			current.merge(this_ANY_OTHER_4);
                    		

                    			newLeafNode(this_ANY_OTHER_4, grammarAccess.getVerbAccess().getANY_OTHERTerminalRuleCall_4());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVerb"


    // $ANTLR start "entryRuleIndexOptions"
    // InternalSQLParser.g:3713:1: entryRuleIndexOptions returns [String current=null] : iv_ruleIndexOptions= ruleIndexOptions EOF ;
    public final String entryRuleIndexOptions() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleIndexOptions = null;


        try {
            // InternalSQLParser.g:3713:52: (iv_ruleIndexOptions= ruleIndexOptions EOF )
            // InternalSQLParser.g:3714:2: iv_ruleIndexOptions= ruleIndexOptions EOF
            {
             newCompositeNode(grammarAccess.getIndexOptionsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIndexOptions=ruleIndexOptions();

            state._fsp--;

             current =iv_ruleIndexOptions.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIndexOptions"


    // $ANTLR start "ruleIndexOptions"
    // InternalSQLParser.g:3720:1: ruleIndexOptions returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Name_0= ruleName | this_STRING_1= RULE_STRING | kw= FullStop | this_Parenthesized_3= ruleParenthesized )+ ;
    public final AntlrDatatypeRuleToken ruleIndexOptions() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_1=null;
        Token kw=null;
        AntlrDatatypeRuleToken this_Name_0 = null;

        AntlrDatatypeRuleToken this_Parenthesized_3 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3726:2: ( (this_Name_0= ruleName | this_STRING_1= RULE_STRING | kw= FullStop | this_Parenthesized_3= ruleParenthesized )+ )
            // InternalSQLParser.g:3727:2: (this_Name_0= ruleName | this_STRING_1= RULE_STRING | kw= FullStop | this_Parenthesized_3= ruleParenthesized )+
            {
            // InternalSQLParser.g:3727:2: (this_Name_0= ruleName | this_STRING_1= RULE_STRING | kw= FullStop | this_Parenthesized_3= ruleParenthesized )+
            int cnt73=0;
            loop73:
            do {
                int alt73=5;
                switch ( input.LA(1) ) {
                case TIMESTAMPTZ:
                case BIGSERIAL:
                case CHARACTER:
                case GENERATED:
                case IMMEDIATE:
                case PRECISION:
                case TIMESTAMP:
                case DEFERRED:
                case IDENTITY:
                case RESTRICT:
                case SMALLINT:
                case BOOLEAN:
                case CASCADE:
                case DECIMAL:
                case INCLUDE:
                case INTEGER:
                case NUMERIC:
                case VARCHAR:
                case VARYING:
                case WITHOUT:
                case ACTION:
                case ALWAYS:
                case BIGINT:
                case DELETE:
                case DOUBLE:
                case EXISTS:
                case SCHEMA:
                case SERIAL:
                case SIMPLE:
                case STORED:
                case UPDATE:
                case ALTER:
                case BYTEA:
                case FLOAT:
                case INDEX:
                case JSONB:
                case MATCH:
                case CHAR:
                case DATE:
                case REAL:
                case TEXT:
                case TIME:
                case UUID:
                case ZONE:
                case ADD:
                case INT:
                case KEY:
                case SET:
                case BY:
                case IF:
                case NO:
                case RULE_ID:
                case RULE_QUOTED_ID:
                    {
                    alt73=1;
                    }
                    break;
                case RULE_STRING:
                    {
                    alt73=2;
                    }
                    break;
                case FullStop:
                    {
                    alt73=3;
                    }
                    break;
                case LeftParenthesis:
                    {
                    alt73=4;
                    }
                    break;

                }

                switch (alt73) {
            	case 1 :
            	    // InternalSQLParser.g:3728:3: this_Name_0= ruleName
            	    {

            	    			newCompositeNode(grammarAccess.getIndexOptionsAccess().getNameParserRuleCall_0());
            	    		
            	    pushFollow(FOLLOW_24);
            	    this_Name_0=ruleName();

            	    state._fsp--;


            	    			current.merge(this_Name_0);
            	    		

            	    			afterParserOrEnumRuleCall();
            	    		

            	    }
            	    break;
            	case 2 :
            	    // InternalSQLParser.g:3739:3: this_STRING_1= RULE_STRING
            	    {
            	    this_STRING_1=(Token)match(input,RULE_STRING,FOLLOW_24); 

            	    			current.merge(this_STRING_1);
            	    		

            	    			newLeafNode(this_STRING_1, grammarAccess.getIndexOptionsAccess().getSTRINGTerminalRuleCall_1());
            	    		

            	    }
            	    break;
            	case 3 :
            	    // InternalSQLParser.g:3747:3: kw= FullStop
            	    {
            	    kw=(Token)match(input,FullStop,FOLLOW_24); 

            	    			current.merge(kw);
            	    			newLeafNode(kw, grammarAccess.getIndexOptionsAccess().getFullStopKeyword_2());
            	    		

            	    }
            	    break;
            	case 4 :
            	    // InternalSQLParser.g:3753:3: this_Parenthesized_3= ruleParenthesized
            	    {

            	    			newCompositeNode(grammarAccess.getIndexOptionsAccess().getParenthesizedParserRuleCall_3());
            	    		
            	    pushFollow(FOLLOW_24);
            	    this_Parenthesized_3=ruleParenthesized();

            	    state._fsp--;


            	    			current.merge(this_Parenthesized_3);
            	    		

            	    			afterParserOrEnumRuleCall();
            	    		

            	    }
            	    break;

            	default :
            	    if ( cnt73 >= 1 ) break loop73;
                        EarlyExitException eee =
                            new EarlyExitException(73, input);
                        throw eee;
                }
                cnt73++;
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIndexOptions"


    // $ANTLR start "entryRuleIndexTail"
    // InternalSQLParser.g:3767:1: entryRuleIndexTail returns [String current=null] : iv_ruleIndexTail= ruleIndexTail EOF ;
    public final String entryRuleIndexTail() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleIndexTail = null;


        try {
            // InternalSQLParser.g:3767:49: (iv_ruleIndexTail= ruleIndexTail EOF )
            // InternalSQLParser.g:3768:2: iv_ruleIndexTail= ruleIndexTail EOF
            {
             newCompositeNode(grammarAccess.getIndexTailRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleIndexTail=ruleIndexTail();

            state._fsp--;

             current =iv_ruleIndexTail.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIndexTail"


    // $ANTLR start "ruleIndexTail"
    // InternalSQLParser.g:3774:1: ruleIndexTail returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= INCLUDE this_Parenthesized_1= ruleParenthesized (kw= WITH this_Parenthesized_3= ruleParenthesized )? (kw= WHERE this_Parenthesized_5= ruleParenthesized )? ) | (kw= WITH this_Parenthesized_7= ruleParenthesized (kw= WHERE this_Parenthesized_9= ruleParenthesized )? ) | (kw= WHERE this_Parenthesized_11= ruleParenthesized ) ) ;
    public final AntlrDatatypeRuleToken ruleIndexTail() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Parenthesized_1 = null;

        AntlrDatatypeRuleToken this_Parenthesized_3 = null;

        AntlrDatatypeRuleToken this_Parenthesized_5 = null;

        AntlrDatatypeRuleToken this_Parenthesized_7 = null;

        AntlrDatatypeRuleToken this_Parenthesized_9 = null;

        AntlrDatatypeRuleToken this_Parenthesized_11 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3780:2: ( ( (kw= INCLUDE this_Parenthesized_1= ruleParenthesized (kw= WITH this_Parenthesized_3= ruleParenthesized )? (kw= WHERE this_Parenthesized_5= ruleParenthesized )? ) | (kw= WITH this_Parenthesized_7= ruleParenthesized (kw= WHERE this_Parenthesized_9= ruleParenthesized )? ) | (kw= WHERE this_Parenthesized_11= ruleParenthesized ) ) )
            // InternalSQLParser.g:3781:2: ( (kw= INCLUDE this_Parenthesized_1= ruleParenthesized (kw= WITH this_Parenthesized_3= ruleParenthesized )? (kw= WHERE this_Parenthesized_5= ruleParenthesized )? ) | (kw= WITH this_Parenthesized_7= ruleParenthesized (kw= WHERE this_Parenthesized_9= ruleParenthesized )? ) | (kw= WHERE this_Parenthesized_11= ruleParenthesized ) )
            {
            // InternalSQLParser.g:3781:2: ( (kw= INCLUDE this_Parenthesized_1= ruleParenthesized (kw= WITH this_Parenthesized_3= ruleParenthesized )? (kw= WHERE this_Parenthesized_5= ruleParenthesized )? ) | (kw= WITH this_Parenthesized_7= ruleParenthesized (kw= WHERE this_Parenthesized_9= ruleParenthesized )? ) | (kw= WHERE this_Parenthesized_11= ruleParenthesized ) )
            int alt77=3;
            switch ( input.LA(1) ) {
            case INCLUDE:
                {
                alt77=1;
                }
                break;
            case WITH:
                {
                alt77=2;
                }
                break;
            case WHERE:
                {
                alt77=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }

            switch (alt77) {
                case 1 :
                    // InternalSQLParser.g:3782:3: (kw= INCLUDE this_Parenthesized_1= ruleParenthesized (kw= WITH this_Parenthesized_3= ruleParenthesized )? (kw= WHERE this_Parenthesized_5= ruleParenthesized )? )
                    {
                    // InternalSQLParser.g:3782:3: (kw= INCLUDE this_Parenthesized_1= ruleParenthesized (kw= WITH this_Parenthesized_3= ruleParenthesized )? (kw= WHERE this_Parenthesized_5= ruleParenthesized )? )
                    // InternalSQLParser.g:3783:4: kw= INCLUDE this_Parenthesized_1= ruleParenthesized (kw= WITH this_Parenthesized_3= ruleParenthesized )? (kw= WHERE this_Parenthesized_5= ruleParenthesized )?
                    {
                    kw=(Token)match(input,INCLUDE,FOLLOW_9); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getIndexTailAccess().getINCLUDEKeyword_0_0());
                    			

                    				newCompositeNode(grammarAccess.getIndexTailAccess().getParenthesizedParserRuleCall_0_1());
                    			
                    pushFollow(FOLLOW_70);
                    this_Parenthesized_1=ruleParenthesized();

                    state._fsp--;


                    				current.merge(this_Parenthesized_1);
                    			

                    				afterParserOrEnumRuleCall();
                    			
                    // InternalSQLParser.g:3798:4: (kw= WITH this_Parenthesized_3= ruleParenthesized )?
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==WITH) ) {
                        alt74=1;
                    }
                    switch (alt74) {
                        case 1 :
                            // InternalSQLParser.g:3799:5: kw= WITH this_Parenthesized_3= ruleParenthesized
                            {
                            kw=(Token)match(input,WITH,FOLLOW_9); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getIndexTailAccess().getWITHKeyword_0_2_0());
                            				

                            					newCompositeNode(grammarAccess.getIndexTailAccess().getParenthesizedParserRuleCall_0_2_1());
                            				
                            pushFollow(FOLLOW_71);
                            this_Parenthesized_3=ruleParenthesized();

                            state._fsp--;


                            					current.merge(this_Parenthesized_3);
                            				

                            					afterParserOrEnumRuleCall();
                            				

                            }
                            break;

                    }

                    // InternalSQLParser.g:3815:4: (kw= WHERE this_Parenthesized_5= ruleParenthesized )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==WHERE) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // InternalSQLParser.g:3816:5: kw= WHERE this_Parenthesized_5= ruleParenthesized
                            {
                            kw=(Token)match(input,WHERE,FOLLOW_9); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getIndexTailAccess().getWHEREKeyword_0_3_0());
                            				

                            					newCompositeNode(grammarAccess.getIndexTailAccess().getParenthesizedParserRuleCall_0_3_1());
                            				
                            pushFollow(FOLLOW_2);
                            this_Parenthesized_5=ruleParenthesized();

                            state._fsp--;


                            					current.merge(this_Parenthesized_5);
                            				

                            					afterParserOrEnumRuleCall();
                            				

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3834:3: (kw= WITH this_Parenthesized_7= ruleParenthesized (kw= WHERE this_Parenthesized_9= ruleParenthesized )? )
                    {
                    // InternalSQLParser.g:3834:3: (kw= WITH this_Parenthesized_7= ruleParenthesized (kw= WHERE this_Parenthesized_9= ruleParenthesized )? )
                    // InternalSQLParser.g:3835:4: kw= WITH this_Parenthesized_7= ruleParenthesized (kw= WHERE this_Parenthesized_9= ruleParenthesized )?
                    {
                    kw=(Token)match(input,WITH,FOLLOW_9); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getIndexTailAccess().getWITHKeyword_1_0());
                    			

                    				newCompositeNode(grammarAccess.getIndexTailAccess().getParenthesizedParserRuleCall_1_1());
                    			
                    pushFollow(FOLLOW_71);
                    this_Parenthesized_7=ruleParenthesized();

                    state._fsp--;


                    				current.merge(this_Parenthesized_7);
                    			

                    				afterParserOrEnumRuleCall();
                    			
                    // InternalSQLParser.g:3850:4: (kw= WHERE this_Parenthesized_9= ruleParenthesized )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==WHERE) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // InternalSQLParser.g:3851:5: kw= WHERE this_Parenthesized_9= ruleParenthesized
                            {
                            kw=(Token)match(input,WHERE,FOLLOW_9); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getIndexTailAccess().getWHEREKeyword_1_2_0());
                            				

                            					newCompositeNode(grammarAccess.getIndexTailAccess().getParenthesizedParserRuleCall_1_2_1());
                            				
                            pushFollow(FOLLOW_2);
                            this_Parenthesized_9=ruleParenthesized();

                            state._fsp--;


                            					current.merge(this_Parenthesized_9);
                            				

                            					afterParserOrEnumRuleCall();
                            				

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:3869:3: (kw= WHERE this_Parenthesized_11= ruleParenthesized )
                    {
                    // InternalSQLParser.g:3869:3: (kw= WHERE this_Parenthesized_11= ruleParenthesized )
                    // InternalSQLParser.g:3870:4: kw= WHERE this_Parenthesized_11= ruleParenthesized
                    {
                    kw=(Token)match(input,WHERE,FOLLOW_9); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getIndexTailAccess().getWHEREKeyword_2_0());
                    			

                    				newCompositeNode(grammarAccess.getIndexTailAccess().getParenthesizedParserRuleCall_2_1());
                    			
                    pushFollow(FOLLOW_2);
                    this_Parenthesized_11=ruleParenthesized();

                    state._fsp--;


                    				current.merge(this_Parenthesized_11);
                    			

                    				afterParserOrEnumRuleCall();
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIndexTail"


    // $ANTLR start "entryRuleName"
    // InternalSQLParser.g:3890:1: entryRuleName returns [String current=null] : iv_ruleName= ruleName EOF ;
    public final String entryRuleName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleName = null;


        try {
            // InternalSQLParser.g:3890:44: (iv_ruleName= ruleName EOF )
            // InternalSQLParser.g:3891:2: iv_ruleName= ruleName EOF
            {
             newCompositeNode(grammarAccess.getNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleName=ruleName();

            state._fsp--;

             current =iv_ruleName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleName"


    // $ANTLR start "ruleName"
    // InternalSQLParser.g:3897:1: ruleName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ExprName_0= ruleExprName | kw= GENERATED ) ;
    public final AntlrDatatypeRuleToken ruleName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ExprName_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:3903:2: ( (this_ExprName_0= ruleExprName | kw= GENERATED ) )
            // InternalSQLParser.g:3904:2: (this_ExprName_0= ruleExprName | kw= GENERATED )
            {
            // InternalSQLParser.g:3904:2: (this_ExprName_0= ruleExprName | kw= GENERATED )
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==TIMESTAMPTZ||(LA78_0>=BIGSERIAL && LA78_0<=CHARACTER)||LA78_0==IMMEDIATE||(LA78_0>=PRECISION && LA78_0<=DECIMAL)||(LA78_0>=INCLUDE && LA78_0<=NUMERIC)||(LA78_0>=VARCHAR && LA78_0<=BIGINT)||(LA78_0>=DELETE && LA78_0<=STORED)||(LA78_0>=UPDATE && LA78_0<=BYTEA)||(LA78_0>=FLOAT && LA78_0<=MATCH)||(LA78_0>=CHAR && LA78_0<=DATE)||(LA78_0>=REAL && LA78_0<=UUID)||(LA78_0>=ZONE && LA78_0<=KEY)||LA78_0==SET||(LA78_0>=BY && LA78_0<=NO)||(LA78_0>=RULE_ID && LA78_0<=RULE_QUOTED_ID)) ) {
                alt78=1;
            }
            else if ( (LA78_0==GENERATED) ) {
                alt78=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }
            switch (alt78) {
                case 1 :
                    // InternalSQLParser.g:3905:3: this_ExprName_0= ruleExprName
                    {

                    			newCompositeNode(grammarAccess.getNameAccess().getExprNameParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExprName_0=ruleExprName();

                    state._fsp--;


                    			current.merge(this_ExprName_0);
                    		

                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3916:3: kw= GENERATED
                    {
                    kw=(Token)match(input,GENERATED,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getGENERATEDKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleName"


    // $ANTLR start "entryRuleExprName"
    // InternalSQLParser.g:3925:1: entryRuleExprName returns [String current=null] : iv_ruleExprName= ruleExprName EOF ;
    public final String entryRuleExprName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleExprName = null;


        try {
            // InternalSQLParser.g:3925:48: (iv_ruleExprName= ruleExprName EOF )
            // InternalSQLParser.g:3926:2: iv_ruleExprName= ruleExprName EOF
            {
             newCompositeNode(grammarAccess.getExprNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExprName=ruleExprName();

            state._fsp--;

             current =iv_ruleExprName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExprName"


    // $ANTLR start "ruleExprName"
    // InternalSQLParser.g:3932:1: ruleExprName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA | kw= DELETE | kw= UPDATE | kw= CASCADE | kw= RESTRICT | kw= NO | kw= ACTION | kw= SET | kw= MATCH | kw= SIMPLE | kw= DEFERRED | kw= IMMEDIATE | kw= ALWAYS | kw= BY | kw= IDENTITY | kw= STORED | kw= ALTER | kw= ADD | kw= INDEX | kw= SCHEMA | kw= INCLUDE ) ;
    public final AntlrDatatypeRuleToken ruleExprName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token this_QUOTED_ID_1=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3938:2: ( (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA | kw= DELETE | kw= UPDATE | kw= CASCADE | kw= RESTRICT | kw= NO | kw= ACTION | kw= SET | kw= MATCH | kw= SIMPLE | kw= DEFERRED | kw= IMMEDIATE | kw= ALWAYS | kw= BY | kw= IDENTITY | kw= STORED | kw= ALTER | kw= ADD | kw= INDEX | kw= SCHEMA | kw= INCLUDE ) )
            // InternalSQLParser.g:3939:2: (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA | kw= DELETE | kw= UPDATE | kw= CASCADE | kw= RESTRICT | kw= NO | kw= ACTION | kw= SET | kw= MATCH | kw= SIMPLE | kw= DEFERRED | kw= IMMEDIATE | kw= ALWAYS | kw= BY | kw= IDENTITY | kw= STORED | kw= ALTER | kw= ADD | kw= INDEX | kw= SCHEMA | kw= INCLUDE )
            {
            // InternalSQLParser.g:3939:2: (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA | kw= DELETE | kw= UPDATE | kw= CASCADE | kw= RESTRICT | kw= NO | kw= ACTION | kw= SET | kw= MATCH | kw= SIMPLE | kw= DEFERRED | kw= IMMEDIATE | kw= ALWAYS | kw= BY | kw= IDENTITY | kw= STORED | kw= ALTER | kw= ADD | kw= INDEX | kw= SCHEMA | kw= INCLUDE )
            int alt79=52;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt79=1;
                }
                break;
            case RULE_QUOTED_ID:
                {
                alt79=2;
                }
                break;
            case KEY:
                {
                alt79=3;
                }
                break;
            case TEXT:
                {
                alt79=4;
                }
                break;
            case IF:
                {
                alt79=5;
                }
                break;
            case EXISTS:
                {
                alt79=6;
                }
                break;
            case INT:
                {
                alt79=7;
                }
                break;
            case BIGINT:
                {
                alt79=8;
                }
                break;
            case DECIMAL:
                {
                alt79=9;
                }
                break;
            case TIMESTAMP:
                {
                alt79=10;
                }
                break;
            case BOOLEAN:
                {
                alt79=11;
                }
                break;
            case VARCHAR:
                {
                alt79=12;
                }
                break;
            case INTEGER:
                {
                alt79=13;
                }
                break;
            case SMALLINT:
                {
                alt79=14;
                }
                break;
            case NUMERIC:
                {
                alt79=15;
                }
                break;
            case REAL:
                {
                alt79=16;
                }
                break;
            case FLOAT:
                {
                alt79=17;
                }
                break;
            case PRECISION:
                {
                alt79=18;
                }
                break;
            case CHAR:
                {
                alt79=19;
                }
                break;
            case CHARACTER:
                {
                alt79=20;
                }
                break;
            case TIME:
                {
                alt79=21;
                }
                break;
            case DOUBLE:
                {
                alt79=22;
                }
                break;
            case VARYING:
                {
                alt79=23;
                }
                break;
            case WITHOUT:
                {
                alt79=24;
                }
                break;
            case ZONE:
                {
                alt79=25;
                }
                break;
            case UUID:
                {
                alt79=26;
                }
                break;
            case DATE:
                {
                alt79=27;
                }
                break;
            case SERIAL:
                {
                alt79=28;
                }
                break;
            case BIGSERIAL:
                {
                alt79=29;
                }
                break;
            case TIMESTAMPTZ:
                {
                alt79=30;
                }
                break;
            case JSONB:
                {
                alt79=31;
                }
                break;
            case BYTEA:
                {
                alt79=32;
                }
                break;
            case DELETE:
                {
                alt79=33;
                }
                break;
            case UPDATE:
                {
                alt79=34;
                }
                break;
            case CASCADE:
                {
                alt79=35;
                }
                break;
            case RESTRICT:
                {
                alt79=36;
                }
                break;
            case NO:
                {
                alt79=37;
                }
                break;
            case ACTION:
                {
                alt79=38;
                }
                break;
            case SET:
                {
                alt79=39;
                }
                break;
            case MATCH:
                {
                alt79=40;
                }
                break;
            case SIMPLE:
                {
                alt79=41;
                }
                break;
            case DEFERRED:
                {
                alt79=42;
                }
                break;
            case IMMEDIATE:
                {
                alt79=43;
                }
                break;
            case ALWAYS:
                {
                alt79=44;
                }
                break;
            case BY:
                {
                alt79=45;
                }
                break;
            case IDENTITY:
                {
                alt79=46;
                }
                break;
            case STORED:
                {
                alt79=47;
                }
                break;
            case ALTER:
                {
                alt79=48;
                }
                break;
            case ADD:
                {
                alt79=49;
                }
                break;
            case INDEX:
                {
                alt79=50;
                }
                break;
            case SCHEMA:
                {
                alt79=51;
                }
                break;
            case INCLUDE:
                {
                alt79=52;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }

            switch (alt79) {
                case 1 :
                    // InternalSQLParser.g:3940:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getExprNameAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3948:3: this_QUOTED_ID_1= RULE_QUOTED_ID
                    {
                    this_QUOTED_ID_1=(Token)match(input,RULE_QUOTED_ID,FOLLOW_2); 

                    			current.merge(this_QUOTED_ID_1);
                    		

                    			newLeafNode(this_QUOTED_ID_1, grammarAccess.getExprNameAccess().getQUOTED_IDTerminalRuleCall_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:3956:3: kw= KEY
                    {
                    kw=(Token)match(input,KEY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getKEYKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:3962:3: kw= TEXT
                    {
                    kw=(Token)match(input,TEXT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getTEXTKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:3968:3: kw= IF
                    {
                    kw=(Token)match(input,IF,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getIFKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:3974:3: kw= EXISTS
                    {
                    kw=(Token)match(input,EXISTS,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getEXISTSKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:3980:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getINTKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalSQLParser.g:3986:3: kw= BIGINT
                    {
                    kw=(Token)match(input,BIGINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getBIGINTKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalSQLParser.g:3992:3: kw= DECIMAL
                    {
                    kw=(Token)match(input,DECIMAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getDECIMALKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalSQLParser.g:3998:3: kw= TIMESTAMP
                    {
                    kw=(Token)match(input,TIMESTAMP,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getTIMESTAMPKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalSQLParser.g:4004:3: kw= BOOLEAN
                    {
                    kw=(Token)match(input,BOOLEAN,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getBOOLEANKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalSQLParser.g:4010:3: kw= VARCHAR
                    {
                    kw=(Token)match(input,VARCHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getVARCHARKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalSQLParser.g:4016:3: kw= INTEGER
                    {
                    kw=(Token)match(input,INTEGER,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getINTEGERKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalSQLParser.g:4022:3: kw= SMALLINT
                    {
                    kw=(Token)match(input,SMALLINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSMALLINTKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalSQLParser.g:4028:3: kw= NUMERIC
                    {
                    kw=(Token)match(input,NUMERIC,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getNUMERICKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalSQLParser.g:4034:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getREALKeyword_15());
                    		

                    }
                    break;
                case 17 :
                    // InternalSQLParser.g:4040:3: kw= FLOAT
                    {
                    kw=(Token)match(input,FLOAT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getFLOATKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalSQLParser.g:4046:3: kw= PRECISION
                    {
                    kw=(Token)match(input,PRECISION,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getPRECISIONKeyword_17());
                    		

                    }
                    break;
                case 19 :
                    // InternalSQLParser.g:4052:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getCHARKeyword_18());
                    		

                    }
                    break;
                case 20 :
                    // InternalSQLParser.g:4058:3: kw= CHARACTER
                    {
                    kw=(Token)match(input,CHARACTER,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getCHARACTERKeyword_19());
                    		

                    }
                    break;
                case 21 :
                    // InternalSQLParser.g:4064:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getTIMEKeyword_20());
                    		

                    }
                    break;
                case 22 :
                    // InternalSQLParser.g:4070:3: kw= DOUBLE
                    {
                    kw=(Token)match(input,DOUBLE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getDOUBLEKeyword_21());
                    		

                    }
                    break;
                case 23 :
                    // InternalSQLParser.g:4076:3: kw= VARYING
                    {
                    kw=(Token)match(input,VARYING,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getVARYINGKeyword_22());
                    		

                    }
                    break;
                case 24 :
                    // InternalSQLParser.g:4082:3: kw= WITHOUT
                    {
                    kw=(Token)match(input,WITHOUT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getWITHOUTKeyword_23());
                    		

                    }
                    break;
                case 25 :
                    // InternalSQLParser.g:4088:3: kw= ZONE
                    {
                    kw=(Token)match(input,ZONE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getZONEKeyword_24());
                    		

                    }
                    break;
                case 26 :
                    // InternalSQLParser.g:4094:3: kw= UUID
                    {
                    kw=(Token)match(input,UUID,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getUUIDKeyword_25());
                    		

                    }
                    break;
                case 27 :
                    // InternalSQLParser.g:4100:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getDATEKeyword_26());
                    		

                    }
                    break;
                case 28 :
                    // InternalSQLParser.g:4106:3: kw= SERIAL
                    {
                    kw=(Token)match(input,SERIAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSERIALKeyword_27());
                    		

                    }
                    break;
                case 29 :
                    // InternalSQLParser.g:4112:3: kw= BIGSERIAL
                    {
                    kw=(Token)match(input,BIGSERIAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getBIGSERIALKeyword_28());
                    		

                    }
                    break;
                case 30 :
                    // InternalSQLParser.g:4118:3: kw= TIMESTAMPTZ
                    {
                    kw=(Token)match(input,TIMESTAMPTZ,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getTIMESTAMPTZKeyword_29());
                    		

                    }
                    break;
                case 31 :
                    // InternalSQLParser.g:4124:3: kw= JSONB
                    {
                    kw=(Token)match(input,JSONB,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getJSONBKeyword_30());
                    		

                    }
                    break;
                case 32 :
                    // InternalSQLParser.g:4130:3: kw= BYTEA
                    {
                    kw=(Token)match(input,BYTEA,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getBYTEAKeyword_31());
                    		

                    }
                    break;
                case 33 :
                    // InternalSQLParser.g:4136:3: kw= DELETE
                    {
                    kw=(Token)match(input,DELETE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getDELETEKeyword_32());
                    		

                    }
                    break;
                case 34 :
                    // InternalSQLParser.g:4142:3: kw= UPDATE
                    {
                    kw=(Token)match(input,UPDATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getUPDATEKeyword_33());
                    		

                    }
                    break;
                case 35 :
                    // InternalSQLParser.g:4148:3: kw= CASCADE
                    {
                    kw=(Token)match(input,CASCADE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getCASCADEKeyword_34());
                    		

                    }
                    break;
                case 36 :
                    // InternalSQLParser.g:4154:3: kw= RESTRICT
                    {
                    kw=(Token)match(input,RESTRICT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getRESTRICTKeyword_35());
                    		

                    }
                    break;
                case 37 :
                    // InternalSQLParser.g:4160:3: kw= NO
                    {
                    kw=(Token)match(input,NO,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getNOKeyword_36());
                    		

                    }
                    break;
                case 38 :
                    // InternalSQLParser.g:4166:3: kw= ACTION
                    {
                    kw=(Token)match(input,ACTION,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getACTIONKeyword_37());
                    		

                    }
                    break;
                case 39 :
                    // InternalSQLParser.g:4172:3: kw= SET
                    {
                    kw=(Token)match(input,SET,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSETKeyword_38());
                    		

                    }
                    break;
                case 40 :
                    // InternalSQLParser.g:4178:3: kw= MATCH
                    {
                    kw=(Token)match(input,MATCH,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getMATCHKeyword_39());
                    		

                    }
                    break;
                case 41 :
                    // InternalSQLParser.g:4184:3: kw= SIMPLE
                    {
                    kw=(Token)match(input,SIMPLE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSIMPLEKeyword_40());
                    		

                    }
                    break;
                case 42 :
                    // InternalSQLParser.g:4190:3: kw= DEFERRED
                    {
                    kw=(Token)match(input,DEFERRED,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getDEFERREDKeyword_41());
                    		

                    }
                    break;
                case 43 :
                    // InternalSQLParser.g:4196:3: kw= IMMEDIATE
                    {
                    kw=(Token)match(input,IMMEDIATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getIMMEDIATEKeyword_42());
                    		

                    }
                    break;
                case 44 :
                    // InternalSQLParser.g:4202:3: kw= ALWAYS
                    {
                    kw=(Token)match(input,ALWAYS,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getALWAYSKeyword_43());
                    		

                    }
                    break;
                case 45 :
                    // InternalSQLParser.g:4208:3: kw= BY
                    {
                    kw=(Token)match(input,BY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getBYKeyword_44());
                    		

                    }
                    break;
                case 46 :
                    // InternalSQLParser.g:4214:3: kw= IDENTITY
                    {
                    kw=(Token)match(input,IDENTITY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getIDENTITYKeyword_45());
                    		

                    }
                    break;
                case 47 :
                    // InternalSQLParser.g:4220:3: kw= STORED
                    {
                    kw=(Token)match(input,STORED,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSTOREDKeyword_46());
                    		

                    }
                    break;
                case 48 :
                    // InternalSQLParser.g:4226:3: kw= ALTER
                    {
                    kw=(Token)match(input,ALTER,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getALTERKeyword_47());
                    		

                    }
                    break;
                case 49 :
                    // InternalSQLParser.g:4232:3: kw= ADD
                    {
                    kw=(Token)match(input,ADD,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getADDKeyword_48());
                    		

                    }
                    break;
                case 50 :
                    // InternalSQLParser.g:4238:3: kw= INDEX
                    {
                    kw=(Token)match(input,INDEX,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getINDEXKeyword_49());
                    		

                    }
                    break;
                case 51 :
                    // InternalSQLParser.g:4244:3: kw= SCHEMA
                    {
                    kw=(Token)match(input,SCHEMA,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSCHEMAKeyword_50());
                    		

                    }
                    break;
                case 52 :
                    // InternalSQLParser.g:4250:3: kw= INCLUDE
                    {
                    kw=(Token)match(input,INCLUDE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getINCLUDEKeyword_51());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExprName"


    // $ANTLR start "ruleAllTypes"
    // InternalSQLParser.g:4259:1: ruleAllTypes returns [Enumerator current=null] : ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) ) ;
    public final Enumerator ruleAllTypes() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;
        Token enumLiteral_8=null;
        Token enumLiteral_9=null;
        Token enumLiteral_10=null;
        Token enumLiteral_11=null;
        Token enumLiteral_12=null;
        Token enumLiteral_13=null;
        Token enumLiteral_14=null;
        Token enumLiteral_15=null;
        Token enumLiteral_16=null;
        Token enumLiteral_17=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4265:2: ( ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) ) )
            // InternalSQLParser.g:4266:2: ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) )
            {
            // InternalSQLParser.g:4266:2: ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) )
            int alt80=18;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt80=1;
                }
                break;
            case BIGINT:
                {
                alt80=2;
                }
                break;
            case UUID:
                {
                alt80=3;
                }
                break;
            case DECIMAL:
                {
                alt80=4;
                }
                break;
            case TIMESTAMP:
                {
                alt80=5;
                }
                break;
            case DATE:
                {
                alt80=6;
                }
                break;
            case BOOLEAN:
                {
                alt80=7;
                }
                break;
            case TEXT:
                {
                alt80=8;
                }
                break;
            case INTEGER:
                {
                alt80=9;
                }
                break;
            case SMALLINT:
                {
                alt80=10;
                }
                break;
            case SERIAL:
                {
                alt80=11;
                }
                break;
            case BIGSERIAL:
                {
                alt80=12;
                }
                break;
            case NUMERIC:
                {
                alt80=13;
                }
                break;
            case REAL:
                {
                alt80=14;
                }
                break;
            case FLOAT:
                {
                alt80=15;
                }
                break;
            case TIMESTAMPTZ:
                {
                alt80=16;
                }
                break;
            case JSONB:
                {
                alt80=17;
                }
                break;
            case BYTEA:
                {
                alt80=18;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }

            switch (alt80) {
                case 1 :
                    // InternalSQLParser.g:4267:3: (enumLiteral_0= INT )
                    {
                    // InternalSQLParser.g:4267:3: (enumLiteral_0= INT )
                    // InternalSQLParser.g:4268:4: enumLiteral_0= INT
                    {
                    enumLiteral_0=(Token)match(input,INT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:4275:3: (enumLiteral_1= BIGINT )
                    {
                    // InternalSQLParser.g:4275:3: (enumLiteral_1= BIGINT )
                    // InternalSQLParser.g:4276:4: enumLiteral_1= BIGINT
                    {
                    enumLiteral_1=(Token)match(input,BIGINT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:4283:3: (enumLiteral_2= UUID )
                    {
                    // InternalSQLParser.g:4283:3: (enumLiteral_2= UUID )
                    // InternalSQLParser.g:4284:4: enumLiteral_2= UUID
                    {
                    enumLiteral_2=(Token)match(input,UUID,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:4291:3: (enumLiteral_3= DECIMAL )
                    {
                    // InternalSQLParser.g:4291:3: (enumLiteral_3= DECIMAL )
                    // InternalSQLParser.g:4292:4: enumLiteral_3= DECIMAL
                    {
                    enumLiteral_3=(Token)match(input,DECIMAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:4299:3: (enumLiteral_4= TIMESTAMP )
                    {
                    // InternalSQLParser.g:4299:3: (enumLiteral_4= TIMESTAMP )
                    // InternalSQLParser.g:4300:4: enumLiteral_4= TIMESTAMP
                    {
                    enumLiteral_4=(Token)match(input,TIMESTAMP,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:4307:3: (enumLiteral_5= DATE )
                    {
                    // InternalSQLParser.g:4307:3: (enumLiteral_5= DATE )
                    // InternalSQLParser.g:4308:4: enumLiteral_5= DATE
                    {
                    enumLiteral_5=(Token)match(input,DATE,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:4315:3: (enumLiteral_6= BOOLEAN )
                    {
                    // InternalSQLParser.g:4315:3: (enumLiteral_6= BOOLEAN )
                    // InternalSQLParser.g:4316:4: enumLiteral_6= BOOLEAN
                    {
                    enumLiteral_6=(Token)match(input,BOOLEAN,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalSQLParser.g:4323:3: (enumLiteral_7= TEXT )
                    {
                    // InternalSQLParser.g:4323:3: (enumLiteral_7= TEXT )
                    // InternalSQLParser.g:4324:4: enumLiteral_7= TEXT
                    {
                    enumLiteral_7=(Token)match(input,TEXT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTEXTEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getAllTypesAccess().getTEXTEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;
                case 9 :
                    // InternalSQLParser.g:4331:3: (enumLiteral_8= INTEGER )
                    {
                    // InternalSQLParser.g:4331:3: (enumLiteral_8= INTEGER )
                    // InternalSQLParser.g:4332:4: enumLiteral_8= INTEGER
                    {
                    enumLiteral_8=(Token)match(input,INTEGER,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getINTEGEREnumLiteralDeclaration_8().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_8, grammarAccess.getAllTypesAccess().getINTEGEREnumLiteralDeclaration_8());
                    			

                    }


                    }
                    break;
                case 10 :
                    // InternalSQLParser.g:4339:3: (enumLiteral_9= SMALLINT )
                    {
                    // InternalSQLParser.g:4339:3: (enumLiteral_9= SMALLINT )
                    // InternalSQLParser.g:4340:4: enumLiteral_9= SMALLINT
                    {
                    enumLiteral_9=(Token)match(input,SMALLINT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getSMALLINTEnumLiteralDeclaration_9().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_9, grammarAccess.getAllTypesAccess().getSMALLINTEnumLiteralDeclaration_9());
                    			

                    }


                    }
                    break;
                case 11 :
                    // InternalSQLParser.g:4347:3: (enumLiteral_10= SERIAL )
                    {
                    // InternalSQLParser.g:4347:3: (enumLiteral_10= SERIAL )
                    // InternalSQLParser.g:4348:4: enumLiteral_10= SERIAL
                    {
                    enumLiteral_10=(Token)match(input,SERIAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getSERIALEnumLiteralDeclaration_10().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_10, grammarAccess.getAllTypesAccess().getSERIALEnumLiteralDeclaration_10());
                    			

                    }


                    }
                    break;
                case 12 :
                    // InternalSQLParser.g:4355:3: (enumLiteral_11= BIGSERIAL )
                    {
                    // InternalSQLParser.g:4355:3: (enumLiteral_11= BIGSERIAL )
                    // InternalSQLParser.g:4356:4: enumLiteral_11= BIGSERIAL
                    {
                    enumLiteral_11=(Token)match(input,BIGSERIAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBIGSERIALEnumLiteralDeclaration_11().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_11, grammarAccess.getAllTypesAccess().getBIGSERIALEnumLiteralDeclaration_11());
                    			

                    }


                    }
                    break;
                case 13 :
                    // InternalSQLParser.g:4363:3: (enumLiteral_12= NUMERIC )
                    {
                    // InternalSQLParser.g:4363:3: (enumLiteral_12= NUMERIC )
                    // InternalSQLParser.g:4364:4: enumLiteral_12= NUMERIC
                    {
                    enumLiteral_12=(Token)match(input,NUMERIC,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getNUMERICEnumLiteralDeclaration_12().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_12, grammarAccess.getAllTypesAccess().getNUMERICEnumLiteralDeclaration_12());
                    			

                    }


                    }
                    break;
                case 14 :
                    // InternalSQLParser.g:4371:3: (enumLiteral_13= REAL )
                    {
                    // InternalSQLParser.g:4371:3: (enumLiteral_13= REAL )
                    // InternalSQLParser.g:4372:4: enumLiteral_13= REAL
                    {
                    enumLiteral_13=(Token)match(input,REAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getREALEnumLiteralDeclaration_13().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_13, grammarAccess.getAllTypesAccess().getREALEnumLiteralDeclaration_13());
                    			

                    }


                    }
                    break;
                case 15 :
                    // InternalSQLParser.g:4379:3: (enumLiteral_14= FLOAT )
                    {
                    // InternalSQLParser.g:4379:3: (enumLiteral_14= FLOAT )
                    // InternalSQLParser.g:4380:4: enumLiteral_14= FLOAT
                    {
                    enumLiteral_14=(Token)match(input,FLOAT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getFLOATEnumLiteralDeclaration_14().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_14, grammarAccess.getAllTypesAccess().getFLOATEnumLiteralDeclaration_14());
                    			

                    }


                    }
                    break;
                case 16 :
                    // InternalSQLParser.g:4387:3: (enumLiteral_15= TIMESTAMPTZ )
                    {
                    // InternalSQLParser.g:4387:3: (enumLiteral_15= TIMESTAMPTZ )
                    // InternalSQLParser.g:4388:4: enumLiteral_15= TIMESTAMPTZ
                    {
                    enumLiteral_15=(Token)match(input,TIMESTAMPTZ,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTIMESTAMPTZEnumLiteralDeclaration_15().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_15, grammarAccess.getAllTypesAccess().getTIMESTAMPTZEnumLiteralDeclaration_15());
                    			

                    }


                    }
                    break;
                case 17 :
                    // InternalSQLParser.g:4395:3: (enumLiteral_16= JSONB )
                    {
                    // InternalSQLParser.g:4395:3: (enumLiteral_16= JSONB )
                    // InternalSQLParser.g:4396:4: enumLiteral_16= JSONB
                    {
                    enumLiteral_16=(Token)match(input,JSONB,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getJSONBEnumLiteralDeclaration_16().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_16, grammarAccess.getAllTypesAccess().getJSONBEnumLiteralDeclaration_16());
                    			

                    }


                    }
                    break;
                case 18 :
                    // InternalSQLParser.g:4403:3: (enumLiteral_17= BYTEA )
                    {
                    // InternalSQLParser.g:4403:3: (enumLiteral_17= BYTEA )
                    // InternalSQLParser.g:4404:4: enumLiteral_17= BYTEA
                    {
                    enumLiteral_17=(Token)match(input,BYTEA,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBYTEAEnumLiteralDeclaration_17().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_17, grammarAccess.getAllTypesAccess().getBYTEAEnumLiteralDeclaration_17());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAllTypes"


    // $ANTLR start "ruleDoublePrecision"
    // InternalSQLParser.g:4414:1: ruleDoublePrecision returns [Enumerator current=null] : (enumLiteral_0= PRECISION ) ;
    public final Enumerator ruleDoublePrecision() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4420:2: ( (enumLiteral_0= PRECISION ) )
            // InternalSQLParser.g:4421:2: (enumLiteral_0= PRECISION )
            {
            // InternalSQLParser.g:4421:2: (enumLiteral_0= PRECISION )
            // InternalSQLParser.g:4422:3: enumLiteral_0= PRECISION
            {
            enumLiteral_0=(Token)match(input,PRECISION,FOLLOW_2); 

            			current = grammarAccess.getDoublePrecisionAccess().getDOUBLE_PRECISIONEnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getDoublePrecisionAccess().getDOUBLE_PRECISIONEnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDoublePrecision"


    // $ANTLR start "ruleZoneNoTz"
    // InternalSQLParser.g:4431:1: ruleZoneNoTz returns [Enumerator current=null] : (enumLiteral_0= ZONE ) ;
    public final Enumerator ruleZoneNoTz() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4437:2: ( (enumLiteral_0= ZONE ) )
            // InternalSQLParser.g:4438:2: (enumLiteral_0= ZONE )
            {
            // InternalSQLParser.g:4438:2: (enumLiteral_0= ZONE )
            // InternalSQLParser.g:4439:3: enumLiteral_0= ZONE
            {
            enumLiteral_0=(Token)match(input,ZONE,FOLLOW_2); 

            			current = grammarAccess.getZoneNoTzAccess().getTIMESTAMPEnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getZoneNoTzAccess().getTIMESTAMPEnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleZoneNoTz"


    // $ANTLR start "ruleZoneTz"
    // InternalSQLParser.g:4448:1: ruleZoneTz returns [Enumerator current=null] : (enumLiteral_0= ZONE ) ;
    public final Enumerator ruleZoneTz() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4454:2: ( (enumLiteral_0= ZONE ) )
            // InternalSQLParser.g:4455:2: (enumLiteral_0= ZONE )
            {
            // InternalSQLParser.g:4455:2: (enumLiteral_0= ZONE )
            // InternalSQLParser.g:4456:3: enumLiteral_0= ZONE
            {
            enumLiteral_0=(Token)match(input,ZONE,FOLLOW_2); 

            			current = grammarAccess.getZoneTzAccess().getTIMESTAMPTZEnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getZoneTzAccess().getTIMESTAMPTZEnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleZoneTz"


    // $ANTLR start "ruleRefAction"
    // InternalSQLParser.g:4465:1: ruleRefAction returns [Enumerator current=null] : ( (enumLiteral_0= CASCADE ) | (enumLiteral_1= RESTRICT ) ) ;
    public final Enumerator ruleRefAction() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4471:2: ( ( (enumLiteral_0= CASCADE ) | (enumLiteral_1= RESTRICT ) ) )
            // InternalSQLParser.g:4472:2: ( (enumLiteral_0= CASCADE ) | (enumLiteral_1= RESTRICT ) )
            {
            // InternalSQLParser.g:4472:2: ( (enumLiteral_0= CASCADE ) | (enumLiteral_1= RESTRICT ) )
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==CASCADE) ) {
                alt81=1;
            }
            else if ( (LA81_0==RESTRICT) ) {
                alt81=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }
            switch (alt81) {
                case 1 :
                    // InternalSQLParser.g:4473:3: (enumLiteral_0= CASCADE )
                    {
                    // InternalSQLParser.g:4473:3: (enumLiteral_0= CASCADE )
                    // InternalSQLParser.g:4474:4: enumLiteral_0= CASCADE
                    {
                    enumLiteral_0=(Token)match(input,CASCADE,FOLLOW_2); 

                    				current = grammarAccess.getRefActionAccess().getCASCADEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getRefActionAccess().getCASCADEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:4481:3: (enumLiteral_1= RESTRICT )
                    {
                    // InternalSQLParser.g:4481:3: (enumLiteral_1= RESTRICT )
                    // InternalSQLParser.g:4482:4: enumLiteral_1= RESTRICT
                    {
                    enumLiteral_1=(Token)match(input,RESTRICT,FOLLOW_2); 

                    				current = grammarAccess.getRefActionAccess().getRESTRICTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getRefActionAccess().getRESTRICTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRefAction"


    // $ANTLR start "ruleNoAction"
    // InternalSQLParser.g:4492:1: ruleNoAction returns [Enumerator current=null] : (enumLiteral_0= ACTION ) ;
    public final Enumerator ruleNoAction() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4498:2: ( (enumLiteral_0= ACTION ) )
            // InternalSQLParser.g:4499:2: (enumLiteral_0= ACTION )
            {
            // InternalSQLParser.g:4499:2: (enumLiteral_0= ACTION )
            // InternalSQLParser.g:4500:3: enumLiteral_0= ACTION
            {
            enumLiteral_0=(Token)match(input,ACTION,FOLLOW_2); 

            			current = grammarAccess.getNoActionAccess().getNO_ACTIONEnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getNoActionAccess().getNO_ACTIONEnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNoAction"


    // $ANTLR start "ruleSetAction"
    // InternalSQLParser.g:4509:1: ruleSetAction returns [Enumerator current=null] : ( (enumLiteral_0= NULL ) | (enumLiteral_1= DEFAULT ) ) ;
    public final Enumerator ruleSetAction() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4515:2: ( ( (enumLiteral_0= NULL ) | (enumLiteral_1= DEFAULT ) ) )
            // InternalSQLParser.g:4516:2: ( (enumLiteral_0= NULL ) | (enumLiteral_1= DEFAULT ) )
            {
            // InternalSQLParser.g:4516:2: ( (enumLiteral_0= NULL ) | (enumLiteral_1= DEFAULT ) )
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==NULL) ) {
                alt82=1;
            }
            else if ( (LA82_0==DEFAULT) ) {
                alt82=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }
            switch (alt82) {
                case 1 :
                    // InternalSQLParser.g:4517:3: (enumLiteral_0= NULL )
                    {
                    // InternalSQLParser.g:4517:3: (enumLiteral_0= NULL )
                    // InternalSQLParser.g:4518:4: enumLiteral_0= NULL
                    {
                    enumLiteral_0=(Token)match(input,NULL,FOLLOW_2); 

                    				current = grammarAccess.getSetActionAccess().getSET_NULLEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getSetActionAccess().getSET_NULLEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:4525:3: (enumLiteral_1= DEFAULT )
                    {
                    // InternalSQLParser.g:4525:3: (enumLiteral_1= DEFAULT )
                    // InternalSQLParser.g:4526:4: enumLiteral_1= DEFAULT
                    {
                    enumLiteral_1=(Token)match(input,DEFAULT,FOLLOW_2); 

                    				current = grammarAccess.getSetActionAccess().getSET_DEFAULTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getSetActionAccess().getSET_DEFAULTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSetAction"


    // $ANTLR start "ruleMatchKind"
    // InternalSQLParser.g:4536:1: ruleMatchKind returns [Enumerator current=null] : ( (enumLiteral_0= SIMPLE ) | (enumLiteral_1= FULL ) ) ;
    public final Enumerator ruleMatchKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4542:2: ( ( (enumLiteral_0= SIMPLE ) | (enumLiteral_1= FULL ) ) )
            // InternalSQLParser.g:4543:2: ( (enumLiteral_0= SIMPLE ) | (enumLiteral_1= FULL ) )
            {
            // InternalSQLParser.g:4543:2: ( (enumLiteral_0= SIMPLE ) | (enumLiteral_1= FULL ) )
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==SIMPLE) ) {
                alt83=1;
            }
            else if ( (LA83_0==FULL) ) {
                alt83=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;
            }
            switch (alt83) {
                case 1 :
                    // InternalSQLParser.g:4544:3: (enumLiteral_0= SIMPLE )
                    {
                    // InternalSQLParser.g:4544:3: (enumLiteral_0= SIMPLE )
                    // InternalSQLParser.g:4545:4: enumLiteral_0= SIMPLE
                    {
                    enumLiteral_0=(Token)match(input,SIMPLE,FOLLOW_2); 

                    				current = grammarAccess.getMatchKindAccess().getSIMPLEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getMatchKindAccess().getSIMPLEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:4552:3: (enumLiteral_1= FULL )
                    {
                    // InternalSQLParser.g:4552:3: (enumLiteral_1= FULL )
                    // InternalSQLParser.g:4553:4: enumLiteral_1= FULL
                    {
                    enumLiteral_1=(Token)match(input,FULL,FOLLOW_2); 

                    				current = grammarAccess.getMatchKindAccess().getFULLEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getMatchKindAccess().getFULLEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMatchKind"


    // $ANTLR start "ruleDefaultKind"
    // InternalSQLParser.g:4563:1: ruleDefaultKind returns [Enumerator current=null] : (enumLiteral_0= DEFAULT ) ;
    public final Enumerator ruleDefaultKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4569:2: ( (enumLiteral_0= DEFAULT ) )
            // InternalSQLParser.g:4570:2: (enumLiteral_0= DEFAULT )
            {
            // InternalSQLParser.g:4570:2: (enumLiteral_0= DEFAULT )
            // InternalSQLParser.g:4571:3: enumLiteral_0= DEFAULT
            {
            enumLiteral_0=(Token)match(input,DEFAULT,FOLLOW_2); 

            			current = grammarAccess.getDefaultKindAccess().getDEFAULTEnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getDefaultKindAccess().getDEFAULTEnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDefaultKind"


    // $ANTLR start "ruleCheckKind"
    // InternalSQLParser.g:4580:1: ruleCheckKind returns [Enumerator current=null] : (enumLiteral_0= CHECK ) ;
    public final Enumerator ruleCheckKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4586:2: ( (enumLiteral_0= CHECK ) )
            // InternalSQLParser.g:4587:2: (enumLiteral_0= CHECK )
            {
            // InternalSQLParser.g:4587:2: (enumLiteral_0= CHECK )
            // InternalSQLParser.g:4588:3: enumLiteral_0= CHECK
            {
            enumLiteral_0=(Token)match(input,CHECK,FOLLOW_2); 

            			current = grammarAccess.getCheckKindAccess().getCHECKEnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getCheckKindAccess().getCHECKEnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCheckKind"


    // $ANTLR start "ruleGeneratedKind"
    // InternalSQLParser.g:4597:1: ruleGeneratedKind returns [Enumerator current=null] : (enumLiteral_0= GENERATED ) ;
    public final Enumerator ruleGeneratedKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4603:2: ( (enumLiteral_0= GENERATED ) )
            // InternalSQLParser.g:4604:2: (enumLiteral_0= GENERATED )
            {
            // InternalSQLParser.g:4604:2: (enumLiteral_0= GENERATED )
            // InternalSQLParser.g:4605:3: enumLiteral_0= GENERATED
            {
            enumLiteral_0=(Token)match(input,GENERATED,FOLLOW_2); 

            			current = grammarAccess.getGeneratedKindAccess().getGENERATEDEnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getGeneratedKindAccess().getGENERATEDEnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGeneratedKind"


    // $ANTLR start "ruleWithKind"
    // InternalSQLParser.g:4614:1: ruleWithKind returns [Enumerator current=null] : (enumLiteral_0= WITH ) ;
    public final Enumerator ruleWithKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:4620:2: ( (enumLiteral_0= WITH ) )
            // InternalSQLParser.g:4621:2: (enumLiteral_0= WITH )
            {
            // InternalSQLParser.g:4621:2: (enumLiteral_0= WITH )
            // InternalSQLParser.g:4622:3: enumLiteral_0= WITH
            {
            enumLiteral_0=(Token)match(input,WITH,FOLLOW_2); 

            			current = grammarAccess.getWithKindAccess().getWITHEnumLiteralDeclaration().getEnumLiteral().getInstance();
            			newLeafNode(enumLiteral_0, grammarAccess.getWithKindAccess().getWITHEnumLiteralDeclaration());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleWithKind"

    // Delegated rules


    protected DFA5 dfa5 = new DFA5(this);
    protected DFA12 dfa12 = new DFA12(this);
    protected DFA13 dfa13 = new DFA13(this);
    protected DFA18 dfa18 = new DFA18(this);
    protected DFA28 dfa28 = new DFA28(this);
    protected DFA35 dfa35 = new DFA35(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA39 dfa39 = new DFA39(this);
    static final String dfa_1s = "\70\uffff";
    static final String dfa_2s = "\1\4\65\113\2\uffff";
    static final String dfa_3s = "\1\121\65\116\2\uffff";
    static final String dfa_4s = "\66\uffff\1\1\1\2";
    static final String dfa_5s = "\70\uffff}>";
    static final String[] dfa_6s = {
            "\1\36\3\uffff\1\35\1\24\1\65\1\53\1\uffff\1\22\1\12\1\52\1\56\1\44\1\16\1\13\1\43\1\11\2\uffff\1\64\1\15\1\17\1\uffff\1\14\1\27\1\30\1\46\1\54\1\10\1\uffff\1\41\1\26\1\6\1\63\1\34\1\51\1\57\1\uffff\1\42\1\60\1\40\1\uffff\1\21\1\62\1\37\1\50\3\uffff\1\23\1\33\3\uffff\1\20\1\4\1\25\1\32\1\uffff\1\31\1\61\1\7\1\3\1\uffff\1\47\1\uffff\1\55\1\5\1\45\6\uffff\1\1\1\2",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "\1\67\2\uffff\1\66",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "231:3: ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )?";
        }
    }
    static final String dfa_7s = "\1\4\65\54\2\uffff";
    static final String dfa_8s = "\1\121\65\120\2\uffff";
    static final String dfa_9s = "\66\uffff\1\2\1\1";
    static final String[] dfa_10s = {
            "\1\36\3\uffff\1\35\1\24\1\65\1\53\1\uffff\1\22\1\12\1\52\1\56\1\44\1\16\1\13\1\43\1\11\2\uffff\1\64\1\15\1\17\1\uffff\1\14\1\27\1\30\1\46\1\54\1\10\1\uffff\1\41\1\26\1\6\1\63\1\34\1\51\1\57\1\uffff\1\42\1\60\1\40\1\uffff\1\21\1\62\1\37\1\50\3\uffff\1\23\1\33\3\uffff\1\20\1\4\1\25\1\32\1\uffff\1\31\1\61\1\7\1\3\1\uffff\1\47\1\uffff\1\55\1\5\1\45\6\uffff\1\1\1\2",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "\1\66\24\uffff\1\66\3\uffff\1\66\10\uffff\1\67\1\uffff\1\66",
            "",
            ""
    };
    static final char[] dfa_7 = DFA.unpackEncodedStringToUnsignedChars(dfa_7s);
    static final char[] dfa_8 = DFA.unpackEncodedStringToUnsignedChars(dfa_8s);
    static final short[] dfa_9 = DFA.unpackEncodedString(dfa_9s);
    static final short[][] dfa_10 = unpackEncodedStringArray(dfa_10s);

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_7;
            this.max = dfa_8;
            this.accept = dfa_9;
            this.special = dfa_5;
            this.transition = dfa_10;
        }
        public String getDescription() {
            return "508:3: ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )?";
        }
    }
    static final String[] dfa_11s = {
            "\1\36\3\uffff\1\35\1\24\1\65\1\53\1\uffff\1\22\1\12\1\52\1\56\1\44\1\16\1\13\1\43\1\11\2\uffff\1\64\1\15\1\17\1\uffff\1\14\1\27\1\30\1\46\1\54\1\10\1\uffff\1\41\1\26\1\6\1\63\1\34\1\51\1\57\1\uffff\1\42\1\60\1\40\1\uffff\1\21\1\62\1\37\1\50\3\uffff\1\23\1\33\3\uffff\1\20\1\4\1\25\1\32\1\uffff\1\31\1\61\1\7\1\3\1\uffff\1\47\1\uffff\1\55\1\5\1\45\6\uffff\1\1\1\2",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "\1\67\24\uffff\1\66\3\uffff\1\67\12\uffff\1\67",
            "",
            ""
    };
    static final short[][] dfa_11 = unpackEncodedStringArray(dfa_11s);

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_7;
            this.max = dfa_8;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_11;
        }
        public String getDescription() {
            return "533:3: ( ( ( ( ruleName ) ) otherlv_8= ADD ( (lv_constraint_9_0= ruleTableConstraint ) ) ) | ( ( (lv_target_10_0= ruleName ) ) ( (lv_body_11_0= ruleAlterTableBody ) ) otherlv_12= Semicolon ) )";
        }
    }
    static final String dfa_12s = "\1\4\65\64\2\uffff";
    static final String[] dfa_13s = {
            "\1\36\3\uffff\1\35\1\24\1\65\1\53\1\uffff\1\22\1\12\1\52\1\56\1\44\1\16\1\13\1\43\1\11\2\uffff\1\64\1\15\1\17\1\uffff\1\14\1\27\1\30\1\46\1\54\1\10\1\uffff\1\41\1\26\1\6\1\63\1\34\1\51\1\57\1\uffff\1\42\1\60\1\40\1\uffff\1\21\1\62\1\37\1\50\3\uffff\1\23\1\33\3\uffff\1\20\1\4\1\25\1\32\1\uffff\1\31\1\61\1\7\1\3\1\uffff\1\47\1\uffff\1\55\1\5\1\45\6\uffff\1\1\1\2",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "\1\67\26\uffff\1\67\2\uffff\1\66",
            "",
            ""
    };
    static final char[] dfa_12 = DFA.unpackEncodedStringToUnsignedChars(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_12;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "723:3: ( ( (lv_schemaName_9_0= ruleName ) ) otherlv_10= FullStop )?";
        }
    }
    static final String dfa_14s = "\14\uffff";
    static final String dfa_15s = "\2\uffff\3\1\7\uffff";
    static final String dfa_16s = "\1\4\1\uffff\3\5\1\uffff\1\35\5\uffff";
    static final String dfa_17s = "\1\102\1\uffff\3\115\1\uffff\1\113\5\uffff";
    static final String dfa_18s = "\1\uffff\1\1\3\uffff\1\2\1\uffff\1\4\1\5\1\3\1\6\1\7";
    static final String dfa_19s = "\14\uffff}>";
    static final String[] dfa_20s = {
            "\1\1\3\uffff\1\1\1\6\4\uffff\1\3\3\uffff\2\1\1\uffff\1\2\3\uffff\1\1\1\4\1\uffff\1\5\4\uffff\1\1\2\uffff\1\10\2\uffff\1\1\5\uffff\1\1\1\uffff\1\1\1\uffff\1\1\4\uffff\1\7\1\1\3\uffff\2\1\1\uffff\1\1\3\uffff\1\1",
            "",
            "\1\1\1\uffff\1\1\2\uffff\1\1\13\uffff\1\1\4\uffff\1\1\16\uffff\1\1\3\uffff\1\1\12\uffff\1\1\12\uffff\1\1\6\uffff\1\11\2\1",
            "\1\1\1\uffff\1\1\2\uffff\1\1\13\uffff\1\1\4\uffff\1\1\2\uffff\1\12\13\uffff\1\1\3\uffff\1\1\12\uffff\1\1\5\uffff\1\13\4\uffff\1\1\7\uffff\2\1",
            "\1\1\1\uffff\1\1\2\uffff\1\1\13\uffff\1\1\4\uffff\1\1\16\uffff\1\1\3\uffff\1\1\12\uffff\1\1\12\uffff\1\1\6\uffff\1\11\2\1",
            "",
            "\1\5\55\uffff\1\7",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final short[] dfa_15 = DFA.unpackEncodedString(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final char[] dfa_17 = DFA.unpackEncodedStringToUnsignedChars(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final short[][] dfa_20 = unpackEncodedStringArray(dfa_20s);

    class DFA28 extends DFA {

        public DFA28(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 28;
            this.eot = dfa_14;
            this.eof = dfa_15;
            this.min = dfa_16;
            this.max = dfa_17;
            this.accept = dfa_18;
            this.special = dfa_19;
            this.transition = dfa_20;
        }
        public String getDescription() {
            return "1202:3: ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) )";
        }
    }
    static final String dfa_21s = "\75\uffff";
    static final String dfa_22s = "\1\5\1\4\6\uffff\65\7";
    static final String dfa_23s = "\1\104\1\121\6\uffff\65\104";
    static final String dfa_24s = "\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\65\uffff";
    static final String dfa_25s = "\75\uffff}>";
    static final String[] dfa_26s = {
            "\1\1\1\uffff\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\45\3\uffff\1\44\1\33\1\74\1\62\1\uffff\1\31\1\21\1\61\1\65\1\53\1\25\1\22\1\52\1\20\2\uffff\1\73\1\24\1\26\1\uffff\1\23\1\36\1\37\1\55\1\63\1\17\1\uffff\1\50\1\35\1\15\1\72\1\43\1\60\1\66\1\uffff\1\51\1\67\1\47\1\uffff\1\30\1\71\1\46\1\57\3\uffff\1\32\1\42\3\uffff\1\27\1\13\1\34\1\41\1\uffff\1\40\1\70\1\16\1\12\1\uffff\1\56\1\uffff\1\64\1\14\1\54\6\uffff\1\10\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\4\uffff\1\2\16\uffff\1\3\3\uffff\1\7\12\uffff\1\5\12\uffff\1\4"
    };

    static final short[] dfa_21 = DFA.unpackEncodedString(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final char[] dfa_23 = DFA.unpackEncodedStringToUnsignedChars(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final short[][] dfa_26 = unpackEncodedStringArray(dfa_26s);

    class DFA35 extends DFA {

        public DFA35(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 35;
            this.eot = dfa_21;
            this.eof = dfa_21;
            this.min = dfa_22;
            this.max = dfa_23;
            this.accept = dfa_24;
            this.special = dfa_25;
            this.transition = dfa_26;
        }
        public String getDescription() {
            return "1678:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull | this_Nullable_3= ruleNullable | this_ColumnReferences_4= ruleColumnReferences | this_ColumnRaw_5= ruleColumnRaw )";
        }
    }
    static final String dfa_27s = "\73\uffff";
    static final String dfa_28s = "\1\5\1\4\4\uffff\65\27";
    static final String dfa_29s = "\1\56\1\121\4\uffff\65\56";
    static final String dfa_30s = "\2\uffff\1\1\1\2\1\3\1\4\65\uffff";
    static final String dfa_31s = "\73\uffff}>";
    static final String[] dfa_32s = {
            "\1\1\21\uffff\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\43\3\uffff\1\42\1\31\1\72\1\60\1\uffff\1\27\1\17\1\57\1\63\1\51\1\23\1\20\1\50\1\16\2\uffff\1\71\1\22\1\24\1\uffff\1\21\1\34\1\35\1\53\1\61\1\15\1\uffff\1\46\1\33\1\13\1\70\1\41\1\56\1\64\1\uffff\1\47\1\65\1\45\1\uffff\1\26\1\67\1\44\1\55\3\uffff\1\30\1\40\3\uffff\1\25\1\11\1\32\1\37\1\uffff\1\36\1\66\1\14\1\10\1\uffff\1\54\1\uffff\1\62\1\12\1\52\6\uffff\1\6\1\7",
            "",
            "",
            "",
            "",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5",
            "\1\4\3\uffff\1\2\16\uffff\1\3\3\uffff\1\5"
    };

    static final short[] dfa_27 = DFA.unpackEncodedString(dfa_27s);
    static final char[] dfa_28 = DFA.unpackEncodedStringToUnsignedChars(dfa_28s);
    static final char[] dfa_29 = DFA.unpackEncodedStringToUnsignedChars(dfa_29s);
    static final short[] dfa_30 = DFA.unpackEncodedString(dfa_30s);
    static final short[] dfa_31 = DFA.unpackEncodedString(dfa_31s);
    static final short[][] dfa_32 = unpackEncodedStringArray(dfa_32s);

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = dfa_27;
            this.eof = dfa_27;
            this.min = dfa_28;
            this.max = dfa_29;
            this.accept = dfa_30;
            this.special = dfa_31;
            this.transition = dfa_32;
        }
        public String getDescription() {
            return "2121:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey | this_ForeignKey_2= ruleForeignKey | this_TableCheck_3= ruleTableCheck )";
        }
    }
    static final String dfa_33s = "\1\uffff\65\67\2\uffff";
    static final String dfa_34s = "\1\4\65\5\2\uffff";
    static final String dfa_35s = "\1\121\65\131\2\uffff";
    static final String[] dfa_36s = {
            "\1\36\3\uffff\1\35\1\24\1\65\1\53\1\uffff\1\22\1\12\1\52\1\56\1\44\1\16\1\13\1\43\1\11\2\uffff\1\64\1\15\1\17\1\uffff\1\14\1\27\1\30\1\46\1\54\1\10\1\uffff\1\41\1\26\1\6\1\63\1\34\1\51\1\57\1\uffff\1\42\1\60\1\40\1\uffff\1\21\1\62\1\37\1\50\3\uffff\1\23\1\33\3\uffff\1\20\1\4\1\25\1\32\1\uffff\1\31\1\61\1\7\1\3\1\uffff\1\47\1\uffff\1\55\1\5\1\45\6\uffff\1\1\1\2",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "\3\67\2\uffff\1\67\1\uffff\1\67\11\uffff\1\67\4\uffff\1\67\6\uffff\2\67\6\uffff\3\67\1\uffff\1\67\3\uffff\1\67\6\uffff\1\67\12\uffff\2\67\4\uffff\4\67\1\66\2\67\4\uffff\1\67\3\uffff\1\67",
            "",
            ""
    };
    static final short[] dfa_33 = DFA.unpackEncodedString(dfa_33s);
    static final char[] dfa_34 = DFA.unpackEncodedStringToUnsignedChars(dfa_34s);
    static final char[] dfa_35 = DFA.unpackEncodedStringToUnsignedChars(dfa_35s);
    static final short[][] dfa_36 = unpackEncodedStringArray(dfa_36s);

    class DFA39 extends DFA {

        public DFA39(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 39;
            this.eot = dfa_1;
            this.eof = dfa_33;
            this.min = dfa_34;
            this.max = dfa_35;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_36;
        }
        public String getDescription() {
            return "2493:3: ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )?";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000180C00000002L,0x0000000002218020L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x78C7BBFBF73FEF10L,0x00000000000303AFL});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x78C7FFFBFFBFEF30L,0x00000000000303AFL});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000000003000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x7CC7BBFBF73FEF10L,0x00000000000303AFL});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000100000000000L,0x0000000000010020L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0001040000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x78C7BBFBF73FEF10L,0x00000000000307AFL});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0010000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x78C7BBFBF73FEF10L,0x0000000000030BAFL});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x8020000001000002L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x78C7BBFBF73FEF12L,0x0000000000074BAFL});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0001004000400000L,0x0000000000010000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x00000000021FFFFFL});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x58C2A092162C4310L,0x0000000000000004L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x7AC7FFFBFFFFEFB2L,0x00000000000303BFL});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x7AC7FFFBFFFFEFB0L,0x00000000000303BFL});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0xFAC7BBFBF73FEB10L,0x00000000020F4BAFL});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000100000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0004000000001042L,0x0000000000000C10L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0004000000001042L,0x0000000000000410L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0100010000000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000001042L,0x0000000000000410L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000001042L,0x0000000000000010L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000120000L,0x0000000000000220L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0200000000400000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000008800L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000042L,0x0000000000000010L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000800L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0xF8C7BBFBF73FEB12L,0x00000000020F4BAFL});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x00000000021F7FFFL});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF2L,0x00000000021F7FFFL});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x8020000000000002L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0020000000000002L});

}