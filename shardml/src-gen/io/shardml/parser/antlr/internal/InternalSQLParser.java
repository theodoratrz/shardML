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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "TIMESTAMPTZ", "CONSTRAINT", "DEFERRABLE", "REFERENCES", "BIGSERIAL", "CHARACTER", "GENERATED", "IMMEDIATE", "INITIALLY", "PRECISION", "TIMESTAMP", "DEFERRED", "IDENTITY", "RESTRICT", "SMALLINT", "BOOLEAN", "CASCADE", "DECIMAL", "DEFAULT", "FOREIGN", "INTEGER", "NUMERIC", "PRIMARY", "VARCHAR", "VARYING", "WITHOUT", "ACTION", "ALWAYS", "BIGINT", "CREATE", "DELETE", "DOUBLE", "EXISTS", "SERIAL", "SIMPLE", "STORED", "UNIQUE", "UPDATE", "BYTEA", "CHECK", "FLOAT", "JSONB", "MATCH", "TABLE", "CHAR", "DATE", "FULL", "NULL", "REAL", "TEXT", "TIME", "UUID", "WITH", "ZONE", "INT", "KEY", "NOT", "SET", "AS", "BY", "IF", "NO", "ON", "LeftParenthesis", "RightParenthesis", "Comma", "FullStop", "Semicolon", "RULE_ID", "RULE_QUOTED_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int CREATE=33;
    public static final int ZONE=57;
    public static final int DECIMAL=21;
    public static final int TIMESTAMPTZ=4;
    public static final int TEXT=53;
    public static final int IMMEDIATE=11;
    public static final int TABLE=47;
    public static final int LeftParenthesis=67;
    public static final int TIMESTAMP=14;
    public static final int DEFERRED=15;
    public static final int UUID=55;
    public static final int BIGSERIAL=8;
    public static final int IF=64;
    public static final int INTEGER=24;
    public static final int VARYING=28;
    public static final int NULL=51;
    public static final int RULE_ID=72;
    public static final int RULE_QUOTED_ID=73;
    public static final int UNIQUE=40;
    public static final int RightParenthesis=68;
    public static final int RESTRICT=17;
    public static final int BIGINT=32;
    public static final int BOOLEAN=19;
    public static final int FULL=50;
    public static final int IDENTITY=16;
    public static final int DATE=49;
    public static final int NOT=60;
    public static final int AS=62;
    public static final int RULE_INT=75;
    public static final int VARCHAR=27;
    public static final int REAL=52;
    public static final int RULE_ML_COMMENT=76;
    public static final int DEFERRABLE=6;
    public static final int WITHOUT=29;
    public static final int CHECK=43;
    public static final int KEY=59;
    public static final int NO=65;
    public static final int FLOAT=44;
    public static final int SET=61;
    public static final int STORED=39;
    public static final int CASCADE=20;
    public static final int CHAR=48;
    public static final int RULE_STRING=74;
    public static final int CONSTRAINT=5;
    public static final int ALWAYS=31;
    public static final int INT=58;
    public static final int PRECISION=13;
    public static final int RULE_SL_COMMENT=77;
    public static final int Comma=69;
    public static final int DELETE=34;
    public static final int FOREIGN=23;
    public static final int BY=63;
    public static final int CHARACTER=9;
    public static final int UPDATE=41;
    public static final int DOUBLE=35;
    public static final int EOF=-1;
    public static final int GENERATED=10;
    public static final int ON=66;
    public static final int SERIAL=37;
    public static final int FullStop=70;
    public static final int NUMERIC=25;
    public static final int JSONB=45;
    public static final int EXISTS=36;
    public static final int RULE_WS=78;
    public static final int PRIMARY=26;
    public static final int TIME=54;
    public static final int RULE_ANY_OTHER=79;
    public static final int WITH=56;
    public static final int Semicolon=71;
    public static final int INITIALLY=12;
    public static final int BYTEA=42;
    public static final int ACTION=30;
    public static final int SMALLINT=18;
    public static final int SIMPLE=38;
    public static final int REFERENCES=7;
    public static final int DEFAULT=22;
    public static final int MATCH=46;

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
    // InternalSQLParser.g:65:1: ruleSchema returns [EObject current=null] : ( ( (lv_tables_0_0= ruleTable ) ) (otherlv_1= Semicolon )? )* ;
    public final EObject ruleSchema() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_tables_0_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:71:2: ( ( ( (lv_tables_0_0= ruleTable ) ) (otherlv_1= Semicolon )? )* )
            // InternalSQLParser.g:72:2: ( ( (lv_tables_0_0= ruleTable ) ) (otherlv_1= Semicolon )? )*
            {
            // InternalSQLParser.g:72:2: ( ( (lv_tables_0_0= ruleTable ) ) (otherlv_1= Semicolon )? )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==CREATE) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSQLParser.g:73:3: ( (lv_tables_0_0= ruleTable ) ) (otherlv_1= Semicolon )?
            	    {
            	    // InternalSQLParser.g:73:3: ( (lv_tables_0_0= ruleTable ) )
            	    // InternalSQLParser.g:74:4: (lv_tables_0_0= ruleTable )
            	    {
            	    // InternalSQLParser.g:74:4: (lv_tables_0_0= ruleTable )
            	    // InternalSQLParser.g:75:5: lv_tables_0_0= ruleTable
            	    {

            	    					newCompositeNode(grammarAccess.getSchemaAccess().getTablesTableParserRuleCall_0_0());
            	    				
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

            	    // InternalSQLParser.g:92:3: (otherlv_1= Semicolon )?
            	    int alt1=2;
            	    int LA1_0 = input.LA(1);

            	    if ( (LA1_0==Semicolon) ) {
            	        alt1=1;
            	    }
            	    switch (alt1) {
            	        case 1 :
            	            // InternalSQLParser.g:93:4: otherlv_1= Semicolon
            	            {
            	            otherlv_1=(Token)match(input,Semicolon,FOLLOW_3); 

            	            				newLeafNode(otherlv_1, grammarAccess.getSchemaAccess().getSemicolonKeyword_1());
            	            			

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
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
    // InternalSQLParser.g:102:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalSQLParser.g:102:46: (iv_ruleTable= ruleTable EOF )
            // InternalSQLParser.g:103:2: iv_ruleTable= ruleTable EOF
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
    // InternalSQLParser.g:109:1: ruleTable returns [EObject current=null] : (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ( (lv_options_15_0= ruleTableOptions ) )? ) ;
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
            // InternalSQLParser.g:115:2: ( (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ( (lv_options_15_0= ruleTableOptions ) )? ) )
            // InternalSQLParser.g:116:2: (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ( (lv_options_15_0= ruleTableOptions ) )? )
            {
            // InternalSQLParser.g:116:2: (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ( (lv_options_15_0= ruleTableOptions ) )? )
            // InternalSQLParser.g:117:3: otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ( (lv_options_15_0= ruleTableOptions ) )?
            {
            otherlv_0=(Token)match(input,CREATE,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getTableAccess().getCREATEKeyword_0());
            		
            otherlv_1=(Token)match(input,TABLE,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getTableAccess().getTABLEKeyword_1());
            		
            // InternalSQLParser.g:125:3: ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==IF) ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1==NOT) ) {
                    alt3=1;
                }
            }
            switch (alt3) {
                case 1 :
                    // InternalSQLParser.g:126:4: ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS
                    {
                    // InternalSQLParser.g:126:4: ( (lv_ifNotExists_2_0= IF ) )
                    // InternalSQLParser.g:127:5: (lv_ifNotExists_2_0= IF )
                    {
                    // InternalSQLParser.g:127:5: (lv_ifNotExists_2_0= IF )
                    // InternalSQLParser.g:128:6: lv_ifNotExists_2_0= IF
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

            // InternalSQLParser.g:149:3: ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )?
            int alt4=2;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // InternalSQLParser.g:150:4: ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop
                    {
                    // InternalSQLParser.g:150:4: ( (lv_schemaName_5_0= ruleName ) )
                    // InternalSQLParser.g:151:5: (lv_schemaName_5_0= ruleName )
                    {
                    // InternalSQLParser.g:151:5: (lv_schemaName_5_0= ruleName )
                    // InternalSQLParser.g:152:6: lv_schemaName_5_0= ruleName
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

            // InternalSQLParser.g:174:3: ( (lv_name_7_0= ruleName ) )
            // InternalSQLParser.g:175:4: (lv_name_7_0= ruleName )
            {
            // InternalSQLParser.g:175:4: (lv_name_7_0= ruleName )
            // InternalSQLParser.g:176:5: lv_name_7_0= ruleName
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
            		
            // InternalSQLParser.g:197:3: ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==TIMESTAMPTZ||(LA5_0>=BIGSERIAL && LA5_0<=IMMEDIATE)||(LA5_0>=PRECISION && LA5_0<=DECIMAL)||(LA5_0>=INTEGER && LA5_0<=NUMERIC)||(LA5_0>=VARCHAR && LA5_0<=BIGINT)||(LA5_0>=DELETE && LA5_0<=STORED)||(LA5_0>=UPDATE && LA5_0<=BYTEA)||(LA5_0>=FLOAT && LA5_0<=MATCH)||(LA5_0>=CHAR && LA5_0<=DATE)||(LA5_0>=REAL && LA5_0<=UUID)||(LA5_0>=ZONE && LA5_0<=KEY)||LA5_0==SET||(LA5_0>=BY && LA5_0<=NO)||(LA5_0>=RULE_ID && LA5_0<=RULE_QUOTED_ID)) ) {
                alt5=1;
            }
            else if ( (LA5_0==CONSTRAINT||LA5_0==FOREIGN||LA5_0==PRIMARY||LA5_0==UNIQUE||LA5_0==CHECK) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalSQLParser.g:198:4: ( (lv_columns_9_0= ruleColumn ) )
                    {
                    // InternalSQLParser.g:198:4: ( (lv_columns_9_0= ruleColumn ) )
                    // InternalSQLParser.g:199:5: (lv_columns_9_0= ruleColumn )
                    {
                    // InternalSQLParser.g:199:5: (lv_columns_9_0= ruleColumn )
                    // InternalSQLParser.g:200:6: lv_columns_9_0= ruleColumn
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
                    // InternalSQLParser.g:218:4: ( (lv_constraints_10_0= ruleTableConstraint ) )
                    {
                    // InternalSQLParser.g:218:4: ( (lv_constraints_10_0= ruleTableConstraint ) )
                    // InternalSQLParser.g:219:5: (lv_constraints_10_0= ruleTableConstraint )
                    {
                    // InternalSQLParser.g:219:5: (lv_constraints_10_0= ruleTableConstraint )
                    // InternalSQLParser.g:220:6: lv_constraints_10_0= ruleTableConstraint
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

            // InternalSQLParser.g:238:3: (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==Comma) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalSQLParser.g:239:4: otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) )
            	    {
            	    otherlv_11=(Token)match(input,Comma,FOLLOW_10); 

            	    				newLeafNode(otherlv_11, grammarAccess.getTableAccess().getCommaKeyword_7_0());
            	    			
            	    // InternalSQLParser.g:243:4: ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) )
            	    int alt6=2;
            	    int LA6_0 = input.LA(1);

            	    if ( (LA6_0==TIMESTAMPTZ||(LA6_0>=BIGSERIAL && LA6_0<=IMMEDIATE)||(LA6_0>=PRECISION && LA6_0<=DECIMAL)||(LA6_0>=INTEGER && LA6_0<=NUMERIC)||(LA6_0>=VARCHAR && LA6_0<=BIGINT)||(LA6_0>=DELETE && LA6_0<=STORED)||(LA6_0>=UPDATE && LA6_0<=BYTEA)||(LA6_0>=FLOAT && LA6_0<=MATCH)||(LA6_0>=CHAR && LA6_0<=DATE)||(LA6_0>=REAL && LA6_0<=UUID)||(LA6_0>=ZONE && LA6_0<=KEY)||LA6_0==SET||(LA6_0>=BY && LA6_0<=NO)||(LA6_0>=RULE_ID && LA6_0<=RULE_QUOTED_ID)) ) {
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
            	            // InternalSQLParser.g:244:5: ( (lv_columns_12_0= ruleColumn ) )
            	            {
            	            // InternalSQLParser.g:244:5: ( (lv_columns_12_0= ruleColumn ) )
            	            // InternalSQLParser.g:245:6: (lv_columns_12_0= ruleColumn )
            	            {
            	            // InternalSQLParser.g:245:6: (lv_columns_12_0= ruleColumn )
            	            // InternalSQLParser.g:246:7: lv_columns_12_0= ruleColumn
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
            	            // InternalSQLParser.g:264:5: ( (lv_constraints_13_0= ruleTableConstraint ) )
            	            {
            	            // InternalSQLParser.g:264:5: ( (lv_constraints_13_0= ruleTableConstraint ) )
            	            // InternalSQLParser.g:265:6: (lv_constraints_13_0= ruleTableConstraint )
            	            {
            	            // InternalSQLParser.g:265:6: (lv_constraints_13_0= ruleTableConstraint )
            	            // InternalSQLParser.g:266:7: lv_constraints_13_0= ruleTableConstraint
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
            	    break loop7;
                }
            } while (true);

            otherlv_14=(Token)match(input,RightParenthesis,FOLLOW_12); 

            			newLeafNode(otherlv_14, grammarAccess.getTableAccess().getRightParenthesisKeyword_8());
            		
            // InternalSQLParser.g:289:3: ( (lv_options_15_0= ruleTableOptions ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==WITH) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSQLParser.g:290:4: (lv_options_15_0= ruleTableOptions )
                    {
                    // InternalSQLParser.g:290:4: (lv_options_15_0= ruleTableOptions )
                    // InternalSQLParser.g:291:5: lv_options_15_0= ruleTableOptions
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
    // InternalSQLParser.g:312:1: entryRuleTableOptions returns [EObject current=null] : iv_ruleTableOptions= ruleTableOptions EOF ;
    public final EObject entryRuleTableOptions() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableOptions = null;


        try {
            // InternalSQLParser.g:312:53: (iv_ruleTableOptions= ruleTableOptions EOF )
            // InternalSQLParser.g:313:2: iv_ruleTableOptions= ruleTableOptions EOF
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
    // InternalSQLParser.g:319:1: ruleTableOptions returns [EObject current=null] : ( ( (lv_kind_0_0= ruleWithKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) ) ;
    public final EObject ruleTableOptions() throws RecognitionException {
        EObject current = null;

        Enumerator lv_kind_0_0 = null;

        AntlrDatatypeRuleToken lv_text_1_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:325:2: ( ( ( (lv_kind_0_0= ruleWithKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) ) )
            // InternalSQLParser.g:326:2: ( ( (lv_kind_0_0= ruleWithKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) )
            {
            // InternalSQLParser.g:326:2: ( ( (lv_kind_0_0= ruleWithKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) )
            // InternalSQLParser.g:327:3: ( (lv_kind_0_0= ruleWithKind ) ) ( (lv_text_1_0= ruleParenthesized ) )
            {
            // InternalSQLParser.g:327:3: ( (lv_kind_0_0= ruleWithKind ) )
            // InternalSQLParser.g:328:4: (lv_kind_0_0= ruleWithKind )
            {
            // InternalSQLParser.g:328:4: (lv_kind_0_0= ruleWithKind )
            // InternalSQLParser.g:329:5: lv_kind_0_0= ruleWithKind
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

            // InternalSQLParser.g:346:3: ( (lv_text_1_0= ruleParenthesized ) )
            // InternalSQLParser.g:347:4: (lv_text_1_0= ruleParenthesized )
            {
            // InternalSQLParser.g:347:4: (lv_text_1_0= ruleParenthesized )
            // InternalSQLParser.g:348:5: lv_text_1_0= ruleParenthesized
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


    // $ANTLR start "entryRuleColumn"
    // InternalSQLParser.g:369:1: entryRuleColumn returns [EObject current=null] : iv_ruleColumn= ruleColumn EOF ;
    public final EObject entryRuleColumn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumn = null;


        try {
            // InternalSQLParser.g:369:47: (iv_ruleColumn= ruleColumn EOF )
            // InternalSQLParser.g:370:2: iv_ruleColumn= ruleColumn EOF
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
    // InternalSQLParser.g:376:1: ruleColumn returns [EObject current=null] : ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* ) ;
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
            // InternalSQLParser.g:382:2: ( ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* ) )
            // InternalSQLParser.g:383:2: ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* )
            {
            // InternalSQLParser.g:383:2: ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* )
            // InternalSQLParser.g:384:3: ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )*
            {
            // InternalSQLParser.g:384:3: ( (lv_name_0_0= ruleName ) )
            // InternalSQLParser.g:385:4: (lv_name_0_0= ruleName )
            {
            // InternalSQLParser.g:385:4: (lv_name_0_0= ruleName )
            // InternalSQLParser.g:386:5: lv_name_0_0= ruleName
            {

            					newCompositeNode(grammarAccess.getColumnAccess().getNameNameParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_13);
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

            // InternalSQLParser.g:403:3: ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) )
            int alt9=7;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // InternalSQLParser.g:404:4: ( (lv_simpleType_1_0= ruleAllTypes ) )
                    {
                    // InternalSQLParser.g:404:4: ( (lv_simpleType_1_0= ruleAllTypes ) )
                    // InternalSQLParser.g:405:5: (lv_simpleType_1_0= ruleAllTypes )
                    {
                    // InternalSQLParser.g:405:5: (lv_simpleType_1_0= ruleAllTypes )
                    // InternalSQLParser.g:406:6: lv_simpleType_1_0= ruleAllTypes
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeAllTypesEnumRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_14);
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
                    // InternalSQLParser.g:424:4: ( (lv_varchar_2_0= ruleVarchar ) )
                    {
                    // InternalSQLParser.g:424:4: ( (lv_varchar_2_0= ruleVarchar ) )
                    // InternalSQLParser.g:425:5: (lv_varchar_2_0= ruleVarchar )
                    {
                    // InternalSQLParser.g:425:5: (lv_varchar_2_0= ruleVarchar )
                    // InternalSQLParser.g:426:6: lv_varchar_2_0= ruleVarchar
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getVarcharVarcharParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_14);
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
                    // InternalSQLParser.g:444:4: ( (lv_numeric_3_0= ruleNumeric ) )
                    {
                    // InternalSQLParser.g:444:4: ( (lv_numeric_3_0= ruleNumeric ) )
                    // InternalSQLParser.g:445:5: (lv_numeric_3_0= ruleNumeric )
                    {
                    // InternalSQLParser.g:445:5: (lv_numeric_3_0= ruleNumeric )
                    // InternalSQLParser.g:446:6: lv_numeric_3_0= ruleNumeric
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getNumericNumericParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_14);
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
                    // InternalSQLParser.g:464:4: ( (lv_char_4_0= ruleChar ) )
                    {
                    // InternalSQLParser.g:464:4: ( (lv_char_4_0= ruleChar ) )
                    // InternalSQLParser.g:465:5: (lv_char_4_0= ruleChar )
                    {
                    // InternalSQLParser.g:465:5: (lv_char_4_0= ruleChar )
                    // InternalSQLParser.g:466:6: lv_char_4_0= ruleChar
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getCharCharParserRuleCall_1_3_0());
                    					
                    pushFollow(FOLLOW_14);
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
                    // InternalSQLParser.g:484:4: (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) )
                    {
                    // InternalSQLParser.g:484:4: (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) )
                    // InternalSQLParser.g:485:5: otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) )
                    {
                    otherlv_5=(Token)match(input,DOUBLE,FOLLOW_15); 

                    					newLeafNode(otherlv_5, grammarAccess.getColumnAccess().getDOUBLEKeyword_1_4_0());
                    				
                    // InternalSQLParser.g:489:5: ( (lv_simpleType_6_0= ruleDoublePrecision ) )
                    // InternalSQLParser.g:490:6: (lv_simpleType_6_0= ruleDoublePrecision )
                    {
                    // InternalSQLParser.g:490:6: (lv_simpleType_6_0= ruleDoublePrecision )
                    // InternalSQLParser.g:491:7: lv_simpleType_6_0= ruleDoublePrecision
                    {

                    							newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeDoublePrecisionEnumRuleCall_1_4_1_0());
                    						
                    pushFollow(FOLLOW_14);
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
                    // InternalSQLParser.g:510:4: (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) )
                    {
                    // InternalSQLParser.g:510:4: (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) )
                    // InternalSQLParser.g:511:5: otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) )
                    {
                    otherlv_7=(Token)match(input,TIMESTAMP,FOLLOW_16); 

                    					newLeafNode(otherlv_7, grammarAccess.getColumnAccess().getTIMESTAMPKeyword_1_5_0());
                    				
                    otherlv_8=(Token)match(input,WITHOUT,FOLLOW_17); 

                    					newLeafNode(otherlv_8, grammarAccess.getColumnAccess().getWITHOUTKeyword_1_5_1());
                    				
                    otherlv_9=(Token)match(input,TIME,FOLLOW_18); 

                    					newLeafNode(otherlv_9, grammarAccess.getColumnAccess().getTIMEKeyword_1_5_2());
                    				
                    // InternalSQLParser.g:523:5: ( (lv_simpleType_10_0= ruleZoneNoTz ) )
                    // InternalSQLParser.g:524:6: (lv_simpleType_10_0= ruleZoneNoTz )
                    {
                    // InternalSQLParser.g:524:6: (lv_simpleType_10_0= ruleZoneNoTz )
                    // InternalSQLParser.g:525:7: lv_simpleType_10_0= ruleZoneNoTz
                    {

                    							newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeZoneNoTzEnumRuleCall_1_5_3_0());
                    						
                    pushFollow(FOLLOW_14);
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
                    // InternalSQLParser.g:544:4: (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) )
                    {
                    // InternalSQLParser.g:544:4: (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) )
                    // InternalSQLParser.g:545:5: otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) )
                    {
                    otherlv_11=(Token)match(input,TIMESTAMP,FOLLOW_19); 

                    					newLeafNode(otherlv_11, grammarAccess.getColumnAccess().getTIMESTAMPKeyword_1_6_0());
                    				
                    otherlv_12=(Token)match(input,WITH,FOLLOW_17); 

                    					newLeafNode(otherlv_12, grammarAccess.getColumnAccess().getWITHKeyword_1_6_1());
                    				
                    otherlv_13=(Token)match(input,TIME,FOLLOW_18); 

                    					newLeafNode(otherlv_13, grammarAccess.getColumnAccess().getTIMEKeyword_1_6_2());
                    				
                    // InternalSQLParser.g:557:5: ( (lv_simpleType_14_0= ruleZoneTz ) )
                    // InternalSQLParser.g:558:6: (lv_simpleType_14_0= ruleZoneTz )
                    {
                    // InternalSQLParser.g:558:6: (lv_simpleType_14_0= ruleZoneTz )
                    // InternalSQLParser.g:559:7: lv_simpleType_14_0= ruleZoneTz
                    {

                    							newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeZoneTzEnumRuleCall_1_6_3_0());
                    						
                    pushFollow(FOLLOW_14);
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

            // InternalSQLParser.g:578:3: ( (lv_constraint_15_0= ruleConstraint ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==CONSTRAINT||LA10_0==REFERENCES||LA10_0==GENERATED||(LA10_0>=DEFAULT && LA10_0<=FOREIGN)||LA10_0==PRIMARY||LA10_0==UNIQUE||LA10_0==CHECK||LA10_0==NULL||LA10_0==NOT) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSQLParser.g:579:4: (lv_constraint_15_0= ruleConstraint )
            	    {
            	    // InternalSQLParser.g:579:4: (lv_constraint_15_0= ruleConstraint )
            	    // InternalSQLParser.g:580:5: lv_constraint_15_0= ruleConstraint
            	    {

            	    					newCompositeNode(grammarAccess.getColumnAccess().getConstraintConstraintParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_14);
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
            	    break loop10;
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
    // InternalSQLParser.g:601:1: entryRuleVarchar returns [EObject current=null] : iv_ruleVarchar= ruleVarchar EOF ;
    public final EObject entryRuleVarchar() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarchar = null;


        try {
            // InternalSQLParser.g:601:48: (iv_ruleVarchar= ruleVarchar EOF )
            // InternalSQLParser.g:602:2: iv_ruleVarchar= ruleVarchar EOF
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
    // InternalSQLParser.g:608:1: ruleVarchar returns [EObject current=null] : ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis ) ;
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
            // InternalSQLParser.g:614:2: ( ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis ) )
            // InternalSQLParser.g:615:2: ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis )
            {
            // InternalSQLParser.g:615:2: ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis )
            // InternalSQLParser.g:616:3: (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis
            {
            // InternalSQLParser.g:616:3: (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==VARCHAR) ) {
                alt11=1;
            }
            else if ( (LA11_0==CHARACTER) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalSQLParser.g:617:4: otherlv_0= VARCHAR
                    {
                    otherlv_0=(Token)match(input,VARCHAR,FOLLOW_9); 

                    				newLeafNode(otherlv_0, grammarAccess.getVarcharAccess().getVARCHARKeyword_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:622:4: ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING )
                    {
                    // InternalSQLParser.g:622:4: ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING )
                    // InternalSQLParser.g:623:5: ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING
                    {
                    // InternalSQLParser.g:623:5: ( (lv_varying_1_0= CHARACTER ) )
                    // InternalSQLParser.g:624:6: (lv_varying_1_0= CHARACTER )
                    {
                    // InternalSQLParser.g:624:6: (lv_varying_1_0= CHARACTER )
                    // InternalSQLParser.g:625:7: lv_varying_1_0= CHARACTER
                    {
                    lv_varying_1_0=(Token)match(input,CHARACTER,FOLLOW_20); 

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

            otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_21); 

            			newLeafNode(otherlv_3, grammarAccess.getVarcharAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:647:3: ( (lv_size_4_0= RULE_INT ) )
            // InternalSQLParser.g:648:4: (lv_size_4_0= RULE_INT )
            {
            // InternalSQLParser.g:648:4: (lv_size_4_0= RULE_INT )
            // InternalSQLParser.g:649:5: lv_size_4_0= RULE_INT
            {
            lv_size_4_0=(Token)match(input,RULE_INT,FOLLOW_22); 

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
    // InternalSQLParser.g:673:1: entryRuleNumeric returns [EObject current=null] : iv_ruleNumeric= ruleNumeric EOF ;
    public final EObject entryRuleNumeric() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumeric = null;


        try {
            // InternalSQLParser.g:673:48: (iv_ruleNumeric= ruleNumeric EOF )
            // InternalSQLParser.g:674:2: iv_ruleNumeric= ruleNumeric EOF
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
    // InternalSQLParser.g:680:1: ruleNumeric returns [EObject current=null] : ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis ) ;
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
            // InternalSQLParser.g:686:2: ( ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis ) )
            // InternalSQLParser.g:687:2: ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis )
            {
            // InternalSQLParser.g:687:2: ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis )
            // InternalSQLParser.g:688:3: ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis
            {
            // InternalSQLParser.g:688:3: ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==DECIMAL) ) {
                alt12=1;
            }
            else if ( (LA12_0==NUMERIC) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // InternalSQLParser.g:689:4: ( (lv_decimal_0_0= DECIMAL ) )
                    {
                    // InternalSQLParser.g:689:4: ( (lv_decimal_0_0= DECIMAL ) )
                    // InternalSQLParser.g:690:5: (lv_decimal_0_0= DECIMAL )
                    {
                    // InternalSQLParser.g:690:5: (lv_decimal_0_0= DECIMAL )
                    // InternalSQLParser.g:691:6: lv_decimal_0_0= DECIMAL
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
                    // InternalSQLParser.g:704:4: otherlv_1= NUMERIC
                    {
                    otherlv_1=(Token)match(input,NUMERIC,FOLLOW_9); 

                    				newLeafNode(otherlv_1, grammarAccess.getNumericAccess().getNUMERICKeyword_0_1());
                    			

                    }
                    break;

            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_21); 

            			newLeafNode(otherlv_2, grammarAccess.getNumericAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:713:3: ( (lv_precision_3_0= RULE_INT ) )
            // InternalSQLParser.g:714:4: (lv_precision_3_0= RULE_INT )
            {
            // InternalSQLParser.g:714:4: (lv_precision_3_0= RULE_INT )
            // InternalSQLParser.g:715:5: lv_precision_3_0= RULE_INT
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

            // InternalSQLParser.g:731:3: (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==Comma) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalSQLParser.g:732:4: otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) )
                    {
                    otherlv_4=(Token)match(input,Comma,FOLLOW_21); 

                    				newLeafNode(otherlv_4, grammarAccess.getNumericAccess().getCommaKeyword_3_0());
                    			
                    // InternalSQLParser.g:736:4: ( (lv_scale_5_0= RULE_INT ) )
                    // InternalSQLParser.g:737:5: (lv_scale_5_0= RULE_INT )
                    {
                    // InternalSQLParser.g:737:5: (lv_scale_5_0= RULE_INT )
                    // InternalSQLParser.g:738:6: lv_scale_5_0= RULE_INT
                    {
                    lv_scale_5_0=(Token)match(input,RULE_INT,FOLLOW_22); 

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
    // InternalSQLParser.g:763:1: entryRuleChar returns [EObject current=null] : iv_ruleChar= ruleChar EOF ;
    public final EObject entryRuleChar() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleChar = null;


        try {
            // InternalSQLParser.g:763:45: (iv_ruleChar= ruleChar EOF )
            // InternalSQLParser.g:764:2: iv_ruleChar= ruleChar EOF
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
    // InternalSQLParser.g:770:1: ruleChar returns [EObject current=null] : ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis ) ;
    public final EObject ruleChar() throws RecognitionException {
        EObject current = null;

        Token lv_character_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_size_3_0=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalSQLParser.g:776:2: ( ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis ) )
            // InternalSQLParser.g:777:2: ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis )
            {
            // InternalSQLParser.g:777:2: ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis )
            // InternalSQLParser.g:778:3: ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis
            {
            // InternalSQLParser.g:778:3: ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==CHARACTER) ) {
                alt14=1;
            }
            else if ( (LA14_0==CHAR) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // InternalSQLParser.g:779:4: ( (lv_character_0_0= CHARACTER ) )
                    {
                    // InternalSQLParser.g:779:4: ( (lv_character_0_0= CHARACTER ) )
                    // InternalSQLParser.g:780:5: (lv_character_0_0= CHARACTER )
                    {
                    // InternalSQLParser.g:780:5: (lv_character_0_0= CHARACTER )
                    // InternalSQLParser.g:781:6: lv_character_0_0= CHARACTER
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
                    // InternalSQLParser.g:794:4: otherlv_1= CHAR
                    {
                    otherlv_1=(Token)match(input,CHAR,FOLLOW_9); 

                    				newLeafNode(otherlv_1, grammarAccess.getCharAccess().getCHARKeyword_0_1());
                    			

                    }
                    break;

            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_21); 

            			newLeafNode(otherlv_2, grammarAccess.getCharAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:803:3: ( (lv_size_3_0= RULE_INT ) )
            // InternalSQLParser.g:804:4: (lv_size_3_0= RULE_INT )
            {
            // InternalSQLParser.g:804:4: (lv_size_3_0= RULE_INT )
            // InternalSQLParser.g:805:5: lv_size_3_0= RULE_INT
            {
            lv_size_3_0=(Token)match(input,RULE_INT,FOLLOW_22); 

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
    // InternalSQLParser.g:830:1: ruleConstraintName[EObject in_current] returns [EObject current=in_current] : (otherlv_0= CONSTRAINT ( (lv_name_1_0= ruleName ) ) )? ;
    public final EObject ruleConstraintName(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:836:2: ( (otherlv_0= CONSTRAINT ( (lv_name_1_0= ruleName ) ) )? )
            // InternalSQLParser.g:837:2: (otherlv_0= CONSTRAINT ( (lv_name_1_0= ruleName ) ) )?
            {
            // InternalSQLParser.g:837:2: (otherlv_0= CONSTRAINT ( (lv_name_1_0= ruleName ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==CONSTRAINT) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalSQLParser.g:838:3: otherlv_0= CONSTRAINT ( (lv_name_1_0= ruleName ) )
                    {
                    otherlv_0=(Token)match(input,CONSTRAINT,FOLLOW_5); 

                    			newLeafNode(otherlv_0, grammarAccess.getConstraintNameAccess().getCONSTRAINTKeyword_0());
                    		
                    // InternalSQLParser.g:842:3: ( (lv_name_1_0= ruleName ) )
                    // InternalSQLParser.g:843:4: (lv_name_1_0= ruleName )
                    {
                    // InternalSQLParser.g:843:4: (lv_name_1_0= ruleName )
                    // InternalSQLParser.g:844:5: lv_name_1_0= ruleName
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
    // InternalSQLParser.g:865:1: entryRuleConstraint returns [EObject current=null] : iv_ruleConstraint= ruleConstraint EOF ;
    public final EObject entryRuleConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstraint = null;


        try {
            // InternalSQLParser.g:865:51: (iv_ruleConstraint= ruleConstraint EOF )
            // InternalSQLParser.g:866:2: iv_ruleConstraint= ruleConstraint EOF
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
    // InternalSQLParser.g:872:1: ruleConstraint returns [EObject current=null] : (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull | this_Nullable_3= ruleNullable | this_ColumnReferences_4= ruleColumnReferences | this_ColumnRaw_5= ruleColumnRaw ) ;
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
            // InternalSQLParser.g:878:2: ( (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull | this_Nullable_3= ruleNullable | this_ColumnReferences_4= ruleColumnReferences | this_ColumnRaw_5= ruleColumnRaw ) )
            // InternalSQLParser.g:879:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull | this_Nullable_3= ruleNullable | this_ColumnReferences_4= ruleColumnReferences | this_ColumnRaw_5= ruleColumnRaw )
            {
            // InternalSQLParser.g:879:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull | this_Nullable_3= ruleNullable | this_ColumnReferences_4= ruleColumnReferences | this_ColumnRaw_5= ruleColumnRaw )
            int alt16=6;
            alt16 = dfa16.predict(input);
            switch (alt16) {
                case 1 :
                    // InternalSQLParser.g:880:3: this_PrimaryAnnotation_0= rulePrimaryAnnotation
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
                    // InternalSQLParser.g:889:3: this_UniqueAnnotation_1= ruleUniqueAnnotation
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
                    // InternalSQLParser.g:898:3: this_NotNull_2= ruleNotNull
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
                    // InternalSQLParser.g:907:3: this_Nullable_3= ruleNullable
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
                    // InternalSQLParser.g:916:3: this_ColumnReferences_4= ruleColumnReferences
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
                    // InternalSQLParser.g:925:3: this_ColumnRaw_5= ruleColumnRaw
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
    // InternalSQLParser.g:937:1: entryRulePrimaryAnnotation returns [EObject current=null] : iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF ;
    public final EObject entryRulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryAnnotation = null;


        try {
            // InternalSQLParser.g:937:58: (iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF )
            // InternalSQLParser.g:938:2: iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF
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
    // InternalSQLParser.g:944:1: rulePrimaryAnnotation returns [EObject current=null] : ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= PRIMARY otherlv_3= KEY ) ;
    public final EObject rulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject this_ConstraintName_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:950:2: ( ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= PRIMARY otherlv_3= KEY ) )
            // InternalSQLParser.g:951:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= PRIMARY otherlv_3= KEY )
            {
            // InternalSQLParser.g:951:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= PRIMARY otherlv_3= KEY )
            // InternalSQLParser.g:952:3: () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= PRIMARY otherlv_3= KEY
            {
            // InternalSQLParser.g:952:3: ()
            // InternalSQLParser.g:953:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getPrimaryAnnotationAccess().getPrimaryAnnotationAction_0(),
            					current);
            			

            }


            			if (current==null) {
            				current = createModelElement(grammarAccess.getPrimaryAnnotationRule());
            			}
            			newCompositeNode(grammarAccess.getPrimaryAnnotationAccess().getConstraintNameParserRuleCall_1());
            		
            pushFollow(FOLLOW_23);
            this_ConstraintName_1=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_1;
            			afterParserOrEnumRuleCall();
            		
            otherlv_2=(Token)match(input,PRIMARY,FOLLOW_24); 

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
    // InternalSQLParser.g:982:1: entryRuleUniqueAnnotation returns [EObject current=null] : iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF ;
    public final EObject entryRuleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueAnnotation = null;


        try {
            // InternalSQLParser.g:982:57: (iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF )
            // InternalSQLParser.g:983:2: iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF
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
    // InternalSQLParser.g:989:1: ruleUniqueAnnotation returns [EObject current=null] : ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= UNIQUE ) ;
    public final EObject ruleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_ConstraintName_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:995:2: ( ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= UNIQUE ) )
            // InternalSQLParser.g:996:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= UNIQUE )
            {
            // InternalSQLParser.g:996:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= UNIQUE )
            // InternalSQLParser.g:997:3: () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= UNIQUE
            {
            // InternalSQLParser.g:997:3: ()
            // InternalSQLParser.g:998:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getUniqueAnnotationAccess().getUniqueAnnotationAction_0(),
            					current);
            			

            }


            			if (current==null) {
            				current = createModelElement(grammarAccess.getUniqueAnnotationRule());
            			}
            			newCompositeNode(grammarAccess.getUniqueAnnotationAccess().getConstraintNameParserRuleCall_1());
            		
            pushFollow(FOLLOW_25);
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
    // InternalSQLParser.g:1023:1: entryRuleNotNull returns [EObject current=null] : iv_ruleNotNull= ruleNotNull EOF ;
    public final EObject entryRuleNotNull() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNotNull = null;


        try {
            // InternalSQLParser.g:1023:48: (iv_ruleNotNull= ruleNotNull EOF )
            // InternalSQLParser.g:1024:2: iv_ruleNotNull= ruleNotNull EOF
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
    // InternalSQLParser.g:1030:1: ruleNotNull returns [EObject current=null] : ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NOT otherlv_3= NULL ) ;
    public final EObject ruleNotNull() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject this_ConstraintName_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1036:2: ( ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NOT otherlv_3= NULL ) )
            // InternalSQLParser.g:1037:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NOT otherlv_3= NULL )
            {
            // InternalSQLParser.g:1037:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NOT otherlv_3= NULL )
            // InternalSQLParser.g:1038:3: () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NOT otherlv_3= NULL
            {
            // InternalSQLParser.g:1038:3: ()
            // InternalSQLParser.g:1039:4: 
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
            		
            otherlv_2=(Token)match(input,NOT,FOLLOW_26); 

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
    // InternalSQLParser.g:1068:1: entryRuleNullable returns [EObject current=null] : iv_ruleNullable= ruleNullable EOF ;
    public final EObject entryRuleNullable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNullable = null;


        try {
            // InternalSQLParser.g:1068:49: (iv_ruleNullable= ruleNullable EOF )
            // InternalSQLParser.g:1069:2: iv_ruleNullable= ruleNullable EOF
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
    // InternalSQLParser.g:1075:1: ruleNullable returns [EObject current=null] : ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NULL ) ;
    public final EObject ruleNullable() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject this_ConstraintName_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1081:2: ( ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NULL ) )
            // InternalSQLParser.g:1082:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NULL )
            {
            // InternalSQLParser.g:1082:2: ( () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NULL )
            // InternalSQLParser.g:1083:3: () this_ConstraintName_1= ruleConstraintName[$current] otherlv_2= NULL
            {
            // InternalSQLParser.g:1083:3: ()
            // InternalSQLParser.g:1084:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getNullableAccess().getNullableAction_0(),
            					current);
            			

            }


            			if (current==null) {
            				current = createModelElement(grammarAccess.getNullableRule());
            			}
            			newCompositeNode(grammarAccess.getNullableAccess().getConstraintNameParserRuleCall_1());
            		
            pushFollow(FOLLOW_26);
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
    // InternalSQLParser.g:1109:1: entryRuleColumnReferences returns [EObject current=null] : iv_ruleColumnReferences= ruleColumnReferences EOF ;
    public final EObject entryRuleColumnReferences() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumnReferences = null;


        try {
            // InternalSQLParser.g:1109:57: (iv_ruleColumnReferences= ruleColumnReferences EOF )
            // InternalSQLParser.g:1110:2: iv_ruleColumnReferences= ruleColumnReferences EOF
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
    // InternalSQLParser.g:1116:1: ruleColumnReferences returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= REFERENCES this_References_2= ruleReferences[$current] ) ;
    public final EObject ruleColumnReferences() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject this_ConstraintName_0 = null;

        EObject this_References_2 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1122:2: ( (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= REFERENCES this_References_2= ruleReferences[$current] ) )
            // InternalSQLParser.g:1123:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= REFERENCES this_References_2= ruleReferences[$current] )
            {
            // InternalSQLParser.g:1123:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= REFERENCES this_References_2= ruleReferences[$current] )
            // InternalSQLParser.g:1124:3: this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= REFERENCES this_References_2= ruleReferences[$current]
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getColumnReferencesRule());
            			}
            			newCompositeNode(grammarAccess.getColumnReferencesAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_27);
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
    // InternalSQLParser.g:1154:1: entryRuleColumnRaw returns [EObject current=null] : iv_ruleColumnRaw= ruleColumnRaw EOF ;
    public final EObject entryRuleColumnRaw() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumnRaw = null;


        try {
            // InternalSQLParser.g:1154:50: (iv_ruleColumnRaw= ruleColumnRaw EOF )
            // InternalSQLParser.g:1155:2: iv_ruleColumnRaw= ruleColumnRaw EOF
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
    // InternalSQLParser.g:1161:1: ruleColumnRaw returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) ) ) ;
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
            // InternalSQLParser.g:1167:2: ( (this_ConstraintName_0= ruleConstraintName[$current] ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) ) ) )
            // InternalSQLParser.g:1168:2: (this_ConstraintName_0= ruleConstraintName[$current] ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) ) )
            {
            // InternalSQLParser.g:1168:2: (this_ConstraintName_0= ruleConstraintName[$current] ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) ) )
            // InternalSQLParser.g:1169:3: this_ConstraintName_0= ruleConstraintName[$current] ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) )
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getColumnRawRule());
            			}
            			newCompositeNode(grammarAccess.getColumnRawAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_28);
            this_ConstraintName_0=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_0;
            			afterParserOrEnumRuleCall();
            		
            // InternalSQLParser.g:1180:3: ( ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) ) | ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) ) | ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) ) )
            int alt17=3;
            switch ( input.LA(1) ) {
            case DEFAULT:
                {
                alt17=1;
                }
                break;
            case CHECK:
                {
                alt17=2;
                }
                break;
            case GENERATED:
                {
                alt17=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // InternalSQLParser.g:1181:4: ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) )
                    {
                    // InternalSQLParser.g:1181:4: ( ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) ) )
                    // InternalSQLParser.g:1182:5: ( (lv_kind_1_0= ruleDefaultKind ) ) ( (lv_text_2_0= ruleDefaultExpr ) )
                    {
                    // InternalSQLParser.g:1182:5: ( (lv_kind_1_0= ruleDefaultKind ) )
                    // InternalSQLParser.g:1183:6: (lv_kind_1_0= ruleDefaultKind )
                    {
                    // InternalSQLParser.g:1183:6: (lv_kind_1_0= ruleDefaultKind )
                    // InternalSQLParser.g:1184:7: lv_kind_1_0= ruleDefaultKind
                    {

                    							newCompositeNode(grammarAccess.getColumnRawAccess().getKindDefaultKindEnumRuleCall_1_0_0_0());
                    						
                    pushFollow(FOLLOW_29);
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

                    // InternalSQLParser.g:1201:5: ( (lv_text_2_0= ruleDefaultExpr ) )
                    // InternalSQLParser.g:1202:6: (lv_text_2_0= ruleDefaultExpr )
                    {
                    // InternalSQLParser.g:1202:6: (lv_text_2_0= ruleDefaultExpr )
                    // InternalSQLParser.g:1203:7: lv_text_2_0= ruleDefaultExpr
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
                    // InternalSQLParser.g:1222:4: ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) )
                    {
                    // InternalSQLParser.g:1222:4: ( ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) ) )
                    // InternalSQLParser.g:1223:5: ( (lv_kind_3_0= ruleCheckKind ) ) ( (lv_text_4_0= ruleParenthesized ) )
                    {
                    // InternalSQLParser.g:1223:5: ( (lv_kind_3_0= ruleCheckKind ) )
                    // InternalSQLParser.g:1224:6: (lv_kind_3_0= ruleCheckKind )
                    {
                    // InternalSQLParser.g:1224:6: (lv_kind_3_0= ruleCheckKind )
                    // InternalSQLParser.g:1225:7: lv_kind_3_0= ruleCheckKind
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

                    // InternalSQLParser.g:1242:5: ( (lv_text_4_0= ruleParenthesized ) )
                    // InternalSQLParser.g:1243:6: (lv_text_4_0= ruleParenthesized )
                    {
                    // InternalSQLParser.g:1243:6: (lv_text_4_0= ruleParenthesized )
                    // InternalSQLParser.g:1244:7: lv_text_4_0= ruleParenthesized
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
                    // InternalSQLParser.g:1263:4: ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) )
                    {
                    // InternalSQLParser.g:1263:4: ( ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) ) )
                    // InternalSQLParser.g:1264:5: ( (lv_kind_5_0= ruleGeneratedKind ) ) ( (lv_text_6_0= ruleGeneratedTail ) )
                    {
                    // InternalSQLParser.g:1264:5: ( (lv_kind_5_0= ruleGeneratedKind ) )
                    // InternalSQLParser.g:1265:6: (lv_kind_5_0= ruleGeneratedKind )
                    {
                    // InternalSQLParser.g:1265:6: (lv_kind_5_0= ruleGeneratedKind )
                    // InternalSQLParser.g:1266:7: lv_kind_5_0= ruleGeneratedKind
                    {

                    							newCompositeNode(grammarAccess.getColumnRawAccess().getKindGeneratedKindEnumRuleCall_1_2_0_0());
                    						
                    pushFollow(FOLLOW_30);
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

                    // InternalSQLParser.g:1283:5: ( (lv_text_6_0= ruleGeneratedTail ) )
                    // InternalSQLParser.g:1284:6: (lv_text_6_0= ruleGeneratedTail )
                    {
                    // InternalSQLParser.g:1284:6: (lv_text_6_0= ruleGeneratedTail )
                    // InternalSQLParser.g:1285:7: lv_text_6_0= ruleGeneratedTail
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
    // InternalSQLParser.g:1308:1: entryRuleTableConstraint returns [EObject current=null] : iv_ruleTableConstraint= ruleTableConstraint EOF ;
    public final EObject entryRuleTableConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableConstraint = null;


        try {
            // InternalSQLParser.g:1308:56: (iv_ruleTableConstraint= ruleTableConstraint EOF )
            // InternalSQLParser.g:1309:2: iv_ruleTableConstraint= ruleTableConstraint EOF
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
    // InternalSQLParser.g:1315:1: ruleTableConstraint returns [EObject current=null] : (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey | this_ForeignKey_2= ruleForeignKey | this_TableCheck_3= ruleTableCheck ) ;
    public final EObject ruleTableConstraint() throws RecognitionException {
        EObject current = null;

        EObject this_PrimaryKey_0 = null;

        EObject this_UniqueKey_1 = null;

        EObject this_ForeignKey_2 = null;

        EObject this_TableCheck_3 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1321:2: ( (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey | this_ForeignKey_2= ruleForeignKey | this_TableCheck_3= ruleTableCheck ) )
            // InternalSQLParser.g:1322:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey | this_ForeignKey_2= ruleForeignKey | this_TableCheck_3= ruleTableCheck )
            {
            // InternalSQLParser.g:1322:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey | this_ForeignKey_2= ruleForeignKey | this_TableCheck_3= ruleTableCheck )
            int alt18=4;
            alt18 = dfa18.predict(input);
            switch (alt18) {
                case 1 :
                    // InternalSQLParser.g:1323:3: this_PrimaryKey_0= rulePrimaryKey
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
                    // InternalSQLParser.g:1332:3: this_UniqueKey_1= ruleUniqueKey
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
                    // InternalSQLParser.g:1341:3: this_ForeignKey_2= ruleForeignKey
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
                    // InternalSQLParser.g:1350:3: this_TableCheck_3= ruleTableCheck
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
    // InternalSQLParser.g:1363:1: ruleKeyColumns[EObject in_current] returns [EObject current=in_current] : (otherlv_0= LeftParenthesis ( ( ruleName ) ) (otherlv_2= Comma ( ( ruleName ) ) )* otherlv_4= RightParenthesis ) ;
    public final EObject ruleKeyColumns(EObject in_current) throws RecognitionException {
        EObject current = in_current;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalSQLParser.g:1369:2: ( (otherlv_0= LeftParenthesis ( ( ruleName ) ) (otherlv_2= Comma ( ( ruleName ) ) )* otherlv_4= RightParenthesis ) )
            // InternalSQLParser.g:1370:2: (otherlv_0= LeftParenthesis ( ( ruleName ) ) (otherlv_2= Comma ( ( ruleName ) ) )* otherlv_4= RightParenthesis )
            {
            // InternalSQLParser.g:1370:2: (otherlv_0= LeftParenthesis ( ( ruleName ) ) (otherlv_2= Comma ( ( ruleName ) ) )* otherlv_4= RightParenthesis )
            // InternalSQLParser.g:1371:3: otherlv_0= LeftParenthesis ( ( ruleName ) ) (otherlv_2= Comma ( ( ruleName ) ) )* otherlv_4= RightParenthesis
            {
            otherlv_0=(Token)match(input,LeftParenthesis,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getKeyColumnsAccess().getLeftParenthesisKeyword_0());
            		
            // InternalSQLParser.g:1375:3: ( ( ruleName ) )
            // InternalSQLParser.g:1376:4: ( ruleName )
            {
            // InternalSQLParser.g:1376:4: ( ruleName )
            // InternalSQLParser.g:1377:5: ruleName
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

            // InternalSQLParser.g:1391:3: (otherlv_2= Comma ( ( ruleName ) ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==Comma) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalSQLParser.g:1392:4: otherlv_2= Comma ( ( ruleName ) )
            	    {
            	    otherlv_2=(Token)match(input,Comma,FOLLOW_5); 

            	    				newLeafNode(otherlv_2, grammarAccess.getKeyColumnsAccess().getCommaKeyword_2_0());
            	    			
            	    // InternalSQLParser.g:1396:4: ( ( ruleName ) )
            	    // InternalSQLParser.g:1397:5: ( ruleName )
            	    {
            	    // InternalSQLParser.g:1397:5: ( ruleName )
            	    // InternalSQLParser.g:1398:6: ruleName
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
            	    break loop19;
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
    // InternalSQLParser.g:1421:1: entryRulePrimaryKey returns [EObject current=null] : iv_rulePrimaryKey= rulePrimaryKey EOF ;
    public final EObject entryRulePrimaryKey() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryKey = null;


        try {
            // InternalSQLParser.g:1421:51: (iv_rulePrimaryKey= rulePrimaryKey EOF )
            // InternalSQLParser.g:1422:2: iv_rulePrimaryKey= rulePrimaryKey EOF
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
    // InternalSQLParser.g:1428:1: rulePrimaryKey returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= PRIMARY otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] ) ;
    public final EObject rulePrimaryKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;
        EObject this_ConstraintName_0 = null;

        EObject this_KeyColumns_3 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1434:2: ( (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= PRIMARY otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] ) )
            // InternalSQLParser.g:1435:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= PRIMARY otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] )
            {
            // InternalSQLParser.g:1435:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= PRIMARY otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] )
            // InternalSQLParser.g:1436:3: this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= PRIMARY otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current]
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getPrimaryKeyRule());
            			}
            			newCompositeNode(grammarAccess.getPrimaryKeyAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_23);
            this_ConstraintName_0=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_0;
            			afterParserOrEnumRuleCall();
            		
            otherlv_1=(Token)match(input,PRIMARY,FOLLOW_24); 

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
    // InternalSQLParser.g:1470:1: entryRuleUniqueKey returns [EObject current=null] : iv_ruleUniqueKey= ruleUniqueKey EOF ;
    public final EObject entryRuleUniqueKey() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueKey = null;


        try {
            // InternalSQLParser.g:1470:50: (iv_ruleUniqueKey= ruleUniqueKey EOF )
            // InternalSQLParser.g:1471:2: iv_ruleUniqueKey= ruleUniqueKey EOF
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
    // InternalSQLParser.g:1477:1: ruleUniqueKey returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= UNIQUE this_KeyColumns_2= ruleKeyColumns[$current] ) ;
    public final EObject ruleUniqueKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject this_ConstraintName_0 = null;

        EObject this_KeyColumns_2 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1483:2: ( (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= UNIQUE this_KeyColumns_2= ruleKeyColumns[$current] ) )
            // InternalSQLParser.g:1484:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= UNIQUE this_KeyColumns_2= ruleKeyColumns[$current] )
            {
            // InternalSQLParser.g:1484:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= UNIQUE this_KeyColumns_2= ruleKeyColumns[$current] )
            // InternalSQLParser.g:1485:3: this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= UNIQUE this_KeyColumns_2= ruleKeyColumns[$current]
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getUniqueKeyRule());
            			}
            			newCompositeNode(grammarAccess.getUniqueKeyAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_25);
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
    // InternalSQLParser.g:1515:1: entryRuleForeignKey returns [EObject current=null] : iv_ruleForeignKey= ruleForeignKey EOF ;
    public final EObject entryRuleForeignKey() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleForeignKey = null;


        try {
            // InternalSQLParser.g:1515:51: (iv_ruleForeignKey= ruleForeignKey EOF )
            // InternalSQLParser.g:1516:2: iv_ruleForeignKey= ruleForeignKey EOF
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
    // InternalSQLParser.g:1522:1: ruleForeignKey returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= FOREIGN otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] otherlv_4= REFERENCES this_References_5= ruleReferences[$current] ) ;
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
            // InternalSQLParser.g:1528:2: ( (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= FOREIGN otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] otherlv_4= REFERENCES this_References_5= ruleReferences[$current] ) )
            // InternalSQLParser.g:1529:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= FOREIGN otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] otherlv_4= REFERENCES this_References_5= ruleReferences[$current] )
            {
            // InternalSQLParser.g:1529:2: (this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= FOREIGN otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] otherlv_4= REFERENCES this_References_5= ruleReferences[$current] )
            // InternalSQLParser.g:1530:3: this_ConstraintName_0= ruleConstraintName[$current] otherlv_1= FOREIGN otherlv_2= KEY this_KeyColumns_3= ruleKeyColumns[$current] otherlv_4= REFERENCES this_References_5= ruleReferences[$current]
            {

            			if (current==null) {
            				current = createModelElement(grammarAccess.getForeignKeyRule());
            			}
            			newCompositeNode(grammarAccess.getForeignKeyAccess().getConstraintNameParserRuleCall_0());
            		
            pushFollow(FOLLOW_31);
            this_ConstraintName_0=ruleConstraintName(current);

            state._fsp--;


            			current = this_ConstraintName_0;
            			afterParserOrEnumRuleCall();
            		
            otherlv_1=(Token)match(input,FOREIGN,FOLLOW_24); 

            			newLeafNode(otherlv_1, grammarAccess.getForeignKeyAccess().getFOREIGNKeyword_1());
            		
            otherlv_2=(Token)match(input,KEY,FOLLOW_9); 

            			newLeafNode(otherlv_2, grammarAccess.getForeignKeyAccess().getKEYKeyword_2());
            		

            			if (current==null) {
            				current = createModelElement(grammarAccess.getForeignKeyRule());
            			}
            			newCompositeNode(grammarAccess.getForeignKeyAccess().getKeyColumnsParserRuleCall_3());
            		
            pushFollow(FOLLOW_27);
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
    // InternalSQLParser.g:1579:1: entryRuleTableCheck returns [EObject current=null] : iv_ruleTableCheck= ruleTableCheck EOF ;
    public final EObject entryRuleTableCheck() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableCheck = null;


        try {
            // InternalSQLParser.g:1579:51: (iv_ruleTableCheck= ruleTableCheck EOF )
            // InternalSQLParser.g:1580:2: iv_ruleTableCheck= ruleTableCheck EOF
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
    // InternalSQLParser.g:1586:1: ruleTableCheck returns [EObject current=null] : (this_ConstraintName_0= ruleConstraintName[$current] ( (lv_check_1_0= ruleCheckClause ) ) ) ;
    public final EObject ruleTableCheck() throws RecognitionException {
        EObject current = null;

        EObject this_ConstraintName_0 = null;

        EObject lv_check_1_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1592:2: ( (this_ConstraintName_0= ruleConstraintName[$current] ( (lv_check_1_0= ruleCheckClause ) ) ) )
            // InternalSQLParser.g:1593:2: (this_ConstraintName_0= ruleConstraintName[$current] ( (lv_check_1_0= ruleCheckClause ) ) )
            {
            // InternalSQLParser.g:1593:2: (this_ConstraintName_0= ruleConstraintName[$current] ( (lv_check_1_0= ruleCheckClause ) ) )
            // InternalSQLParser.g:1594:3: this_ConstraintName_0= ruleConstraintName[$current] ( (lv_check_1_0= ruleCheckClause ) )
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
            		
            // InternalSQLParser.g:1605:3: ( (lv_check_1_0= ruleCheckClause ) )
            // InternalSQLParser.g:1606:4: (lv_check_1_0= ruleCheckClause )
            {
            // InternalSQLParser.g:1606:4: (lv_check_1_0= ruleCheckClause )
            // InternalSQLParser.g:1607:5: lv_check_1_0= ruleCheckClause
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
    // InternalSQLParser.g:1628:1: entryRuleCheckClause returns [EObject current=null] : iv_ruleCheckClause= ruleCheckClause EOF ;
    public final EObject entryRuleCheckClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCheckClause = null;


        try {
            // InternalSQLParser.g:1628:52: (iv_ruleCheckClause= ruleCheckClause EOF )
            // InternalSQLParser.g:1629:2: iv_ruleCheckClause= ruleCheckClause EOF
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
    // InternalSQLParser.g:1635:1: ruleCheckClause returns [EObject current=null] : ( ( (lv_kind_0_0= ruleCheckKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) ) ;
    public final EObject ruleCheckClause() throws RecognitionException {
        EObject current = null;

        Enumerator lv_kind_0_0 = null;

        AntlrDatatypeRuleToken lv_text_1_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:1641:2: ( ( ( (lv_kind_0_0= ruleCheckKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) ) )
            // InternalSQLParser.g:1642:2: ( ( (lv_kind_0_0= ruleCheckKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) )
            {
            // InternalSQLParser.g:1642:2: ( ( (lv_kind_0_0= ruleCheckKind ) ) ( (lv_text_1_0= ruleParenthesized ) ) )
            // InternalSQLParser.g:1643:3: ( (lv_kind_0_0= ruleCheckKind ) ) ( (lv_text_1_0= ruleParenthesized ) )
            {
            // InternalSQLParser.g:1643:3: ( (lv_kind_0_0= ruleCheckKind ) )
            // InternalSQLParser.g:1644:4: (lv_kind_0_0= ruleCheckKind )
            {
            // InternalSQLParser.g:1644:4: (lv_kind_0_0= ruleCheckKind )
            // InternalSQLParser.g:1645:5: lv_kind_0_0= ruleCheckKind
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

            // InternalSQLParser.g:1662:3: ( (lv_text_1_0= ruleParenthesized ) )
            // InternalSQLParser.g:1663:4: (lv_text_1_0= ruleParenthesized )
            {
            // InternalSQLParser.g:1663:4: (lv_text_1_0= ruleParenthesized )
            // InternalSQLParser.g:1664:5: lv_text_1_0= ruleParenthesized
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
    // InternalSQLParser.g:1686:1: ruleReferences[EObject in_current] returns [EObject current=in_current] : ( ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )? ( ( ruleName ) ) (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )? (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )? ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )? ( (lv_deferrable_14_0= ruleDeferrability ) )? ) ;
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
            // InternalSQLParser.g:1692:2: ( ( ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )? ( ( ruleName ) ) (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )? (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )? ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )? ( (lv_deferrable_14_0= ruleDeferrability ) )? ) )
            // InternalSQLParser.g:1693:2: ( ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )? ( ( ruleName ) ) (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )? (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )? ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )? ( (lv_deferrable_14_0= ruleDeferrability ) )? )
            {
            // InternalSQLParser.g:1693:2: ( ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )? ( ( ruleName ) ) (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )? (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )? ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )? ( (lv_deferrable_14_0= ruleDeferrability ) )? )
            // InternalSQLParser.g:1694:3: ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )? ( ( ruleName ) ) (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )? (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )? ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )? ( (lv_deferrable_14_0= ruleDeferrability ) )?
            {
            // InternalSQLParser.g:1694:3: ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )?
            int alt20=2;
            alt20 = dfa20.predict(input);
            switch (alt20) {
                case 1 :
                    // InternalSQLParser.g:1695:4: ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop
                    {
                    // InternalSQLParser.g:1695:4: ( (lv_refSchema_0_0= ruleName ) )
                    // InternalSQLParser.g:1696:5: (lv_refSchema_0_0= ruleName )
                    {
                    // InternalSQLParser.g:1696:5: (lv_refSchema_0_0= ruleName )
                    // InternalSQLParser.g:1697:6: lv_refSchema_0_0= ruleName
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

            // InternalSQLParser.g:1719:3: ( ( ruleName ) )
            // InternalSQLParser.g:1720:4: ( ruleName )
            {
            // InternalSQLParser.g:1720:4: ( ruleName )
            // InternalSQLParser.g:1721:5: ruleName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getReferencesRule());
            					}
            				

            					newCompositeNode(grammarAccess.getReferencesAccess().getRefTableTableCrossReference_1_0());
            				
            pushFollow(FOLLOW_32);
            ruleName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSQLParser.g:1735:3: (otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==LeftParenthesis) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // InternalSQLParser.g:1736:4: otherlv_3= LeftParenthesis ( ( ruleName ) ) (otherlv_5= Comma ( ( ruleName ) ) )* otherlv_7= RightParenthesis
                    {
                    otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_5); 

                    				newLeafNode(otherlv_3, grammarAccess.getReferencesAccess().getLeftParenthesisKeyword_2_0());
                    			
                    // InternalSQLParser.g:1740:4: ( ( ruleName ) )
                    // InternalSQLParser.g:1741:5: ( ruleName )
                    {
                    // InternalSQLParser.g:1741:5: ( ruleName )
                    // InternalSQLParser.g:1742:6: ruleName
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

                    // InternalSQLParser.g:1756:4: (otherlv_5= Comma ( ( ruleName ) ) )*
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==Comma) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // InternalSQLParser.g:1757:5: otherlv_5= Comma ( ( ruleName ) )
                    	    {
                    	    otherlv_5=(Token)match(input,Comma,FOLLOW_5); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getReferencesAccess().getCommaKeyword_2_2_0());
                    	    				
                    	    // InternalSQLParser.g:1761:5: ( ( ruleName ) )
                    	    // InternalSQLParser.g:1762:6: ( ruleName )
                    	    {
                    	    // InternalSQLParser.g:1762:6: ( ruleName )
                    	    // InternalSQLParser.g:1763:7: ruleName
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
                    	    break loop21;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,RightParenthesis,FOLLOW_33); 

                    				newLeafNode(otherlv_7, grammarAccess.getReferencesAccess().getRightParenthesisKeyword_2_3());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:1783:3: (otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) ) )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==MATCH) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalSQLParser.g:1784:4: otherlv_8= MATCH ( (lv_match_9_0= ruleMatchKind ) )
                    {
                    otherlv_8=(Token)match(input,MATCH,FOLLOW_34); 

                    				newLeafNode(otherlv_8, grammarAccess.getReferencesAccess().getMATCHKeyword_3_0());
                    			
                    // InternalSQLParser.g:1788:4: ( (lv_match_9_0= ruleMatchKind ) )
                    // InternalSQLParser.g:1789:5: (lv_match_9_0= ruleMatchKind )
                    {
                    // InternalSQLParser.g:1789:5: (lv_match_9_0= ruleMatchKind )
                    // InternalSQLParser.g:1790:6: lv_match_9_0= ruleMatchKind
                    {

                    						newCompositeNode(grammarAccess.getReferencesAccess().getMatchMatchKindEnumRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_35);
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

            // InternalSQLParser.g:1808:3: ( (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? ) | (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? ) )?
            int alt26=3;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==ON) ) {
                int LA26_1 = input.LA(2);

                if ( (LA26_1==DELETE) ) {
                    alt26=1;
                }
                else if ( (LA26_1==UPDATE) ) {
                    alt26=2;
                }
            }
            switch (alt26) {
                case 1 :
                    // InternalSQLParser.g:1809:4: (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? )
                    {
                    // InternalSQLParser.g:1809:4: (this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )? )
                    // InternalSQLParser.g:1810:5: this_OnDelete_10= ruleOnDelete[$current] (this_OnUpdate_11= ruleOnUpdate[$current] )?
                    {

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getReferencesRule());
                    					}
                    					newCompositeNode(grammarAccess.getReferencesAccess().getOnDeleteParserRuleCall_4_0_0());
                    				
                    pushFollow(FOLLOW_35);
                    this_OnDelete_10=ruleOnDelete(current);

                    state._fsp--;


                    					current = this_OnDelete_10;
                    					afterParserOrEnumRuleCall();
                    				
                    // InternalSQLParser.g:1821:5: (this_OnUpdate_11= ruleOnUpdate[$current] )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==ON) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // InternalSQLParser.g:1822:6: this_OnUpdate_11= ruleOnUpdate[$current]
                            {

                            						if (current==null) {
                            							current = createModelElement(grammarAccess.getReferencesRule());
                            						}
                            						newCompositeNode(grammarAccess.getReferencesAccess().getOnUpdateParserRuleCall_4_0_1());
                            					
                            pushFollow(FOLLOW_36);
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
                    // InternalSQLParser.g:1836:4: (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? )
                    {
                    // InternalSQLParser.g:1836:4: (this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )? )
                    // InternalSQLParser.g:1837:5: this_OnUpdate_12= ruleOnUpdate[$current] (this_OnDelete_13= ruleOnDelete[$current] )?
                    {

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getReferencesRule());
                    					}
                    					newCompositeNode(grammarAccess.getReferencesAccess().getOnUpdateParserRuleCall_4_1_0());
                    				
                    pushFollow(FOLLOW_35);
                    this_OnUpdate_12=ruleOnUpdate(current);

                    state._fsp--;


                    					current = this_OnUpdate_12;
                    					afterParserOrEnumRuleCall();
                    				
                    // InternalSQLParser.g:1848:5: (this_OnDelete_13= ruleOnDelete[$current] )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==ON) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // InternalSQLParser.g:1849:6: this_OnDelete_13= ruleOnDelete[$current]
                            {

                            						if (current==null) {
                            							current = createModelElement(grammarAccess.getReferencesRule());
                            						}
                            						newCompositeNode(grammarAccess.getReferencesAccess().getOnDeleteParserRuleCall_4_1_1());
                            					
                            pushFollow(FOLLOW_36);
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

            // InternalSQLParser.g:1863:3: ( (lv_deferrable_14_0= ruleDeferrability ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==NOT) ) {
                int LA27_1 = input.LA(2);

                if ( (LA27_1==DEFERRABLE) ) {
                    alt27=1;
                }
            }
            else if ( (LA27_0==DEFERRABLE||LA27_0==INITIALLY) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalSQLParser.g:1864:4: (lv_deferrable_14_0= ruleDeferrability )
                    {
                    // InternalSQLParser.g:1864:4: (lv_deferrable_14_0= ruleDeferrability )
                    // InternalSQLParser.g:1865:5: lv_deferrable_14_0= ruleDeferrability
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
    // InternalSQLParser.g:1887:1: ruleOnDelete[EObject in_current] returns [EObject current=in_current] : (otherlv_0= ON otherlv_1= DELETE ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) ) ) ;
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
            // InternalSQLParser.g:1893:2: ( (otherlv_0= ON otherlv_1= DELETE ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) ) ) )
            // InternalSQLParser.g:1894:2: (otherlv_0= ON otherlv_1= DELETE ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) ) )
            {
            // InternalSQLParser.g:1894:2: (otherlv_0= ON otherlv_1= DELETE ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) ) )
            // InternalSQLParser.g:1895:3: otherlv_0= ON otherlv_1= DELETE ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) )
            {
            otherlv_0=(Token)match(input,ON,FOLLOW_37); 

            			newLeafNode(otherlv_0, grammarAccess.getOnDeleteAccess().getONKeyword_0());
            		
            otherlv_1=(Token)match(input,DELETE,FOLLOW_38); 

            			newLeafNode(otherlv_1, grammarAccess.getOnDeleteAccess().getDELETEKeyword_1());
            		
            // InternalSQLParser.g:1903:3: ( ( (lv_onDelete_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) ) )
            int alt28=3;
            switch ( input.LA(1) ) {
            case RESTRICT:
            case CASCADE:
                {
                alt28=1;
                }
                break;
            case NO:
                {
                alt28=2;
                }
                break;
            case SET:
                {
                alt28=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // InternalSQLParser.g:1904:4: ( (lv_onDelete_2_0= ruleRefAction ) )
                    {
                    // InternalSQLParser.g:1904:4: ( (lv_onDelete_2_0= ruleRefAction ) )
                    // InternalSQLParser.g:1905:5: (lv_onDelete_2_0= ruleRefAction )
                    {
                    // InternalSQLParser.g:1905:5: (lv_onDelete_2_0= ruleRefAction )
                    // InternalSQLParser.g:1906:6: lv_onDelete_2_0= ruleRefAction
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
                    // InternalSQLParser.g:1924:4: (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) )
                    {
                    // InternalSQLParser.g:1924:4: (otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) ) )
                    // InternalSQLParser.g:1925:5: otherlv_3= NO ( (lv_onDelete_4_0= ruleNoAction ) )
                    {
                    otherlv_3=(Token)match(input,NO,FOLLOW_39); 

                    					newLeafNode(otherlv_3, grammarAccess.getOnDeleteAccess().getNOKeyword_2_1_0());
                    				
                    // InternalSQLParser.g:1929:5: ( (lv_onDelete_4_0= ruleNoAction ) )
                    // InternalSQLParser.g:1930:6: (lv_onDelete_4_0= ruleNoAction )
                    {
                    // InternalSQLParser.g:1930:6: (lv_onDelete_4_0= ruleNoAction )
                    // InternalSQLParser.g:1931:7: lv_onDelete_4_0= ruleNoAction
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
                    // InternalSQLParser.g:1950:4: (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) )
                    {
                    // InternalSQLParser.g:1950:4: (otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) ) )
                    // InternalSQLParser.g:1951:5: otherlv_5= SET ( (lv_onDelete_6_0= ruleSetAction ) )
                    {
                    otherlv_5=(Token)match(input,SET,FOLLOW_40); 

                    					newLeafNode(otherlv_5, grammarAccess.getOnDeleteAccess().getSETKeyword_2_2_0());
                    				
                    // InternalSQLParser.g:1955:5: ( (lv_onDelete_6_0= ruleSetAction ) )
                    // InternalSQLParser.g:1956:6: (lv_onDelete_6_0= ruleSetAction )
                    {
                    // InternalSQLParser.g:1956:6: (lv_onDelete_6_0= ruleSetAction )
                    // InternalSQLParser.g:1957:7: lv_onDelete_6_0= ruleSetAction
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
    // InternalSQLParser.g:1981:1: ruleOnUpdate[EObject in_current] returns [EObject current=in_current] : (otherlv_0= ON otherlv_1= UPDATE ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) ) ) ;
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
            // InternalSQLParser.g:1987:2: ( (otherlv_0= ON otherlv_1= UPDATE ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) ) ) )
            // InternalSQLParser.g:1988:2: (otherlv_0= ON otherlv_1= UPDATE ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) ) )
            {
            // InternalSQLParser.g:1988:2: (otherlv_0= ON otherlv_1= UPDATE ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) ) )
            // InternalSQLParser.g:1989:3: otherlv_0= ON otherlv_1= UPDATE ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) )
            {
            otherlv_0=(Token)match(input,ON,FOLLOW_41); 

            			newLeafNode(otherlv_0, grammarAccess.getOnUpdateAccess().getONKeyword_0());
            		
            otherlv_1=(Token)match(input,UPDATE,FOLLOW_38); 

            			newLeafNode(otherlv_1, grammarAccess.getOnUpdateAccess().getUPDATEKeyword_1());
            		
            // InternalSQLParser.g:1997:3: ( ( (lv_onUpdate_2_0= ruleRefAction ) ) | (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) ) | (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) ) )
            int alt29=3;
            switch ( input.LA(1) ) {
            case RESTRICT:
            case CASCADE:
                {
                alt29=1;
                }
                break;
            case NO:
                {
                alt29=2;
                }
                break;
            case SET:
                {
                alt29=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }

            switch (alt29) {
                case 1 :
                    // InternalSQLParser.g:1998:4: ( (lv_onUpdate_2_0= ruleRefAction ) )
                    {
                    // InternalSQLParser.g:1998:4: ( (lv_onUpdate_2_0= ruleRefAction ) )
                    // InternalSQLParser.g:1999:5: (lv_onUpdate_2_0= ruleRefAction )
                    {
                    // InternalSQLParser.g:1999:5: (lv_onUpdate_2_0= ruleRefAction )
                    // InternalSQLParser.g:2000:6: lv_onUpdate_2_0= ruleRefAction
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
                    // InternalSQLParser.g:2018:4: (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) )
                    {
                    // InternalSQLParser.g:2018:4: (otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) ) )
                    // InternalSQLParser.g:2019:5: otherlv_3= NO ( (lv_onUpdate_4_0= ruleNoAction ) )
                    {
                    otherlv_3=(Token)match(input,NO,FOLLOW_39); 

                    					newLeafNode(otherlv_3, grammarAccess.getOnUpdateAccess().getNOKeyword_2_1_0());
                    				
                    // InternalSQLParser.g:2023:5: ( (lv_onUpdate_4_0= ruleNoAction ) )
                    // InternalSQLParser.g:2024:6: (lv_onUpdate_4_0= ruleNoAction )
                    {
                    // InternalSQLParser.g:2024:6: (lv_onUpdate_4_0= ruleNoAction )
                    // InternalSQLParser.g:2025:7: lv_onUpdate_4_0= ruleNoAction
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
                    // InternalSQLParser.g:2044:4: (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) )
                    {
                    // InternalSQLParser.g:2044:4: (otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) ) )
                    // InternalSQLParser.g:2045:5: otherlv_5= SET ( (lv_onUpdate_6_0= ruleSetAction ) )
                    {
                    otherlv_5=(Token)match(input,SET,FOLLOW_40); 

                    					newLeafNode(otherlv_5, grammarAccess.getOnUpdateAccess().getSETKeyword_2_2_0());
                    				
                    // InternalSQLParser.g:2049:5: ( (lv_onUpdate_6_0= ruleSetAction ) )
                    // InternalSQLParser.g:2050:6: (lv_onUpdate_6_0= ruleSetAction )
                    {
                    // InternalSQLParser.g:2050:6: (lv_onUpdate_6_0= ruleSetAction )
                    // InternalSQLParser.g:2051:7: lv_onUpdate_6_0= ruleSetAction
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
    // InternalSQLParser.g:2074:1: entryRuleDeferrability returns [String current=null] : iv_ruleDeferrability= ruleDeferrability EOF ;
    public final String entryRuleDeferrability() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDeferrability = null;


        try {
            // InternalSQLParser.g:2074:53: (iv_ruleDeferrability= ruleDeferrability EOF )
            // InternalSQLParser.g:2075:2: iv_ruleDeferrability= ruleDeferrability EOF
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
    // InternalSQLParser.g:2081:1: ruleDeferrability returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? ) | (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? ) ) ;
    public final AntlrDatatypeRuleToken ruleDeferrability() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSQLParser.g:2087:2: ( ( ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? ) | (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? ) ) )
            // InternalSQLParser.g:2088:2: ( ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? ) | (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? ) )
            {
            // InternalSQLParser.g:2088:2: ( ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? ) | (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? ) )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==DEFERRABLE||LA36_0==NOT) ) {
                alt36=1;
            }
            else if ( (LA36_0==INITIALLY) ) {
                alt36=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // InternalSQLParser.g:2089:3: ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? )
                    {
                    // InternalSQLParser.g:2089:3: ( (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )? )
                    // InternalSQLParser.g:2090:4: (kw= NOT )? kw= DEFERRABLE (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )?
                    {
                    // InternalSQLParser.g:2090:4: (kw= NOT )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==NOT) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // InternalSQLParser.g:2091:5: kw= NOT
                            {
                            kw=(Token)match(input,NOT,FOLLOW_42); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getNOTKeyword_0_0());
                            				

                            }
                            break;

                    }

                    kw=(Token)match(input,DEFERRABLE,FOLLOW_43); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getDEFERRABLEKeyword_0_1());
                    			
                    // InternalSQLParser.g:2102:4: (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==INITIALLY) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // InternalSQLParser.g:2103:5: kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE )
                            {
                            kw=(Token)match(input,INITIALLY,FOLLOW_44); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getINITIALLYKeyword_0_2_0());
                            				
                            // InternalSQLParser.g:2108:5: (kw= DEFERRED | kw= IMMEDIATE )
                            int alt31=2;
                            int LA31_0 = input.LA(1);

                            if ( (LA31_0==DEFERRED) ) {
                                alt31=1;
                            }
                            else if ( (LA31_0==IMMEDIATE) ) {
                                alt31=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 31, 0, input);

                                throw nvae;
                            }
                            switch (alt31) {
                                case 1 :
                                    // InternalSQLParser.g:2109:6: kw= DEFERRED
                                    {
                                    kw=(Token)match(input,DEFERRED,FOLLOW_2); 

                                    						current.merge(kw);
                                    						newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getDEFERREDKeyword_0_2_1_0());
                                    					

                                    }
                                    break;
                                case 2 :
                                    // InternalSQLParser.g:2115:6: kw= IMMEDIATE
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
                    // InternalSQLParser.g:2124:3: (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? )
                    {
                    // InternalSQLParser.g:2124:3: (kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )? )
                    // InternalSQLParser.g:2125:4: kw= INITIALLY (kw= DEFERRED | kw= IMMEDIATE ) ( (kw= NOT )? kw= DEFERRABLE )?
                    {
                    kw=(Token)match(input,INITIALLY,FOLLOW_44); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getINITIALLYKeyword_1_0());
                    			
                    // InternalSQLParser.g:2130:4: (kw= DEFERRED | kw= IMMEDIATE )
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==DEFERRED) ) {
                        alt33=1;
                    }
                    else if ( (LA33_0==IMMEDIATE) ) {
                        alt33=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 33, 0, input);

                        throw nvae;
                    }
                    switch (alt33) {
                        case 1 :
                            // InternalSQLParser.g:2131:5: kw= DEFERRED
                            {
                            kw=(Token)match(input,DEFERRED,FOLLOW_45); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getDEFERREDKeyword_1_1_0());
                            				

                            }
                            break;
                        case 2 :
                            // InternalSQLParser.g:2137:5: kw= IMMEDIATE
                            {
                            kw=(Token)match(input,IMMEDIATE,FOLLOW_45); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getDeferrabilityAccess().getIMMEDIATEKeyword_1_1_1());
                            				

                            }
                            break;

                    }

                    // InternalSQLParser.g:2143:4: ( (kw= NOT )? kw= DEFERRABLE )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==NOT) ) {
                        int LA35_1 = input.LA(2);

                        if ( (LA35_1==DEFERRABLE) ) {
                            alt35=1;
                        }
                    }
                    else if ( (LA35_0==DEFERRABLE) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // InternalSQLParser.g:2144:5: (kw= NOT )? kw= DEFERRABLE
                            {
                            // InternalSQLParser.g:2144:5: (kw= NOT )?
                            int alt34=2;
                            int LA34_0 = input.LA(1);

                            if ( (LA34_0==NOT) ) {
                                alt34=1;
                            }
                            switch (alt34) {
                                case 1 :
                                    // InternalSQLParser.g:2145:6: kw= NOT
                                    {
                                    kw=(Token)match(input,NOT,FOLLOW_42); 

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
    // InternalSQLParser.g:2162:1: entryRuleGeneratedTail returns [String current=null] : iv_ruleGeneratedTail= ruleGeneratedTail EOF ;
    public final String entryRuleGeneratedTail() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleGeneratedTail = null;


        try {
            // InternalSQLParser.g:2162:53: (iv_ruleGeneratedTail= ruleGeneratedTail EOF )
            // InternalSQLParser.g:2163:2: iv_ruleGeneratedTail= ruleGeneratedTail EOF
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
    // InternalSQLParser.g:2169:1: ruleGeneratedTail returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= ALWAYS | (kw= BY kw= DEFAULT ) ) kw= AS ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) ) ) ;
    public final AntlrDatatypeRuleToken ruleGeneratedTail() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_Parenthesized_5 = null;

        AntlrDatatypeRuleToken this_Parenthesized_6 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2175:2: ( ( (kw= ALWAYS | (kw= BY kw= DEFAULT ) ) kw= AS ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) ) ) )
            // InternalSQLParser.g:2176:2: ( (kw= ALWAYS | (kw= BY kw= DEFAULT ) ) kw= AS ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) ) )
            {
            // InternalSQLParser.g:2176:2: ( (kw= ALWAYS | (kw= BY kw= DEFAULT ) ) kw= AS ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) ) )
            // InternalSQLParser.g:2177:3: (kw= ALWAYS | (kw= BY kw= DEFAULT ) ) kw= AS ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) )
            {
            // InternalSQLParser.g:2177:3: (kw= ALWAYS | (kw= BY kw= DEFAULT ) )
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==ALWAYS) ) {
                alt37=1;
            }
            else if ( (LA37_0==BY) ) {
                alt37=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }
            switch (alt37) {
                case 1 :
                    // InternalSQLParser.g:2178:4: kw= ALWAYS
                    {
                    kw=(Token)match(input,ALWAYS,FOLLOW_46); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getALWAYSKeyword_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2184:4: (kw= BY kw= DEFAULT )
                    {
                    // InternalSQLParser.g:2184:4: (kw= BY kw= DEFAULT )
                    // InternalSQLParser.g:2185:5: kw= BY kw= DEFAULT
                    {
                    kw=(Token)match(input,BY,FOLLOW_47); 

                    					current.merge(kw);
                    					newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getBYKeyword_0_1_0());
                    				
                    kw=(Token)match(input,DEFAULT,FOLLOW_46); 

                    					current.merge(kw);
                    					newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getDEFAULTKeyword_0_1_1());
                    				

                    }


                    }
                    break;

            }

            kw=(Token)match(input,AS,FOLLOW_48); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getASKeyword_1());
            		
            // InternalSQLParser.g:2202:3: ( (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? ) | (this_Parenthesized_6= ruleParenthesized kw= STORED ) )
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==IDENTITY) ) {
                alt39=1;
            }
            else if ( (LA39_0==LeftParenthesis) ) {
                alt39=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }
            switch (alt39) {
                case 1 :
                    // InternalSQLParser.g:2203:4: (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? )
                    {
                    // InternalSQLParser.g:2203:4: (kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )? )
                    // InternalSQLParser.g:2204:5: kw= IDENTITY (this_Parenthesized_5= ruleParenthesized )?
                    {
                    kw=(Token)match(input,IDENTITY,FOLLOW_49); 

                    					current.merge(kw);
                    					newLeafNode(kw, grammarAccess.getGeneratedTailAccess().getIDENTITYKeyword_2_0_0());
                    				
                    // InternalSQLParser.g:2209:5: (this_Parenthesized_5= ruleParenthesized )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==LeftParenthesis) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // InternalSQLParser.g:2210:6: this_Parenthesized_5= ruleParenthesized
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
                    // InternalSQLParser.g:2223:4: (this_Parenthesized_6= ruleParenthesized kw= STORED )
                    {
                    // InternalSQLParser.g:2223:4: (this_Parenthesized_6= ruleParenthesized kw= STORED )
                    // InternalSQLParser.g:2224:5: this_Parenthesized_6= ruleParenthesized kw= STORED
                    {

                    					newCompositeNode(grammarAccess.getGeneratedTailAccess().getParenthesizedParserRuleCall_2_1_0());
                    				
                    pushFollow(FOLLOW_50);
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
    // InternalSQLParser.g:2245:1: entryRuleDefaultExpr returns [String current=null] : iv_ruleDefaultExpr= ruleDefaultExpr EOF ;
    public final String entryRuleDefaultExpr() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDefaultExpr = null;


        try {
            // InternalSQLParser.g:2245:51: (iv_ruleDefaultExpr= ruleDefaultExpr EOF )
            // InternalSQLParser.g:2246:2: iv_ruleDefaultExpr= ruleDefaultExpr EOF
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
    // InternalSQLParser.g:2252:1: ruleDefaultExpr returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (this_ExprToken_0= ruleExprToken | kw= NULL ) (this_ExprToken_2= ruleExprToken )* ) ;
    public final AntlrDatatypeRuleToken ruleDefaultExpr() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ExprToken_0 = null;

        AntlrDatatypeRuleToken this_ExprToken_2 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2258:2: ( ( (this_ExprToken_0= ruleExprToken | kw= NULL ) (this_ExprToken_2= ruleExprToken )* ) )
            // InternalSQLParser.g:2259:2: ( (this_ExprToken_0= ruleExprToken | kw= NULL ) (this_ExprToken_2= ruleExprToken )* )
            {
            // InternalSQLParser.g:2259:2: ( (this_ExprToken_0= ruleExprToken | kw= NULL ) (this_ExprToken_2= ruleExprToken )* )
            // InternalSQLParser.g:2260:3: (this_ExprToken_0= ruleExprToken | kw= NULL ) (this_ExprToken_2= ruleExprToken )*
            {
            // InternalSQLParser.g:2260:3: (this_ExprToken_0= ruleExprToken | kw= NULL )
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==TIMESTAMPTZ||(LA40_0>=BIGSERIAL && LA40_0<=CHARACTER)||LA40_0==IMMEDIATE||(LA40_0>=PRECISION && LA40_0<=DECIMAL)||(LA40_0>=INTEGER && LA40_0<=NUMERIC)||(LA40_0>=VARCHAR && LA40_0<=BIGINT)||(LA40_0>=DELETE && LA40_0<=STORED)||(LA40_0>=UPDATE && LA40_0<=BYTEA)||(LA40_0>=FLOAT && LA40_0<=MATCH)||(LA40_0>=CHAR && LA40_0<=DATE)||(LA40_0>=REAL && LA40_0<=KEY)||LA40_0==SET||(LA40_0>=BY && LA40_0<=NO)||LA40_0==LeftParenthesis||LA40_0==FullStop||(LA40_0>=RULE_ID && LA40_0<=RULE_INT)||LA40_0==RULE_ANY_OTHER) ) {
                alt40=1;
            }
            else if ( (LA40_0==NULL) ) {
                alt40=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }
            switch (alt40) {
                case 1 :
                    // InternalSQLParser.g:2261:4: this_ExprToken_0= ruleExprToken
                    {

                    				newCompositeNode(grammarAccess.getDefaultExprAccess().getExprTokenParserRuleCall_0_0());
                    			
                    pushFollow(FOLLOW_51);
                    this_ExprToken_0=ruleExprToken();

                    state._fsp--;


                    				current.merge(this_ExprToken_0);
                    			

                    				afterParserOrEnumRuleCall();
                    			

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2272:4: kw= NULL
                    {
                    kw=(Token)match(input,NULL,FOLLOW_51); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDefaultExprAccess().getNULLKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalSQLParser.g:2278:3: (this_ExprToken_2= ruleExprToken )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==TIMESTAMPTZ||(LA41_0>=BIGSERIAL && LA41_0<=CHARACTER)||LA41_0==IMMEDIATE||(LA41_0>=PRECISION && LA41_0<=DECIMAL)||(LA41_0>=INTEGER && LA41_0<=NUMERIC)||(LA41_0>=VARCHAR && LA41_0<=BIGINT)||(LA41_0>=DELETE && LA41_0<=STORED)||(LA41_0>=UPDATE && LA41_0<=BYTEA)||(LA41_0>=FLOAT && LA41_0<=MATCH)||(LA41_0>=CHAR && LA41_0<=DATE)||(LA41_0>=REAL && LA41_0<=KEY)||LA41_0==SET||(LA41_0>=BY && LA41_0<=NO)||LA41_0==LeftParenthesis||LA41_0==FullStop||(LA41_0>=RULE_ID && LA41_0<=RULE_INT)||LA41_0==RULE_ANY_OTHER) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // InternalSQLParser.g:2279:4: this_ExprToken_2= ruleExprToken
            	    {

            	    				newCompositeNode(grammarAccess.getDefaultExprAccess().getExprTokenParserRuleCall_1());
            	    			
            	    pushFollow(FOLLOW_51);
            	    this_ExprToken_2=ruleExprToken();

            	    state._fsp--;


            	    				current.merge(this_ExprToken_2);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;

            	default :
            	    break loop41;
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
    // InternalSQLParser.g:2294:1: entryRuleExprToken returns [String current=null] : iv_ruleExprToken= ruleExprToken EOF ;
    public final String entryRuleExprToken() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleExprToken = null;


        try {
            // InternalSQLParser.g:2294:49: (iv_ruleExprToken= ruleExprToken EOF )
            // InternalSQLParser.g:2295:2: iv_ruleExprToken= ruleExprToken EOF
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
    // InternalSQLParser.g:2301:1: ruleExprToken returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ExprName_0= ruleExprName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= WITH | this_Parenthesized_6= ruleParenthesized ) ;
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
            // InternalSQLParser.g:2307:2: ( (this_ExprName_0= ruleExprName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= WITH | this_Parenthesized_6= ruleParenthesized ) )
            // InternalSQLParser.g:2308:2: (this_ExprName_0= ruleExprName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= WITH | this_Parenthesized_6= ruleParenthesized )
            {
            // InternalSQLParser.g:2308:2: (this_ExprName_0= ruleExprName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= WITH | this_Parenthesized_6= ruleParenthesized )
            int alt42=7;
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
            case SERIAL:
            case SIMPLE:
            case STORED:
            case UPDATE:
            case BYTEA:
            case FLOAT:
            case JSONB:
            case MATCH:
            case CHAR:
            case DATE:
            case REAL:
            case TEXT:
            case TIME:
            case UUID:
            case ZONE:
            case INT:
            case KEY:
            case SET:
            case BY:
            case IF:
            case NO:
            case RULE_ID:
            case RULE_QUOTED_ID:
                {
                alt42=1;
                }
                break;
            case RULE_STRING:
                {
                alt42=2;
                }
                break;
            case RULE_INT:
                {
                alt42=3;
                }
                break;
            case RULE_ANY_OTHER:
                {
                alt42=4;
                }
                break;
            case FullStop:
                {
                alt42=5;
                }
                break;
            case WITH:
                {
                alt42=6;
                }
                break;
            case LeftParenthesis:
                {
                alt42=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }

            switch (alt42) {
                case 1 :
                    // InternalSQLParser.g:2309:3: this_ExprName_0= ruleExprName
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
                    // InternalSQLParser.g:2320:3: this_STRING_1= RULE_STRING
                    {
                    this_STRING_1=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    			current.merge(this_STRING_1);
                    		

                    			newLeafNode(this_STRING_1, grammarAccess.getExprTokenAccess().getSTRINGTerminalRuleCall_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:2328:3: this_INT_2= RULE_INT
                    {
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_2); 

                    			current.merge(this_INT_2);
                    		

                    			newLeafNode(this_INT_2, grammarAccess.getExprTokenAccess().getINTTerminalRuleCall_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:2336:3: this_ANY_OTHER_3= RULE_ANY_OTHER
                    {
                    this_ANY_OTHER_3=(Token)match(input,RULE_ANY_OTHER,FOLLOW_2); 

                    			current.merge(this_ANY_OTHER_3);
                    		

                    			newLeafNode(this_ANY_OTHER_3, grammarAccess.getExprTokenAccess().getANY_OTHERTerminalRuleCall_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:2344:3: kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprTokenAccess().getFullStopKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:2350:3: kw= WITH
                    {
                    kw=(Token)match(input,WITH,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprTokenAccess().getWITHKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:2356:3: this_Parenthesized_6= ruleParenthesized
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
    // InternalSQLParser.g:2370:1: entryRuleParenthesized returns [String current=null] : iv_ruleParenthesized= ruleParenthesized EOF ;
    public final String entryRuleParenthesized() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleParenthesized = null;


        try {
            // InternalSQLParser.g:2370:53: (iv_ruleParenthesized= ruleParenthesized EOF )
            // InternalSQLParser.g:2371:2: iv_ruleParenthesized= ruleParenthesized EOF
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
    // InternalSQLParser.g:2377:1: ruleParenthesized returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= LeftParenthesis (this_InnerToken_1= ruleInnerToken )* kw= RightParenthesis ) ;
    public final AntlrDatatypeRuleToken ruleParenthesized() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_InnerToken_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2383:2: ( (kw= LeftParenthesis (this_InnerToken_1= ruleInnerToken )* kw= RightParenthesis ) )
            // InternalSQLParser.g:2384:2: (kw= LeftParenthesis (this_InnerToken_1= ruleInnerToken )* kw= RightParenthesis )
            {
            // InternalSQLParser.g:2384:2: (kw= LeftParenthesis (this_InnerToken_1= ruleInnerToken )* kw= RightParenthesis )
            // InternalSQLParser.g:2385:3: kw= LeftParenthesis (this_InnerToken_1= ruleInnerToken )* kw= RightParenthesis
            {
            kw=(Token)match(input,LeftParenthesis,FOLLOW_52); 

            			current.merge(kw);
            			newLeafNode(kw, grammarAccess.getParenthesizedAccess().getLeftParenthesisKeyword_0());
            		
            // InternalSQLParser.g:2390:3: (this_InnerToken_1= ruleInnerToken )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=TIMESTAMPTZ && LA43_0<=LeftParenthesis)||(LA43_0>=Comma && LA43_0<=FullStop)||(LA43_0>=RULE_ID && LA43_0<=RULE_INT)||LA43_0==RULE_ANY_OTHER) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalSQLParser.g:2391:4: this_InnerToken_1= ruleInnerToken
            	    {

            	    				newCompositeNode(grammarAccess.getParenthesizedAccess().getInnerTokenParserRuleCall_1());
            	    			
            	    pushFollow(FOLLOW_52);
            	    this_InnerToken_1=ruleInnerToken();

            	    state._fsp--;


            	    				current.merge(this_InnerToken_1);
            	    			

            	    				afterParserOrEnumRuleCall();
            	    			

            	    }
            	    break;

            	default :
            	    break loop43;
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
    // InternalSQLParser.g:2411:1: entryRuleInnerToken returns [String current=null] : iv_ruleInnerToken= ruleInnerToken EOF ;
    public final String entryRuleInnerToken() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleInnerToken = null;


        try {
            // InternalSQLParser.g:2411:50: (iv_ruleInnerToken= ruleInnerToken EOF )
            // InternalSQLParser.g:2412:2: iv_ruleInnerToken= ruleInnerToken EOF
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
    // InternalSQLParser.g:2418:1: ruleInnerToken returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Name_0= ruleName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= Comma | this_Parenthesized_6= ruleParenthesized | kw= CREATE | kw= TABLE | kw= NOT | kw= NULL | kw= PRIMARY | kw= UNIQUE | kw= WITH | kw= CONSTRAINT | kw= FOREIGN | kw= REFERENCES | kw= ON | kw= DEFAULT | kw= FULL | kw= DEFERRABLE | kw= INITIALLY | kw= CHECK | kw= AS ) ;
    public final AntlrDatatypeRuleToken ruleInnerToken() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_1=null;
        Token this_INT_2=null;
        Token this_ANY_OTHER_3=null;
        Token kw=null;
        AntlrDatatypeRuleToken this_Name_0 = null;

        AntlrDatatypeRuleToken this_Parenthesized_6 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2424:2: ( (this_Name_0= ruleName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= Comma | this_Parenthesized_6= ruleParenthesized | kw= CREATE | kw= TABLE | kw= NOT | kw= NULL | kw= PRIMARY | kw= UNIQUE | kw= WITH | kw= CONSTRAINT | kw= FOREIGN | kw= REFERENCES | kw= ON | kw= DEFAULT | kw= FULL | kw= DEFERRABLE | kw= INITIALLY | kw= CHECK | kw= AS ) )
            // InternalSQLParser.g:2425:2: (this_Name_0= ruleName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= Comma | this_Parenthesized_6= ruleParenthesized | kw= CREATE | kw= TABLE | kw= NOT | kw= NULL | kw= PRIMARY | kw= UNIQUE | kw= WITH | kw= CONSTRAINT | kw= FOREIGN | kw= REFERENCES | kw= ON | kw= DEFAULT | kw= FULL | kw= DEFERRABLE | kw= INITIALLY | kw= CHECK | kw= AS )
            {
            // InternalSQLParser.g:2425:2: (this_Name_0= ruleName | this_STRING_1= RULE_STRING | this_INT_2= RULE_INT | this_ANY_OTHER_3= RULE_ANY_OTHER | kw= FullStop | kw= Comma | this_Parenthesized_6= ruleParenthesized | kw= CREATE | kw= TABLE | kw= NOT | kw= NULL | kw= PRIMARY | kw= UNIQUE | kw= WITH | kw= CONSTRAINT | kw= FOREIGN | kw= REFERENCES | kw= ON | kw= DEFAULT | kw= FULL | kw= DEFERRABLE | kw= INITIALLY | kw= CHECK | kw= AS )
            int alt44=24;
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
            case SERIAL:
            case SIMPLE:
            case STORED:
            case UPDATE:
            case BYTEA:
            case FLOAT:
            case JSONB:
            case MATCH:
            case CHAR:
            case DATE:
            case REAL:
            case TEXT:
            case TIME:
            case UUID:
            case ZONE:
            case INT:
            case KEY:
            case SET:
            case BY:
            case IF:
            case NO:
            case RULE_ID:
            case RULE_QUOTED_ID:
                {
                alt44=1;
                }
                break;
            case RULE_STRING:
                {
                alt44=2;
                }
                break;
            case RULE_INT:
                {
                alt44=3;
                }
                break;
            case RULE_ANY_OTHER:
                {
                alt44=4;
                }
                break;
            case FullStop:
                {
                alt44=5;
                }
                break;
            case Comma:
                {
                alt44=6;
                }
                break;
            case LeftParenthesis:
                {
                alt44=7;
                }
                break;
            case CREATE:
                {
                alt44=8;
                }
                break;
            case TABLE:
                {
                alt44=9;
                }
                break;
            case NOT:
                {
                alt44=10;
                }
                break;
            case NULL:
                {
                alt44=11;
                }
                break;
            case PRIMARY:
                {
                alt44=12;
                }
                break;
            case UNIQUE:
                {
                alt44=13;
                }
                break;
            case WITH:
                {
                alt44=14;
                }
                break;
            case CONSTRAINT:
                {
                alt44=15;
                }
                break;
            case FOREIGN:
                {
                alt44=16;
                }
                break;
            case REFERENCES:
                {
                alt44=17;
                }
                break;
            case ON:
                {
                alt44=18;
                }
                break;
            case DEFAULT:
                {
                alt44=19;
                }
                break;
            case FULL:
                {
                alt44=20;
                }
                break;
            case DEFERRABLE:
                {
                alt44=21;
                }
                break;
            case INITIALLY:
                {
                alt44=22;
                }
                break;
            case CHECK:
                {
                alt44=23;
                }
                break;
            case AS:
                {
                alt44=24;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }

            switch (alt44) {
                case 1 :
                    // InternalSQLParser.g:2426:3: this_Name_0= ruleName
                    {

                    			newCompositeNode(grammarAccess.getInnerTokenAccess().getNameParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Name_0=ruleName();

                    state._fsp--;


                    			current.merge(this_Name_0);
                    		

                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2437:3: this_STRING_1= RULE_STRING
                    {
                    this_STRING_1=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    			current.merge(this_STRING_1);
                    		

                    			newLeafNode(this_STRING_1, grammarAccess.getInnerTokenAccess().getSTRINGTerminalRuleCall_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:2445:3: this_INT_2= RULE_INT
                    {
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_2); 

                    			current.merge(this_INT_2);
                    		

                    			newLeafNode(this_INT_2, grammarAccess.getInnerTokenAccess().getINTTerminalRuleCall_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:2453:3: this_ANY_OTHER_3= RULE_ANY_OTHER
                    {
                    this_ANY_OTHER_3=(Token)match(input,RULE_ANY_OTHER,FOLLOW_2); 

                    			current.merge(this_ANY_OTHER_3);
                    		

                    			newLeafNode(this_ANY_OTHER_3, grammarAccess.getInnerTokenAccess().getANY_OTHERTerminalRuleCall_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:2461:3: kw= FullStop
                    {
                    kw=(Token)match(input,FullStop,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getFullStopKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:2467:3: kw= Comma
                    {
                    kw=(Token)match(input,Comma,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getCommaKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:2473:3: this_Parenthesized_6= ruleParenthesized
                    {

                    			newCompositeNode(grammarAccess.getInnerTokenAccess().getParenthesizedParserRuleCall_6());
                    		
                    pushFollow(FOLLOW_2);
                    this_Parenthesized_6=ruleParenthesized();

                    state._fsp--;


                    			current.merge(this_Parenthesized_6);
                    		

                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 8 :
                    // InternalSQLParser.g:2484:3: kw= CREATE
                    {
                    kw=(Token)match(input,CREATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getCREATEKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalSQLParser.g:2490:3: kw= TABLE
                    {
                    kw=(Token)match(input,TABLE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getTABLEKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalSQLParser.g:2496:3: kw= NOT
                    {
                    kw=(Token)match(input,NOT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getNOTKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalSQLParser.g:2502:3: kw= NULL
                    {
                    kw=(Token)match(input,NULL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getNULLKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalSQLParser.g:2508:3: kw= PRIMARY
                    {
                    kw=(Token)match(input,PRIMARY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getPRIMARYKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalSQLParser.g:2514:3: kw= UNIQUE
                    {
                    kw=(Token)match(input,UNIQUE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getUNIQUEKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalSQLParser.g:2520:3: kw= WITH
                    {
                    kw=(Token)match(input,WITH,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getWITHKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalSQLParser.g:2526:3: kw= CONSTRAINT
                    {
                    kw=(Token)match(input,CONSTRAINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getCONSTRAINTKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalSQLParser.g:2532:3: kw= FOREIGN
                    {
                    kw=(Token)match(input,FOREIGN,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getFOREIGNKeyword_15());
                    		

                    }
                    break;
                case 17 :
                    // InternalSQLParser.g:2538:3: kw= REFERENCES
                    {
                    kw=(Token)match(input,REFERENCES,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getREFERENCESKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalSQLParser.g:2544:3: kw= ON
                    {
                    kw=(Token)match(input,ON,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getONKeyword_17());
                    		

                    }
                    break;
                case 19 :
                    // InternalSQLParser.g:2550:3: kw= DEFAULT
                    {
                    kw=(Token)match(input,DEFAULT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getDEFAULTKeyword_18());
                    		

                    }
                    break;
                case 20 :
                    // InternalSQLParser.g:2556:3: kw= FULL
                    {
                    kw=(Token)match(input,FULL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getFULLKeyword_19());
                    		

                    }
                    break;
                case 21 :
                    // InternalSQLParser.g:2562:3: kw= DEFERRABLE
                    {
                    kw=(Token)match(input,DEFERRABLE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getDEFERRABLEKeyword_20());
                    		

                    }
                    break;
                case 22 :
                    // InternalSQLParser.g:2568:3: kw= INITIALLY
                    {
                    kw=(Token)match(input,INITIALLY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getINITIALLYKeyword_21());
                    		

                    }
                    break;
                case 23 :
                    // InternalSQLParser.g:2574:3: kw= CHECK
                    {
                    kw=(Token)match(input,CHECK,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getCHECKKeyword_22());
                    		

                    }
                    break;
                case 24 :
                    // InternalSQLParser.g:2580:3: kw= AS
                    {
                    kw=(Token)match(input,AS,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getInnerTokenAccess().getASKeyword_23());
                    		

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


    // $ANTLR start "entryRuleName"
    // InternalSQLParser.g:2589:1: entryRuleName returns [String current=null] : iv_ruleName= ruleName EOF ;
    public final String entryRuleName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleName = null;


        try {
            // InternalSQLParser.g:2589:44: (iv_ruleName= ruleName EOF )
            // InternalSQLParser.g:2590:2: iv_ruleName= ruleName EOF
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
    // InternalSQLParser.g:2596:1: ruleName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ExprName_0= ruleExprName | kw= GENERATED ) ;
    public final AntlrDatatypeRuleToken ruleName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this_ExprName_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:2602:2: ( (this_ExprName_0= ruleExprName | kw= GENERATED ) )
            // InternalSQLParser.g:2603:2: (this_ExprName_0= ruleExprName | kw= GENERATED )
            {
            // InternalSQLParser.g:2603:2: (this_ExprName_0= ruleExprName | kw= GENERATED )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==TIMESTAMPTZ||(LA45_0>=BIGSERIAL && LA45_0<=CHARACTER)||LA45_0==IMMEDIATE||(LA45_0>=PRECISION && LA45_0<=DECIMAL)||(LA45_0>=INTEGER && LA45_0<=NUMERIC)||(LA45_0>=VARCHAR && LA45_0<=BIGINT)||(LA45_0>=DELETE && LA45_0<=STORED)||(LA45_0>=UPDATE && LA45_0<=BYTEA)||(LA45_0>=FLOAT && LA45_0<=MATCH)||(LA45_0>=CHAR && LA45_0<=DATE)||(LA45_0>=REAL && LA45_0<=UUID)||(LA45_0>=ZONE && LA45_0<=KEY)||LA45_0==SET||(LA45_0>=BY && LA45_0<=NO)||(LA45_0>=RULE_ID && LA45_0<=RULE_QUOTED_ID)) ) {
                alt45=1;
            }
            else if ( (LA45_0==GENERATED) ) {
                alt45=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // InternalSQLParser.g:2604:3: this_ExprName_0= ruleExprName
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
                    // InternalSQLParser.g:2615:3: kw= GENERATED
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
    // InternalSQLParser.g:2624:1: entryRuleExprName returns [String current=null] : iv_ruleExprName= ruleExprName EOF ;
    public final String entryRuleExprName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleExprName = null;


        try {
            // InternalSQLParser.g:2624:48: (iv_ruleExprName= ruleExprName EOF )
            // InternalSQLParser.g:2625:2: iv_ruleExprName= ruleExprName EOF
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
    // InternalSQLParser.g:2631:1: ruleExprName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA | kw= DELETE | kw= UPDATE | kw= CASCADE | kw= RESTRICT | kw= NO | kw= ACTION | kw= SET | kw= MATCH | kw= SIMPLE | kw= DEFERRED | kw= IMMEDIATE | kw= ALWAYS | kw= BY | kw= IDENTITY | kw= STORED ) ;
    public final AntlrDatatypeRuleToken ruleExprName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token this_QUOTED_ID_1=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSQLParser.g:2637:2: ( (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA | kw= DELETE | kw= UPDATE | kw= CASCADE | kw= RESTRICT | kw= NO | kw= ACTION | kw= SET | kw= MATCH | kw= SIMPLE | kw= DEFERRED | kw= IMMEDIATE | kw= ALWAYS | kw= BY | kw= IDENTITY | kw= STORED ) )
            // InternalSQLParser.g:2638:2: (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA | kw= DELETE | kw= UPDATE | kw= CASCADE | kw= RESTRICT | kw= NO | kw= ACTION | kw= SET | kw= MATCH | kw= SIMPLE | kw= DEFERRED | kw= IMMEDIATE | kw= ALWAYS | kw= BY | kw= IDENTITY | kw= STORED )
            {
            // InternalSQLParser.g:2638:2: (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA | kw= DELETE | kw= UPDATE | kw= CASCADE | kw= RESTRICT | kw= NO | kw= ACTION | kw= SET | kw= MATCH | kw= SIMPLE | kw= DEFERRED | kw= IMMEDIATE | kw= ALWAYS | kw= BY | kw= IDENTITY | kw= STORED )
            int alt46=47;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt46=1;
                }
                break;
            case RULE_QUOTED_ID:
                {
                alt46=2;
                }
                break;
            case KEY:
                {
                alt46=3;
                }
                break;
            case TEXT:
                {
                alt46=4;
                }
                break;
            case IF:
                {
                alt46=5;
                }
                break;
            case EXISTS:
                {
                alt46=6;
                }
                break;
            case INT:
                {
                alt46=7;
                }
                break;
            case BIGINT:
                {
                alt46=8;
                }
                break;
            case DECIMAL:
                {
                alt46=9;
                }
                break;
            case TIMESTAMP:
                {
                alt46=10;
                }
                break;
            case BOOLEAN:
                {
                alt46=11;
                }
                break;
            case VARCHAR:
                {
                alt46=12;
                }
                break;
            case INTEGER:
                {
                alt46=13;
                }
                break;
            case SMALLINT:
                {
                alt46=14;
                }
                break;
            case NUMERIC:
                {
                alt46=15;
                }
                break;
            case REAL:
                {
                alt46=16;
                }
                break;
            case FLOAT:
                {
                alt46=17;
                }
                break;
            case PRECISION:
                {
                alt46=18;
                }
                break;
            case CHAR:
                {
                alt46=19;
                }
                break;
            case CHARACTER:
                {
                alt46=20;
                }
                break;
            case TIME:
                {
                alt46=21;
                }
                break;
            case DOUBLE:
                {
                alt46=22;
                }
                break;
            case VARYING:
                {
                alt46=23;
                }
                break;
            case WITHOUT:
                {
                alt46=24;
                }
                break;
            case ZONE:
                {
                alt46=25;
                }
                break;
            case UUID:
                {
                alt46=26;
                }
                break;
            case DATE:
                {
                alt46=27;
                }
                break;
            case SERIAL:
                {
                alt46=28;
                }
                break;
            case BIGSERIAL:
                {
                alt46=29;
                }
                break;
            case TIMESTAMPTZ:
                {
                alt46=30;
                }
                break;
            case JSONB:
                {
                alt46=31;
                }
                break;
            case BYTEA:
                {
                alt46=32;
                }
                break;
            case DELETE:
                {
                alt46=33;
                }
                break;
            case UPDATE:
                {
                alt46=34;
                }
                break;
            case CASCADE:
                {
                alt46=35;
                }
                break;
            case RESTRICT:
                {
                alt46=36;
                }
                break;
            case NO:
                {
                alt46=37;
                }
                break;
            case ACTION:
                {
                alt46=38;
                }
                break;
            case SET:
                {
                alt46=39;
                }
                break;
            case MATCH:
                {
                alt46=40;
                }
                break;
            case SIMPLE:
                {
                alt46=41;
                }
                break;
            case DEFERRED:
                {
                alt46=42;
                }
                break;
            case IMMEDIATE:
                {
                alt46=43;
                }
                break;
            case ALWAYS:
                {
                alt46=44;
                }
                break;
            case BY:
                {
                alt46=45;
                }
                break;
            case IDENTITY:
                {
                alt46=46;
                }
                break;
            case STORED:
                {
                alt46=47;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }

            switch (alt46) {
                case 1 :
                    // InternalSQLParser.g:2639:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getExprNameAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2647:3: this_QUOTED_ID_1= RULE_QUOTED_ID
                    {
                    this_QUOTED_ID_1=(Token)match(input,RULE_QUOTED_ID,FOLLOW_2); 

                    			current.merge(this_QUOTED_ID_1);
                    		

                    			newLeafNode(this_QUOTED_ID_1, grammarAccess.getExprNameAccess().getQUOTED_IDTerminalRuleCall_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:2655:3: kw= KEY
                    {
                    kw=(Token)match(input,KEY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getKEYKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:2661:3: kw= TEXT
                    {
                    kw=(Token)match(input,TEXT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getTEXTKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:2667:3: kw= IF
                    {
                    kw=(Token)match(input,IF,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getIFKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:2673:3: kw= EXISTS
                    {
                    kw=(Token)match(input,EXISTS,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getEXISTSKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:2679:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getINTKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalSQLParser.g:2685:3: kw= BIGINT
                    {
                    kw=(Token)match(input,BIGINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getBIGINTKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalSQLParser.g:2691:3: kw= DECIMAL
                    {
                    kw=(Token)match(input,DECIMAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getDECIMALKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalSQLParser.g:2697:3: kw= TIMESTAMP
                    {
                    kw=(Token)match(input,TIMESTAMP,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getTIMESTAMPKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalSQLParser.g:2703:3: kw= BOOLEAN
                    {
                    kw=(Token)match(input,BOOLEAN,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getBOOLEANKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalSQLParser.g:2709:3: kw= VARCHAR
                    {
                    kw=(Token)match(input,VARCHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getVARCHARKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalSQLParser.g:2715:3: kw= INTEGER
                    {
                    kw=(Token)match(input,INTEGER,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getINTEGERKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalSQLParser.g:2721:3: kw= SMALLINT
                    {
                    kw=(Token)match(input,SMALLINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSMALLINTKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalSQLParser.g:2727:3: kw= NUMERIC
                    {
                    kw=(Token)match(input,NUMERIC,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getNUMERICKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalSQLParser.g:2733:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getREALKeyword_15());
                    		

                    }
                    break;
                case 17 :
                    // InternalSQLParser.g:2739:3: kw= FLOAT
                    {
                    kw=(Token)match(input,FLOAT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getFLOATKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalSQLParser.g:2745:3: kw= PRECISION
                    {
                    kw=(Token)match(input,PRECISION,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getPRECISIONKeyword_17());
                    		

                    }
                    break;
                case 19 :
                    // InternalSQLParser.g:2751:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getCHARKeyword_18());
                    		

                    }
                    break;
                case 20 :
                    // InternalSQLParser.g:2757:3: kw= CHARACTER
                    {
                    kw=(Token)match(input,CHARACTER,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getCHARACTERKeyword_19());
                    		

                    }
                    break;
                case 21 :
                    // InternalSQLParser.g:2763:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getTIMEKeyword_20());
                    		

                    }
                    break;
                case 22 :
                    // InternalSQLParser.g:2769:3: kw= DOUBLE
                    {
                    kw=(Token)match(input,DOUBLE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getDOUBLEKeyword_21());
                    		

                    }
                    break;
                case 23 :
                    // InternalSQLParser.g:2775:3: kw= VARYING
                    {
                    kw=(Token)match(input,VARYING,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getVARYINGKeyword_22());
                    		

                    }
                    break;
                case 24 :
                    // InternalSQLParser.g:2781:3: kw= WITHOUT
                    {
                    kw=(Token)match(input,WITHOUT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getWITHOUTKeyword_23());
                    		

                    }
                    break;
                case 25 :
                    // InternalSQLParser.g:2787:3: kw= ZONE
                    {
                    kw=(Token)match(input,ZONE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getZONEKeyword_24());
                    		

                    }
                    break;
                case 26 :
                    // InternalSQLParser.g:2793:3: kw= UUID
                    {
                    kw=(Token)match(input,UUID,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getUUIDKeyword_25());
                    		

                    }
                    break;
                case 27 :
                    // InternalSQLParser.g:2799:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getDATEKeyword_26());
                    		

                    }
                    break;
                case 28 :
                    // InternalSQLParser.g:2805:3: kw= SERIAL
                    {
                    kw=(Token)match(input,SERIAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSERIALKeyword_27());
                    		

                    }
                    break;
                case 29 :
                    // InternalSQLParser.g:2811:3: kw= BIGSERIAL
                    {
                    kw=(Token)match(input,BIGSERIAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getBIGSERIALKeyword_28());
                    		

                    }
                    break;
                case 30 :
                    // InternalSQLParser.g:2817:3: kw= TIMESTAMPTZ
                    {
                    kw=(Token)match(input,TIMESTAMPTZ,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getTIMESTAMPTZKeyword_29());
                    		

                    }
                    break;
                case 31 :
                    // InternalSQLParser.g:2823:3: kw= JSONB
                    {
                    kw=(Token)match(input,JSONB,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getJSONBKeyword_30());
                    		

                    }
                    break;
                case 32 :
                    // InternalSQLParser.g:2829:3: kw= BYTEA
                    {
                    kw=(Token)match(input,BYTEA,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getBYTEAKeyword_31());
                    		

                    }
                    break;
                case 33 :
                    // InternalSQLParser.g:2835:3: kw= DELETE
                    {
                    kw=(Token)match(input,DELETE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getDELETEKeyword_32());
                    		

                    }
                    break;
                case 34 :
                    // InternalSQLParser.g:2841:3: kw= UPDATE
                    {
                    kw=(Token)match(input,UPDATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getUPDATEKeyword_33());
                    		

                    }
                    break;
                case 35 :
                    // InternalSQLParser.g:2847:3: kw= CASCADE
                    {
                    kw=(Token)match(input,CASCADE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getCASCADEKeyword_34());
                    		

                    }
                    break;
                case 36 :
                    // InternalSQLParser.g:2853:3: kw= RESTRICT
                    {
                    kw=(Token)match(input,RESTRICT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getRESTRICTKeyword_35());
                    		

                    }
                    break;
                case 37 :
                    // InternalSQLParser.g:2859:3: kw= NO
                    {
                    kw=(Token)match(input,NO,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getNOKeyword_36());
                    		

                    }
                    break;
                case 38 :
                    // InternalSQLParser.g:2865:3: kw= ACTION
                    {
                    kw=(Token)match(input,ACTION,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getACTIONKeyword_37());
                    		

                    }
                    break;
                case 39 :
                    // InternalSQLParser.g:2871:3: kw= SET
                    {
                    kw=(Token)match(input,SET,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSETKeyword_38());
                    		

                    }
                    break;
                case 40 :
                    // InternalSQLParser.g:2877:3: kw= MATCH
                    {
                    kw=(Token)match(input,MATCH,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getMATCHKeyword_39());
                    		

                    }
                    break;
                case 41 :
                    // InternalSQLParser.g:2883:3: kw= SIMPLE
                    {
                    kw=(Token)match(input,SIMPLE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSIMPLEKeyword_40());
                    		

                    }
                    break;
                case 42 :
                    // InternalSQLParser.g:2889:3: kw= DEFERRED
                    {
                    kw=(Token)match(input,DEFERRED,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getDEFERREDKeyword_41());
                    		

                    }
                    break;
                case 43 :
                    // InternalSQLParser.g:2895:3: kw= IMMEDIATE
                    {
                    kw=(Token)match(input,IMMEDIATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getIMMEDIATEKeyword_42());
                    		

                    }
                    break;
                case 44 :
                    // InternalSQLParser.g:2901:3: kw= ALWAYS
                    {
                    kw=(Token)match(input,ALWAYS,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getALWAYSKeyword_43());
                    		

                    }
                    break;
                case 45 :
                    // InternalSQLParser.g:2907:3: kw= BY
                    {
                    kw=(Token)match(input,BY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getBYKeyword_44());
                    		

                    }
                    break;
                case 46 :
                    // InternalSQLParser.g:2913:3: kw= IDENTITY
                    {
                    kw=(Token)match(input,IDENTITY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getIDENTITYKeyword_45());
                    		

                    }
                    break;
                case 47 :
                    // InternalSQLParser.g:2919:3: kw= STORED
                    {
                    kw=(Token)match(input,STORED,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getExprNameAccess().getSTOREDKeyword_46());
                    		

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
    // InternalSQLParser.g:2928:1: ruleAllTypes returns [Enumerator current=null] : ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) ) ;
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
            // InternalSQLParser.g:2934:2: ( ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) ) )
            // InternalSQLParser.g:2935:2: ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) )
            {
            // InternalSQLParser.g:2935:2: ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) )
            int alt47=18;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt47=1;
                }
                break;
            case BIGINT:
                {
                alt47=2;
                }
                break;
            case UUID:
                {
                alt47=3;
                }
                break;
            case DECIMAL:
                {
                alt47=4;
                }
                break;
            case TIMESTAMP:
                {
                alt47=5;
                }
                break;
            case DATE:
                {
                alt47=6;
                }
                break;
            case BOOLEAN:
                {
                alt47=7;
                }
                break;
            case TEXT:
                {
                alt47=8;
                }
                break;
            case INTEGER:
                {
                alt47=9;
                }
                break;
            case SMALLINT:
                {
                alt47=10;
                }
                break;
            case SERIAL:
                {
                alt47=11;
                }
                break;
            case BIGSERIAL:
                {
                alt47=12;
                }
                break;
            case NUMERIC:
                {
                alt47=13;
                }
                break;
            case REAL:
                {
                alt47=14;
                }
                break;
            case FLOAT:
                {
                alt47=15;
                }
                break;
            case TIMESTAMPTZ:
                {
                alt47=16;
                }
                break;
            case JSONB:
                {
                alt47=17;
                }
                break;
            case BYTEA:
                {
                alt47=18;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }

            switch (alt47) {
                case 1 :
                    // InternalSQLParser.g:2936:3: (enumLiteral_0= INT )
                    {
                    // InternalSQLParser.g:2936:3: (enumLiteral_0= INT )
                    // InternalSQLParser.g:2937:4: enumLiteral_0= INT
                    {
                    enumLiteral_0=(Token)match(input,INT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:2944:3: (enumLiteral_1= BIGINT )
                    {
                    // InternalSQLParser.g:2944:3: (enumLiteral_1= BIGINT )
                    // InternalSQLParser.g:2945:4: enumLiteral_1= BIGINT
                    {
                    enumLiteral_1=(Token)match(input,BIGINT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:2952:3: (enumLiteral_2= UUID )
                    {
                    // InternalSQLParser.g:2952:3: (enumLiteral_2= UUID )
                    // InternalSQLParser.g:2953:4: enumLiteral_2= UUID
                    {
                    enumLiteral_2=(Token)match(input,UUID,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:2960:3: (enumLiteral_3= DECIMAL )
                    {
                    // InternalSQLParser.g:2960:3: (enumLiteral_3= DECIMAL )
                    // InternalSQLParser.g:2961:4: enumLiteral_3= DECIMAL
                    {
                    enumLiteral_3=(Token)match(input,DECIMAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:2968:3: (enumLiteral_4= TIMESTAMP )
                    {
                    // InternalSQLParser.g:2968:3: (enumLiteral_4= TIMESTAMP )
                    // InternalSQLParser.g:2969:4: enumLiteral_4= TIMESTAMP
                    {
                    enumLiteral_4=(Token)match(input,TIMESTAMP,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:2976:3: (enumLiteral_5= DATE )
                    {
                    // InternalSQLParser.g:2976:3: (enumLiteral_5= DATE )
                    // InternalSQLParser.g:2977:4: enumLiteral_5= DATE
                    {
                    enumLiteral_5=(Token)match(input,DATE,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:2984:3: (enumLiteral_6= BOOLEAN )
                    {
                    // InternalSQLParser.g:2984:3: (enumLiteral_6= BOOLEAN )
                    // InternalSQLParser.g:2985:4: enumLiteral_6= BOOLEAN
                    {
                    enumLiteral_6=(Token)match(input,BOOLEAN,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalSQLParser.g:2992:3: (enumLiteral_7= TEXT )
                    {
                    // InternalSQLParser.g:2992:3: (enumLiteral_7= TEXT )
                    // InternalSQLParser.g:2993:4: enumLiteral_7= TEXT
                    {
                    enumLiteral_7=(Token)match(input,TEXT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTEXTEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getAllTypesAccess().getTEXTEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;
                case 9 :
                    // InternalSQLParser.g:3000:3: (enumLiteral_8= INTEGER )
                    {
                    // InternalSQLParser.g:3000:3: (enumLiteral_8= INTEGER )
                    // InternalSQLParser.g:3001:4: enumLiteral_8= INTEGER
                    {
                    enumLiteral_8=(Token)match(input,INTEGER,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getINTEGEREnumLiteralDeclaration_8().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_8, grammarAccess.getAllTypesAccess().getINTEGEREnumLiteralDeclaration_8());
                    			

                    }


                    }
                    break;
                case 10 :
                    // InternalSQLParser.g:3008:3: (enumLiteral_9= SMALLINT )
                    {
                    // InternalSQLParser.g:3008:3: (enumLiteral_9= SMALLINT )
                    // InternalSQLParser.g:3009:4: enumLiteral_9= SMALLINT
                    {
                    enumLiteral_9=(Token)match(input,SMALLINT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getSMALLINTEnumLiteralDeclaration_9().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_9, grammarAccess.getAllTypesAccess().getSMALLINTEnumLiteralDeclaration_9());
                    			

                    }


                    }
                    break;
                case 11 :
                    // InternalSQLParser.g:3016:3: (enumLiteral_10= SERIAL )
                    {
                    // InternalSQLParser.g:3016:3: (enumLiteral_10= SERIAL )
                    // InternalSQLParser.g:3017:4: enumLiteral_10= SERIAL
                    {
                    enumLiteral_10=(Token)match(input,SERIAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getSERIALEnumLiteralDeclaration_10().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_10, grammarAccess.getAllTypesAccess().getSERIALEnumLiteralDeclaration_10());
                    			

                    }


                    }
                    break;
                case 12 :
                    // InternalSQLParser.g:3024:3: (enumLiteral_11= BIGSERIAL )
                    {
                    // InternalSQLParser.g:3024:3: (enumLiteral_11= BIGSERIAL )
                    // InternalSQLParser.g:3025:4: enumLiteral_11= BIGSERIAL
                    {
                    enumLiteral_11=(Token)match(input,BIGSERIAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBIGSERIALEnumLiteralDeclaration_11().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_11, grammarAccess.getAllTypesAccess().getBIGSERIALEnumLiteralDeclaration_11());
                    			

                    }


                    }
                    break;
                case 13 :
                    // InternalSQLParser.g:3032:3: (enumLiteral_12= NUMERIC )
                    {
                    // InternalSQLParser.g:3032:3: (enumLiteral_12= NUMERIC )
                    // InternalSQLParser.g:3033:4: enumLiteral_12= NUMERIC
                    {
                    enumLiteral_12=(Token)match(input,NUMERIC,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getNUMERICEnumLiteralDeclaration_12().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_12, grammarAccess.getAllTypesAccess().getNUMERICEnumLiteralDeclaration_12());
                    			

                    }


                    }
                    break;
                case 14 :
                    // InternalSQLParser.g:3040:3: (enumLiteral_13= REAL )
                    {
                    // InternalSQLParser.g:3040:3: (enumLiteral_13= REAL )
                    // InternalSQLParser.g:3041:4: enumLiteral_13= REAL
                    {
                    enumLiteral_13=(Token)match(input,REAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getREALEnumLiteralDeclaration_13().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_13, grammarAccess.getAllTypesAccess().getREALEnumLiteralDeclaration_13());
                    			

                    }


                    }
                    break;
                case 15 :
                    // InternalSQLParser.g:3048:3: (enumLiteral_14= FLOAT )
                    {
                    // InternalSQLParser.g:3048:3: (enumLiteral_14= FLOAT )
                    // InternalSQLParser.g:3049:4: enumLiteral_14= FLOAT
                    {
                    enumLiteral_14=(Token)match(input,FLOAT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getFLOATEnumLiteralDeclaration_14().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_14, grammarAccess.getAllTypesAccess().getFLOATEnumLiteralDeclaration_14());
                    			

                    }


                    }
                    break;
                case 16 :
                    // InternalSQLParser.g:3056:3: (enumLiteral_15= TIMESTAMPTZ )
                    {
                    // InternalSQLParser.g:3056:3: (enumLiteral_15= TIMESTAMPTZ )
                    // InternalSQLParser.g:3057:4: enumLiteral_15= TIMESTAMPTZ
                    {
                    enumLiteral_15=(Token)match(input,TIMESTAMPTZ,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTIMESTAMPTZEnumLiteralDeclaration_15().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_15, grammarAccess.getAllTypesAccess().getTIMESTAMPTZEnumLiteralDeclaration_15());
                    			

                    }


                    }
                    break;
                case 17 :
                    // InternalSQLParser.g:3064:3: (enumLiteral_16= JSONB )
                    {
                    // InternalSQLParser.g:3064:3: (enumLiteral_16= JSONB )
                    // InternalSQLParser.g:3065:4: enumLiteral_16= JSONB
                    {
                    enumLiteral_16=(Token)match(input,JSONB,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getJSONBEnumLiteralDeclaration_16().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_16, grammarAccess.getAllTypesAccess().getJSONBEnumLiteralDeclaration_16());
                    			

                    }


                    }
                    break;
                case 18 :
                    // InternalSQLParser.g:3072:3: (enumLiteral_17= BYTEA )
                    {
                    // InternalSQLParser.g:3072:3: (enumLiteral_17= BYTEA )
                    // InternalSQLParser.g:3073:4: enumLiteral_17= BYTEA
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
    // InternalSQLParser.g:3083:1: ruleDoublePrecision returns [Enumerator current=null] : (enumLiteral_0= PRECISION ) ;
    public final Enumerator ruleDoublePrecision() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3089:2: ( (enumLiteral_0= PRECISION ) )
            // InternalSQLParser.g:3090:2: (enumLiteral_0= PRECISION )
            {
            // InternalSQLParser.g:3090:2: (enumLiteral_0= PRECISION )
            // InternalSQLParser.g:3091:3: enumLiteral_0= PRECISION
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
    // InternalSQLParser.g:3100:1: ruleZoneNoTz returns [Enumerator current=null] : (enumLiteral_0= ZONE ) ;
    public final Enumerator ruleZoneNoTz() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3106:2: ( (enumLiteral_0= ZONE ) )
            // InternalSQLParser.g:3107:2: (enumLiteral_0= ZONE )
            {
            // InternalSQLParser.g:3107:2: (enumLiteral_0= ZONE )
            // InternalSQLParser.g:3108:3: enumLiteral_0= ZONE
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
    // InternalSQLParser.g:3117:1: ruleZoneTz returns [Enumerator current=null] : (enumLiteral_0= ZONE ) ;
    public final Enumerator ruleZoneTz() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3123:2: ( (enumLiteral_0= ZONE ) )
            // InternalSQLParser.g:3124:2: (enumLiteral_0= ZONE )
            {
            // InternalSQLParser.g:3124:2: (enumLiteral_0= ZONE )
            // InternalSQLParser.g:3125:3: enumLiteral_0= ZONE
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
    // InternalSQLParser.g:3134:1: ruleRefAction returns [Enumerator current=null] : ( (enumLiteral_0= CASCADE ) | (enumLiteral_1= RESTRICT ) ) ;
    public final Enumerator ruleRefAction() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3140:2: ( ( (enumLiteral_0= CASCADE ) | (enumLiteral_1= RESTRICT ) ) )
            // InternalSQLParser.g:3141:2: ( (enumLiteral_0= CASCADE ) | (enumLiteral_1= RESTRICT ) )
            {
            // InternalSQLParser.g:3141:2: ( (enumLiteral_0= CASCADE ) | (enumLiteral_1= RESTRICT ) )
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==CASCADE) ) {
                alt48=1;
            }
            else if ( (LA48_0==RESTRICT) ) {
                alt48=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }
            switch (alt48) {
                case 1 :
                    // InternalSQLParser.g:3142:3: (enumLiteral_0= CASCADE )
                    {
                    // InternalSQLParser.g:3142:3: (enumLiteral_0= CASCADE )
                    // InternalSQLParser.g:3143:4: enumLiteral_0= CASCADE
                    {
                    enumLiteral_0=(Token)match(input,CASCADE,FOLLOW_2); 

                    				current = grammarAccess.getRefActionAccess().getCASCADEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getRefActionAccess().getCASCADEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3150:3: (enumLiteral_1= RESTRICT )
                    {
                    // InternalSQLParser.g:3150:3: (enumLiteral_1= RESTRICT )
                    // InternalSQLParser.g:3151:4: enumLiteral_1= RESTRICT
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
    // InternalSQLParser.g:3161:1: ruleNoAction returns [Enumerator current=null] : (enumLiteral_0= ACTION ) ;
    public final Enumerator ruleNoAction() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3167:2: ( (enumLiteral_0= ACTION ) )
            // InternalSQLParser.g:3168:2: (enumLiteral_0= ACTION )
            {
            // InternalSQLParser.g:3168:2: (enumLiteral_0= ACTION )
            // InternalSQLParser.g:3169:3: enumLiteral_0= ACTION
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
    // InternalSQLParser.g:3178:1: ruleSetAction returns [Enumerator current=null] : ( (enumLiteral_0= NULL ) | (enumLiteral_1= DEFAULT ) ) ;
    public final Enumerator ruleSetAction() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3184:2: ( ( (enumLiteral_0= NULL ) | (enumLiteral_1= DEFAULT ) ) )
            // InternalSQLParser.g:3185:2: ( (enumLiteral_0= NULL ) | (enumLiteral_1= DEFAULT ) )
            {
            // InternalSQLParser.g:3185:2: ( (enumLiteral_0= NULL ) | (enumLiteral_1= DEFAULT ) )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==NULL) ) {
                alt49=1;
            }
            else if ( (LA49_0==DEFAULT) ) {
                alt49=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // InternalSQLParser.g:3186:3: (enumLiteral_0= NULL )
                    {
                    // InternalSQLParser.g:3186:3: (enumLiteral_0= NULL )
                    // InternalSQLParser.g:3187:4: enumLiteral_0= NULL
                    {
                    enumLiteral_0=(Token)match(input,NULL,FOLLOW_2); 

                    				current = grammarAccess.getSetActionAccess().getSET_NULLEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getSetActionAccess().getSET_NULLEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3194:3: (enumLiteral_1= DEFAULT )
                    {
                    // InternalSQLParser.g:3194:3: (enumLiteral_1= DEFAULT )
                    // InternalSQLParser.g:3195:4: enumLiteral_1= DEFAULT
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
    // InternalSQLParser.g:3205:1: ruleMatchKind returns [Enumerator current=null] : ( (enumLiteral_0= SIMPLE ) | (enumLiteral_1= FULL ) ) ;
    public final Enumerator ruleMatchKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3211:2: ( ( (enumLiteral_0= SIMPLE ) | (enumLiteral_1= FULL ) ) )
            // InternalSQLParser.g:3212:2: ( (enumLiteral_0= SIMPLE ) | (enumLiteral_1= FULL ) )
            {
            // InternalSQLParser.g:3212:2: ( (enumLiteral_0= SIMPLE ) | (enumLiteral_1= FULL ) )
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==SIMPLE) ) {
                alt50=1;
            }
            else if ( (LA50_0==FULL) ) {
                alt50=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }
            switch (alt50) {
                case 1 :
                    // InternalSQLParser.g:3213:3: (enumLiteral_0= SIMPLE )
                    {
                    // InternalSQLParser.g:3213:3: (enumLiteral_0= SIMPLE )
                    // InternalSQLParser.g:3214:4: enumLiteral_0= SIMPLE
                    {
                    enumLiteral_0=(Token)match(input,SIMPLE,FOLLOW_2); 

                    				current = grammarAccess.getMatchKindAccess().getSIMPLEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getMatchKindAccess().getSIMPLEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:3221:3: (enumLiteral_1= FULL )
                    {
                    // InternalSQLParser.g:3221:3: (enumLiteral_1= FULL )
                    // InternalSQLParser.g:3222:4: enumLiteral_1= FULL
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
    // InternalSQLParser.g:3232:1: ruleDefaultKind returns [Enumerator current=null] : (enumLiteral_0= DEFAULT ) ;
    public final Enumerator ruleDefaultKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3238:2: ( (enumLiteral_0= DEFAULT ) )
            // InternalSQLParser.g:3239:2: (enumLiteral_0= DEFAULT )
            {
            // InternalSQLParser.g:3239:2: (enumLiteral_0= DEFAULT )
            // InternalSQLParser.g:3240:3: enumLiteral_0= DEFAULT
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
    // InternalSQLParser.g:3249:1: ruleCheckKind returns [Enumerator current=null] : (enumLiteral_0= CHECK ) ;
    public final Enumerator ruleCheckKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3255:2: ( (enumLiteral_0= CHECK ) )
            // InternalSQLParser.g:3256:2: (enumLiteral_0= CHECK )
            {
            // InternalSQLParser.g:3256:2: (enumLiteral_0= CHECK )
            // InternalSQLParser.g:3257:3: enumLiteral_0= CHECK
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
    // InternalSQLParser.g:3266:1: ruleGeneratedKind returns [Enumerator current=null] : (enumLiteral_0= GENERATED ) ;
    public final Enumerator ruleGeneratedKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3272:2: ( (enumLiteral_0= GENERATED ) )
            // InternalSQLParser.g:3273:2: (enumLiteral_0= GENERATED )
            {
            // InternalSQLParser.g:3273:2: (enumLiteral_0= GENERATED )
            // InternalSQLParser.g:3274:3: enumLiteral_0= GENERATED
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
    // InternalSQLParser.g:3283:1: ruleWithKind returns [Enumerator current=null] : (enumLiteral_0= WITH ) ;
    public final Enumerator ruleWithKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:3289:2: ( (enumLiteral_0= WITH ) )
            // InternalSQLParser.g:3290:2: (enumLiteral_0= WITH )
            {
            // InternalSQLParser.g:3290:2: (enumLiteral_0= WITH )
            // InternalSQLParser.g:3291:3: enumLiteral_0= WITH
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


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA16 dfa16 = new DFA16(this);
    protected DFA18 dfa18 = new DFA18(this);
    protected DFA20 dfa20 = new DFA20(this);
    static final String dfa_1s = "\63\uffff";
    static final String dfa_2s = "\1\4\60\103\2\uffff";
    static final String dfa_3s = "\1\111\60\106\2\uffff";
    static final String dfa_4s = "\61\uffff\1\1\1\2";
    static final String dfa_5s = "\63\uffff}>";
    static final String[] dfa_6s = {
            "\1\36\3\uffff\1\35\1\24\1\60\1\53\1\uffff\1\22\1\12\1\52\1\56\1\44\1\16\1\13\1\43\1\11\2\uffff\1\15\1\17\1\uffff\1\14\1\27\1\30\1\46\1\54\1\10\1\uffff\1\41\1\26\1\6\1\34\1\51\1\57\1\uffff\1\42\1\40\1\uffff\1\21\1\37\1\50\1\uffff\1\23\1\33\2\uffff\1\20\1\4\1\25\1\32\1\uffff\1\31\1\7\1\3\1\uffff\1\47\1\uffff\1\55\1\5\1\45\6\uffff\1\1\1\2",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "\1\62\2\uffff\1\61",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "149:3: ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )?";
        }
    }
    static final String dfa_7s = "\14\uffff";
    static final String dfa_8s = "\2\uffff\3\1\7\uffff";
    static final String dfa_9s = "\1\4\1\uffff\3\5\1\uffff\1\34\5\uffff";
    static final String dfa_10s = "\1\72\1\uffff\3\105\1\uffff\1\103\5\uffff";
    static final String dfa_11s = "\1\uffff\1\1\3\uffff\1\2\1\uffff\1\4\1\5\1\3\1\6\1\7";
    static final String dfa_12s = "\14\uffff}>";
    static final String[] dfa_13s = {
            "\1\1\3\uffff\1\1\1\6\4\uffff\1\3\3\uffff\2\1\1\uffff\1\2\2\uffff\1\1\1\4\1\uffff\1\5\4\uffff\1\1\2\uffff\1\10\1\uffff\1\1\4\uffff\1\1\1\uffff\2\1\2\uffff\1\7\1\1\2\uffff\2\1\1\uffff\1\1\2\uffff\1\1",
            "",
            "\1\1\1\uffff\1\1\2\uffff\1\1\13\uffff\1\1\3\uffff\1\1\15\uffff\1\1\2\uffff\1\1\7\uffff\1\1\10\uffff\1\1\6\uffff\1\11\2\1",
            "\1\1\1\uffff\1\1\2\uffff\1\1\13\uffff\1\1\3\uffff\1\1\2\uffff\1\12\12\uffff\1\1\2\uffff\1\1\7\uffff\1\1\4\uffff\1\13\3\uffff\1\1\7\uffff\2\1",
            "\1\1\1\uffff\1\1\2\uffff\1\1\13\uffff\1\1\3\uffff\1\1\15\uffff\1\1\2\uffff\1\1\7\uffff\1\1\10\uffff\1\1\6\uffff\1\11\2\1",
            "",
            "\1\5\46\uffff\1\7",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final short[] dfa_8 = DFA.unpackEncodedString(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final char[] dfa_10 = DFA.unpackEncodedStringToUnsignedChars(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[] dfa_12 = DFA.unpackEncodedString(dfa_12s);
    static final short[][] dfa_13 = unpackEncodedStringArray(dfa_13s);

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "403:3: ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) )";
        }
    }
    static final String dfa_14s = "\70\uffff";
    static final String dfa_15s = "\1\5\1\4\6\uffff\60\7";
    static final String dfa_16s = "\1\74\1\111\6\uffff\60\74";
    static final String dfa_17s = "\2\uffff\1\1\1\2\1\3\1\4\1\5\1\6\60\uffff";
    static final String dfa_18s = "\70\uffff}>";
    static final String[] dfa_19s = {
            "\1\1\1\uffff\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\45\3\uffff\1\44\1\33\1\67\1\62\1\uffff\1\31\1\21\1\61\1\65\1\53\1\25\1\22\1\52\1\20\2\uffff\1\24\1\26\1\uffff\1\23\1\36\1\37\1\55\1\63\1\17\1\uffff\1\50\1\35\1\15\1\43\1\60\1\66\1\uffff\1\51\1\47\1\uffff\1\30\1\46\1\57\1\uffff\1\32\1\42\2\uffff\1\27\1\13\1\34\1\41\1\uffff\1\40\1\16\1\12\1\uffff\1\56\1\uffff\1\64\1\14\1\54\6\uffff\1\10\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4",
            "\1\6\2\uffff\1\7\13\uffff\1\7\3\uffff\1\2\15\uffff\1\3\2\uffff\1\7\7\uffff\1\5\10\uffff\1\4"
    };

    static final short[] dfa_14 = DFA.unpackEncodedString(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final char[] dfa_16 = DFA.unpackEncodedStringToUnsignedChars(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[] dfa_18 = DFA.unpackEncodedString(dfa_18s);
    static final short[][] dfa_19 = unpackEncodedStringArray(dfa_19s);

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = dfa_14;
            this.eof = dfa_14;
            this.min = dfa_15;
            this.max = dfa_16;
            this.accept = dfa_17;
            this.special = dfa_18;
            this.transition = dfa_19;
        }
        public String getDescription() {
            return "879:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull | this_Nullable_3= ruleNullable | this_ColumnReferences_4= ruleColumnReferences | this_ColumnRaw_5= ruleColumnRaw )";
        }
    }
    static final String dfa_20s = "\66\uffff";
    static final String dfa_21s = "\1\5\1\4\4\uffff\60\27";
    static final String dfa_22s = "\1\53\1\111\4\uffff\60\53";
    static final String dfa_23s = "\2\uffff\1\1\1\2\1\3\1\4\60\uffff";
    static final String dfa_24s = "\66\uffff}>";
    static final String[] dfa_25s = {
            "\1\1\21\uffff\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\43\3\uffff\1\42\1\31\1\65\1\60\1\uffff\1\27\1\17\1\57\1\63\1\51\1\23\1\20\1\50\1\16\2\uffff\1\22\1\24\1\uffff\1\21\1\34\1\35\1\53\1\61\1\15\1\uffff\1\46\1\33\1\13\1\41\1\56\1\64\1\uffff\1\47\1\45\1\uffff\1\26\1\44\1\55\1\uffff\1\30\1\40\2\uffff\1\25\1\11\1\32\1\37\1\uffff\1\36\1\14\1\10\1\uffff\1\54\1\uffff\1\62\1\12\1\52\6\uffff\1\6\1\7",
            "",
            "",
            "",
            "",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5",
            "\1\4\2\uffff\1\2\15\uffff\1\3\2\uffff\1\5"
    };

    static final short[] dfa_20 = DFA.unpackEncodedString(dfa_20s);
    static final char[] dfa_21 = DFA.unpackEncodedStringToUnsignedChars(dfa_21s);
    static final char[] dfa_22 = DFA.unpackEncodedStringToUnsignedChars(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final short[] dfa_24 = DFA.unpackEncodedString(dfa_24s);
    static final short[][] dfa_25 = unpackEncodedStringArray(dfa_25s);

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = dfa_20;
            this.eof = dfa_20;
            this.min = dfa_21;
            this.max = dfa_22;
            this.accept = dfa_23;
            this.special = dfa_24;
            this.transition = dfa_25;
        }
        public String getDescription() {
            return "1322:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey | this_ForeignKey_2= ruleForeignKey | this_TableCheck_3= ruleTableCheck )";
        }
    }
    static final String dfa_26s = "\1\uffff\60\62\2\uffff";
    static final String dfa_27s = "\1\4\60\5\2\uffff";
    static final String[] dfa_28s = {
            "\1\36\3\uffff\1\35\1\24\1\60\1\53\1\uffff\1\22\1\12\1\52\1\56\1\44\1\16\1\13\1\43\1\11\2\uffff\1\15\1\17\1\uffff\1\14\1\27\1\30\1\46\1\54\1\10\1\uffff\1\41\1\26\1\6\1\34\1\51\1\57\1\uffff\1\42\1\40\1\uffff\1\21\1\37\1\50\1\uffff\1\23\1\33\2\uffff\1\20\1\4\1\25\1\32\1\uffff\1\31\1\7\1\3\1\uffff\1\47\1\uffff\1\55\1\5\1\45\6\uffff\1\1\1\2",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "\3\62\2\uffff\1\62\1\uffff\1\62\11\uffff\1\62\3\uffff\1\62\15\uffff\1\62\2\uffff\1\62\2\uffff\1\62\4\uffff\1\62\10\uffff\1\62\5\uffff\4\62\1\61",
            "",
            ""
    };
    static final short[] dfa_26 = DFA.unpackEncodedString(dfa_26s);
    static final char[] dfa_27 = DFA.unpackEncodedStringToUnsignedChars(dfa_27s);
    static final short[][] dfa_28 = unpackEncodedStringArray(dfa_28s);

    class DFA20 extends DFA {

        public DFA20(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 20;
            this.eot = dfa_1;
            this.eof = dfa_26;
            this.min = dfa_27;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_28;
        }
        public String getDescription() {
            return "1694:3: ( ( (lv_refSchema_0_0= ruleName ) ) otherlv_1= FullStop )?";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000200000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0xAEF376FDFB3FEF10L,0x0000000000000303L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0xAEF37FFDFFBFEF30L,0x0000000000000303L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x04B334290B2C4310L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0xBEFB7FFDFFFFEFB2L,0x0000000000000303L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0xBEFB7FFDFFFFEFB0L,0x0000000000000303L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0xAFFB76FDFB3FEB10L,0x0000000000008F4BL});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x8000000080000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x1000400000001042L,0x000000000000000CL});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x1000400000001042L,0x0000000000000004L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0004004000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x1000000000001042L,0x0000000000000004L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x1000000000001042L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x2000000000120000L,0x0000000000000002L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0008000000400000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000008800L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x1000000000000042L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000008L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0xAFF376FDFB3FEB12L,0x0000000000008F4BL});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0x0000000000008F7FL});

}