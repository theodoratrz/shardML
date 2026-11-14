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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "TIMESTAMPTZ", "BIGSERIAL", "CHARACTER", "PRECISION", "TIMESTAMP", "SMALLINT", "BOOLEAN", "DECIMAL", "INTEGER", "NUMERIC", "PRIMARY", "VARCHAR", "VARYING", "WITHOUT", "BIGINT", "CREATE", "DOUBLE", "EXISTS", "SERIAL", "UNIQUE", "BYTEA", "FLOAT", "JSONB", "TABLE", "CHAR", "DATE", "NULL", "REAL", "TEXT", "TIME", "UUID", "WITH", "ZONE", "INT", "KEY", "NOT", "IF", "LeftParenthesis", "RightParenthesis", "Comma", "FullStop", "Semicolon", "RULE_ID", "RULE_QUOTED_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int CREATE=19;
    public static final int ZONE=36;
    public static final int DECIMAL=11;
    public static final int TIMESTAMPTZ=4;
    public static final int TEXT=32;
    public static final int TABLE=27;
    public static final int LeftParenthesis=41;
    public static final int TIMESTAMP=8;
    public static final int UUID=34;
    public static final int BIGSERIAL=5;
    public static final int IF=40;
    public static final int INTEGER=12;
    public static final int VARYING=16;
    public static final int NULL=30;
    public static final int RULE_ID=46;
    public static final int RULE_QUOTED_ID=47;
    public static final int UNIQUE=23;
    public static final int RightParenthesis=42;
    public static final int BIGINT=18;
    public static final int BOOLEAN=10;
    public static final int DATE=29;
    public static final int NOT=39;
    public static final int RULE_INT=49;
    public static final int VARCHAR=15;
    public static final int REAL=31;
    public static final int RULE_ML_COMMENT=50;
    public static final int WITHOUT=17;
    public static final int KEY=38;
    public static final int FLOAT=25;
    public static final int CHAR=28;
    public static final int RULE_STRING=48;
    public static final int INT=37;
    public static final int PRECISION=7;
    public static final int RULE_SL_COMMENT=51;
    public static final int Comma=43;
    public static final int CHARACTER=6;
    public static final int DOUBLE=20;
    public static final int EOF=-1;
    public static final int SERIAL=22;
    public static final int FullStop=44;
    public static final int NUMERIC=13;
    public static final int JSONB=26;
    public static final int EXISTS=21;
    public static final int RULE_WS=52;
    public static final int PRIMARY=14;
    public static final int TIME=33;
    public static final int RULE_ANY_OTHER=53;
    public static final int WITH=35;
    public static final int Semicolon=45;
    public static final int BYTEA=24;
    public static final int SMALLINT=9;

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
    // InternalSQLParser.g:109:1: ruleTable returns [EObject current=null] : (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ) ;
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



        	enterRule();

        try {
            // InternalSQLParser.g:115:2: ( (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis ) )
            // InternalSQLParser.g:116:2: (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis )
            {
            // InternalSQLParser.g:116:2: (otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis )
            // InternalSQLParser.g:117:3: otherlv_0= CREATE otherlv_1= TABLE ( ( (lv_ifNotExists_2_0= IF ) ) otherlv_3= NOT otherlv_4= EXISTS )? ( ( (lv_schemaName_5_0= ruleName ) ) otherlv_6= FullStop )? ( (lv_name_7_0= ruleName ) ) otherlv_8= LeftParenthesis ( ( (lv_columns_9_0= ruleColumn ) ) | ( (lv_constraints_10_0= ruleTableConstraint ) ) ) (otherlv_11= Comma ( ( (lv_columns_12_0= ruleColumn ) ) | ( (lv_constraints_13_0= ruleTableConstraint ) ) ) )* otherlv_14= RightParenthesis
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

            if ( ((LA5_0>=TIMESTAMPTZ && LA5_0<=NUMERIC)||(LA5_0>=VARCHAR && LA5_0<=BIGINT)||(LA5_0>=DOUBLE && LA5_0<=SERIAL)||(LA5_0>=BYTEA && LA5_0<=JSONB)||(LA5_0>=CHAR && LA5_0<=DATE)||(LA5_0>=REAL && LA5_0<=UUID)||(LA5_0>=ZONE && LA5_0<=KEY)||LA5_0==IF||(LA5_0>=RULE_ID && LA5_0<=RULE_QUOTED_ID)) ) {
                alt5=1;
            }
            else if ( (LA5_0==PRIMARY||LA5_0==UNIQUE) ) {
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

            	    if ( ((LA6_0>=TIMESTAMPTZ && LA6_0<=NUMERIC)||(LA6_0>=VARCHAR && LA6_0<=BIGINT)||(LA6_0>=DOUBLE && LA6_0<=SERIAL)||(LA6_0>=BYTEA && LA6_0<=JSONB)||(LA6_0>=CHAR && LA6_0<=DATE)||(LA6_0>=REAL && LA6_0<=UUID)||(LA6_0>=ZONE && LA6_0<=KEY)||LA6_0==IF||(LA6_0>=RULE_ID && LA6_0<=RULE_QUOTED_ID)) ) {
            	        alt6=1;
            	    }
            	    else if ( (LA6_0==PRIMARY||LA6_0==UNIQUE) ) {
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

            otherlv_14=(Token)match(input,RightParenthesis,FOLLOW_2); 

            			newLeafNode(otherlv_14, grammarAccess.getTableAccess().getRightParenthesisKeyword_8());
            		

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


    // $ANTLR start "entryRuleColumn"
    // InternalSQLParser.g:293:1: entryRuleColumn returns [EObject current=null] : iv_ruleColumn= ruleColumn EOF ;
    public final EObject entryRuleColumn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumn = null;


        try {
            // InternalSQLParser.g:293:47: (iv_ruleColumn= ruleColumn EOF )
            // InternalSQLParser.g:294:2: iv_ruleColumn= ruleColumn EOF
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
    // InternalSQLParser.g:300:1: ruleColumn returns [EObject current=null] : ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* ) ;
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
            // InternalSQLParser.g:306:2: ( ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* ) )
            // InternalSQLParser.g:307:2: ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* )
            {
            // InternalSQLParser.g:307:2: ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )* )
            // InternalSQLParser.g:308:3: ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) ) ( (lv_constraint_15_0= ruleConstraint ) )*
            {
            // InternalSQLParser.g:308:3: ( (lv_name_0_0= ruleName ) )
            // InternalSQLParser.g:309:4: (lv_name_0_0= ruleName )
            {
            // InternalSQLParser.g:309:4: (lv_name_0_0= ruleName )
            // InternalSQLParser.g:310:5: lv_name_0_0= ruleName
            {

            					newCompositeNode(grammarAccess.getColumnAccess().getNameNameParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_12);
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

            // InternalSQLParser.g:327:3: ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) )
            int alt8=7;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // InternalSQLParser.g:328:4: ( (lv_simpleType_1_0= ruleAllTypes ) )
                    {
                    // InternalSQLParser.g:328:4: ( (lv_simpleType_1_0= ruleAllTypes ) )
                    // InternalSQLParser.g:329:5: (lv_simpleType_1_0= ruleAllTypes )
                    {
                    // InternalSQLParser.g:329:5: (lv_simpleType_1_0= ruleAllTypes )
                    // InternalSQLParser.g:330:6: lv_simpleType_1_0= ruleAllTypes
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeAllTypesEnumRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_13);
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
                    // InternalSQLParser.g:348:4: ( (lv_varchar_2_0= ruleVarchar ) )
                    {
                    // InternalSQLParser.g:348:4: ( (lv_varchar_2_0= ruleVarchar ) )
                    // InternalSQLParser.g:349:5: (lv_varchar_2_0= ruleVarchar )
                    {
                    // InternalSQLParser.g:349:5: (lv_varchar_2_0= ruleVarchar )
                    // InternalSQLParser.g:350:6: lv_varchar_2_0= ruleVarchar
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getVarcharVarcharParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_13);
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
                    // InternalSQLParser.g:368:4: ( (lv_numeric_3_0= ruleNumeric ) )
                    {
                    // InternalSQLParser.g:368:4: ( (lv_numeric_3_0= ruleNumeric ) )
                    // InternalSQLParser.g:369:5: (lv_numeric_3_0= ruleNumeric )
                    {
                    // InternalSQLParser.g:369:5: (lv_numeric_3_0= ruleNumeric )
                    // InternalSQLParser.g:370:6: lv_numeric_3_0= ruleNumeric
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getNumericNumericParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_13);
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
                    // InternalSQLParser.g:388:4: ( (lv_char_4_0= ruleChar ) )
                    {
                    // InternalSQLParser.g:388:4: ( (lv_char_4_0= ruleChar ) )
                    // InternalSQLParser.g:389:5: (lv_char_4_0= ruleChar )
                    {
                    // InternalSQLParser.g:389:5: (lv_char_4_0= ruleChar )
                    // InternalSQLParser.g:390:6: lv_char_4_0= ruleChar
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getCharCharParserRuleCall_1_3_0());
                    					
                    pushFollow(FOLLOW_13);
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
                    // InternalSQLParser.g:408:4: (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) )
                    {
                    // InternalSQLParser.g:408:4: (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) )
                    // InternalSQLParser.g:409:5: otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) )
                    {
                    otherlv_5=(Token)match(input,DOUBLE,FOLLOW_14); 

                    					newLeafNode(otherlv_5, grammarAccess.getColumnAccess().getDOUBLEKeyword_1_4_0());
                    				
                    // InternalSQLParser.g:413:5: ( (lv_simpleType_6_0= ruleDoublePrecision ) )
                    // InternalSQLParser.g:414:6: (lv_simpleType_6_0= ruleDoublePrecision )
                    {
                    // InternalSQLParser.g:414:6: (lv_simpleType_6_0= ruleDoublePrecision )
                    // InternalSQLParser.g:415:7: lv_simpleType_6_0= ruleDoublePrecision
                    {

                    							newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeDoublePrecisionEnumRuleCall_1_4_1_0());
                    						
                    pushFollow(FOLLOW_13);
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
                    // InternalSQLParser.g:434:4: (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) )
                    {
                    // InternalSQLParser.g:434:4: (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) )
                    // InternalSQLParser.g:435:5: otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) )
                    {
                    otherlv_7=(Token)match(input,TIMESTAMP,FOLLOW_15); 

                    					newLeafNode(otherlv_7, grammarAccess.getColumnAccess().getTIMESTAMPKeyword_1_5_0());
                    				
                    otherlv_8=(Token)match(input,WITHOUT,FOLLOW_16); 

                    					newLeafNode(otherlv_8, grammarAccess.getColumnAccess().getWITHOUTKeyword_1_5_1());
                    				
                    otherlv_9=(Token)match(input,TIME,FOLLOW_17); 

                    					newLeafNode(otherlv_9, grammarAccess.getColumnAccess().getTIMEKeyword_1_5_2());
                    				
                    // InternalSQLParser.g:447:5: ( (lv_simpleType_10_0= ruleZoneNoTz ) )
                    // InternalSQLParser.g:448:6: (lv_simpleType_10_0= ruleZoneNoTz )
                    {
                    // InternalSQLParser.g:448:6: (lv_simpleType_10_0= ruleZoneNoTz )
                    // InternalSQLParser.g:449:7: lv_simpleType_10_0= ruleZoneNoTz
                    {

                    							newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeZoneNoTzEnumRuleCall_1_5_3_0());
                    						
                    pushFollow(FOLLOW_13);
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
                    // InternalSQLParser.g:468:4: (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) )
                    {
                    // InternalSQLParser.g:468:4: (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) )
                    // InternalSQLParser.g:469:5: otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) )
                    {
                    otherlv_11=(Token)match(input,TIMESTAMP,FOLLOW_18); 

                    					newLeafNode(otherlv_11, grammarAccess.getColumnAccess().getTIMESTAMPKeyword_1_6_0());
                    				
                    otherlv_12=(Token)match(input,WITH,FOLLOW_16); 

                    					newLeafNode(otherlv_12, grammarAccess.getColumnAccess().getWITHKeyword_1_6_1());
                    				
                    otherlv_13=(Token)match(input,TIME,FOLLOW_17); 

                    					newLeafNode(otherlv_13, grammarAccess.getColumnAccess().getTIMEKeyword_1_6_2());
                    				
                    // InternalSQLParser.g:481:5: ( (lv_simpleType_14_0= ruleZoneTz ) )
                    // InternalSQLParser.g:482:6: (lv_simpleType_14_0= ruleZoneTz )
                    {
                    // InternalSQLParser.g:482:6: (lv_simpleType_14_0= ruleZoneTz )
                    // InternalSQLParser.g:483:7: lv_simpleType_14_0= ruleZoneTz
                    {

                    							newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeZoneTzEnumRuleCall_1_6_3_0());
                    						
                    pushFollow(FOLLOW_13);
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

            // InternalSQLParser.g:502:3: ( (lv_constraint_15_0= ruleConstraint ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==PRIMARY||LA9_0==UNIQUE||LA9_0==NOT) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalSQLParser.g:503:4: (lv_constraint_15_0= ruleConstraint )
            	    {
            	    // InternalSQLParser.g:503:4: (lv_constraint_15_0= ruleConstraint )
            	    // InternalSQLParser.g:504:5: lv_constraint_15_0= ruleConstraint
            	    {

            	    					newCompositeNode(grammarAccess.getColumnAccess().getConstraintConstraintParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_13);
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
            	    break loop9;
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
    // InternalSQLParser.g:525:1: entryRuleVarchar returns [EObject current=null] : iv_ruleVarchar= ruleVarchar EOF ;
    public final EObject entryRuleVarchar() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarchar = null;


        try {
            // InternalSQLParser.g:525:48: (iv_ruleVarchar= ruleVarchar EOF )
            // InternalSQLParser.g:526:2: iv_ruleVarchar= ruleVarchar EOF
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
    // InternalSQLParser.g:532:1: ruleVarchar returns [EObject current=null] : ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis ) ;
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
            // InternalSQLParser.g:538:2: ( ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis ) )
            // InternalSQLParser.g:539:2: ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis )
            {
            // InternalSQLParser.g:539:2: ( (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis )
            // InternalSQLParser.g:540:3: (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) ) otherlv_3= LeftParenthesis ( (lv_size_4_0= RULE_INT ) ) otherlv_5= RightParenthesis
            {
            // InternalSQLParser.g:540:3: (otherlv_0= VARCHAR | ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==VARCHAR) ) {
                alt10=1;
            }
            else if ( (LA10_0==CHARACTER) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // InternalSQLParser.g:541:4: otherlv_0= VARCHAR
                    {
                    otherlv_0=(Token)match(input,VARCHAR,FOLLOW_9); 

                    				newLeafNode(otherlv_0, grammarAccess.getVarcharAccess().getVARCHARKeyword_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:546:4: ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING )
                    {
                    // InternalSQLParser.g:546:4: ( ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING )
                    // InternalSQLParser.g:547:5: ( (lv_varying_1_0= CHARACTER ) ) otherlv_2= VARYING
                    {
                    // InternalSQLParser.g:547:5: ( (lv_varying_1_0= CHARACTER ) )
                    // InternalSQLParser.g:548:6: (lv_varying_1_0= CHARACTER )
                    {
                    // InternalSQLParser.g:548:6: (lv_varying_1_0= CHARACTER )
                    // InternalSQLParser.g:549:7: lv_varying_1_0= CHARACTER
                    {
                    lv_varying_1_0=(Token)match(input,CHARACTER,FOLLOW_19); 

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

            otherlv_3=(Token)match(input,LeftParenthesis,FOLLOW_20); 

            			newLeafNode(otherlv_3, grammarAccess.getVarcharAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:571:3: ( (lv_size_4_0= RULE_INT ) )
            // InternalSQLParser.g:572:4: (lv_size_4_0= RULE_INT )
            {
            // InternalSQLParser.g:572:4: (lv_size_4_0= RULE_INT )
            // InternalSQLParser.g:573:5: lv_size_4_0= RULE_INT
            {
            lv_size_4_0=(Token)match(input,RULE_INT,FOLLOW_21); 

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
    // InternalSQLParser.g:597:1: entryRuleNumeric returns [EObject current=null] : iv_ruleNumeric= ruleNumeric EOF ;
    public final EObject entryRuleNumeric() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNumeric = null;


        try {
            // InternalSQLParser.g:597:48: (iv_ruleNumeric= ruleNumeric EOF )
            // InternalSQLParser.g:598:2: iv_ruleNumeric= ruleNumeric EOF
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
    // InternalSQLParser.g:604:1: ruleNumeric returns [EObject current=null] : ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis ) ;
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
            // InternalSQLParser.g:610:2: ( ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis ) )
            // InternalSQLParser.g:611:2: ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis )
            {
            // InternalSQLParser.g:611:2: ( ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis )
            // InternalSQLParser.g:612:3: ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC ) otherlv_2= LeftParenthesis ( (lv_precision_3_0= RULE_INT ) ) (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )? otherlv_6= RightParenthesis
            {
            // InternalSQLParser.g:612:3: ( ( (lv_decimal_0_0= DECIMAL ) ) | otherlv_1= NUMERIC )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==DECIMAL) ) {
                alt11=1;
            }
            else if ( (LA11_0==NUMERIC) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalSQLParser.g:613:4: ( (lv_decimal_0_0= DECIMAL ) )
                    {
                    // InternalSQLParser.g:613:4: ( (lv_decimal_0_0= DECIMAL ) )
                    // InternalSQLParser.g:614:5: (lv_decimal_0_0= DECIMAL )
                    {
                    // InternalSQLParser.g:614:5: (lv_decimal_0_0= DECIMAL )
                    // InternalSQLParser.g:615:6: lv_decimal_0_0= DECIMAL
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
                    // InternalSQLParser.g:628:4: otherlv_1= NUMERIC
                    {
                    otherlv_1=(Token)match(input,NUMERIC,FOLLOW_9); 

                    				newLeafNode(otherlv_1, grammarAccess.getNumericAccess().getNUMERICKeyword_0_1());
                    			

                    }
                    break;

            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_20); 

            			newLeafNode(otherlv_2, grammarAccess.getNumericAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:637:3: ( (lv_precision_3_0= RULE_INT ) )
            // InternalSQLParser.g:638:4: (lv_precision_3_0= RULE_INT )
            {
            // InternalSQLParser.g:638:4: (lv_precision_3_0= RULE_INT )
            // InternalSQLParser.g:639:5: lv_precision_3_0= RULE_INT
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

            // InternalSQLParser.g:655:3: (otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==Comma) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalSQLParser.g:656:4: otherlv_4= Comma ( (lv_scale_5_0= RULE_INT ) )
                    {
                    otherlv_4=(Token)match(input,Comma,FOLLOW_20); 

                    				newLeafNode(otherlv_4, grammarAccess.getNumericAccess().getCommaKeyword_3_0());
                    			
                    // InternalSQLParser.g:660:4: ( (lv_scale_5_0= RULE_INT ) )
                    // InternalSQLParser.g:661:5: (lv_scale_5_0= RULE_INT )
                    {
                    // InternalSQLParser.g:661:5: (lv_scale_5_0= RULE_INT )
                    // InternalSQLParser.g:662:6: lv_scale_5_0= RULE_INT
                    {
                    lv_scale_5_0=(Token)match(input,RULE_INT,FOLLOW_21); 

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
    // InternalSQLParser.g:687:1: entryRuleChar returns [EObject current=null] : iv_ruleChar= ruleChar EOF ;
    public final EObject entryRuleChar() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleChar = null;


        try {
            // InternalSQLParser.g:687:45: (iv_ruleChar= ruleChar EOF )
            // InternalSQLParser.g:688:2: iv_ruleChar= ruleChar EOF
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
    // InternalSQLParser.g:694:1: ruleChar returns [EObject current=null] : ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis ) ;
    public final EObject ruleChar() throws RecognitionException {
        EObject current = null;

        Token lv_character_0_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token lv_size_3_0=null;
        Token otherlv_4=null;


        	enterRule();

        try {
            // InternalSQLParser.g:700:2: ( ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis ) )
            // InternalSQLParser.g:701:2: ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis )
            {
            // InternalSQLParser.g:701:2: ( ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis )
            // InternalSQLParser.g:702:3: ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR ) otherlv_2= LeftParenthesis ( (lv_size_3_0= RULE_INT ) ) otherlv_4= RightParenthesis
            {
            // InternalSQLParser.g:702:3: ( ( (lv_character_0_0= CHARACTER ) ) | otherlv_1= CHAR )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==CHARACTER) ) {
                alt13=1;
            }
            else if ( (LA13_0==CHAR) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // InternalSQLParser.g:703:4: ( (lv_character_0_0= CHARACTER ) )
                    {
                    // InternalSQLParser.g:703:4: ( (lv_character_0_0= CHARACTER ) )
                    // InternalSQLParser.g:704:5: (lv_character_0_0= CHARACTER )
                    {
                    // InternalSQLParser.g:704:5: (lv_character_0_0= CHARACTER )
                    // InternalSQLParser.g:705:6: lv_character_0_0= CHARACTER
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
                    // InternalSQLParser.g:718:4: otherlv_1= CHAR
                    {
                    otherlv_1=(Token)match(input,CHAR,FOLLOW_9); 

                    				newLeafNode(otherlv_1, grammarAccess.getCharAccess().getCHARKeyword_0_1());
                    			

                    }
                    break;

            }

            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_20); 

            			newLeafNode(otherlv_2, grammarAccess.getCharAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:727:3: ( (lv_size_3_0= RULE_INT ) )
            // InternalSQLParser.g:728:4: (lv_size_3_0= RULE_INT )
            {
            // InternalSQLParser.g:728:4: (lv_size_3_0= RULE_INT )
            // InternalSQLParser.g:729:5: lv_size_3_0= RULE_INT
            {
            lv_size_3_0=(Token)match(input,RULE_INT,FOLLOW_21); 

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


    // $ANTLR start "entryRuleConstraint"
    // InternalSQLParser.g:753:1: entryRuleConstraint returns [EObject current=null] : iv_ruleConstraint= ruleConstraint EOF ;
    public final EObject entryRuleConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstraint = null;


        try {
            // InternalSQLParser.g:753:51: (iv_ruleConstraint= ruleConstraint EOF )
            // InternalSQLParser.g:754:2: iv_ruleConstraint= ruleConstraint EOF
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
    // InternalSQLParser.g:760:1: ruleConstraint returns [EObject current=null] : (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull ) ;
    public final EObject ruleConstraint() throws RecognitionException {
        EObject current = null;

        EObject this_PrimaryAnnotation_0 = null;

        EObject this_UniqueAnnotation_1 = null;

        EObject this_NotNull_2 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:766:2: ( (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull ) )
            // InternalSQLParser.g:767:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull )
            {
            // InternalSQLParser.g:767:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull )
            int alt14=3;
            switch ( input.LA(1) ) {
            case PRIMARY:
                {
                alt14=1;
                }
                break;
            case UNIQUE:
                {
                alt14=2;
                }
                break;
            case NOT:
                {
                alt14=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // InternalSQLParser.g:768:3: this_PrimaryAnnotation_0= rulePrimaryAnnotation
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
                    // InternalSQLParser.g:777:3: this_UniqueAnnotation_1= ruleUniqueAnnotation
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
                    // InternalSQLParser.g:786:3: this_NotNull_2= ruleNotNull
                    {

                    			newCompositeNode(grammarAccess.getConstraintAccess().getNotNullParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_NotNull_2=ruleNotNull();

                    state._fsp--;


                    			current = this_NotNull_2;
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
    // InternalSQLParser.g:798:1: entryRulePrimaryAnnotation returns [EObject current=null] : iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF ;
    public final EObject entryRulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryAnnotation = null;


        try {
            // InternalSQLParser.g:798:58: (iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF )
            // InternalSQLParser.g:799:2: iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF
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
    // InternalSQLParser.g:805:1: rulePrimaryAnnotation returns [EObject current=null] : ( () otherlv_1= PRIMARY otherlv_2= KEY ) ;
    public final EObject rulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSQLParser.g:811:2: ( ( () otherlv_1= PRIMARY otherlv_2= KEY ) )
            // InternalSQLParser.g:812:2: ( () otherlv_1= PRIMARY otherlv_2= KEY )
            {
            // InternalSQLParser.g:812:2: ( () otherlv_1= PRIMARY otherlv_2= KEY )
            // InternalSQLParser.g:813:3: () otherlv_1= PRIMARY otherlv_2= KEY
            {
            // InternalSQLParser.g:813:3: ()
            // InternalSQLParser.g:814:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getPrimaryAnnotationAccess().getPrimaryAnnotationAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,PRIMARY,FOLLOW_22); 

            			newLeafNode(otherlv_1, grammarAccess.getPrimaryAnnotationAccess().getPRIMARYKeyword_1());
            		
            otherlv_2=(Token)match(input,KEY,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getPrimaryAnnotationAccess().getKEYKeyword_2());
            		

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
    // InternalSQLParser.g:832:1: entryRuleUniqueAnnotation returns [EObject current=null] : iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF ;
    public final EObject entryRuleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueAnnotation = null;


        try {
            // InternalSQLParser.g:832:57: (iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF )
            // InternalSQLParser.g:833:2: iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF
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
    // InternalSQLParser.g:839:1: ruleUniqueAnnotation returns [EObject current=null] : ( () otherlv_1= UNIQUE ) ;
    public final EObject ruleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalSQLParser.g:845:2: ( ( () otherlv_1= UNIQUE ) )
            // InternalSQLParser.g:846:2: ( () otherlv_1= UNIQUE )
            {
            // InternalSQLParser.g:846:2: ( () otherlv_1= UNIQUE )
            // InternalSQLParser.g:847:3: () otherlv_1= UNIQUE
            {
            // InternalSQLParser.g:847:3: ()
            // InternalSQLParser.g:848:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getUniqueAnnotationAccess().getUniqueAnnotationAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,UNIQUE,FOLLOW_2); 

            			newLeafNode(otherlv_1, grammarAccess.getUniqueAnnotationAccess().getUNIQUEKeyword_1());
            		

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
    // InternalSQLParser.g:862:1: entryRuleNotNull returns [EObject current=null] : iv_ruleNotNull= ruleNotNull EOF ;
    public final EObject entryRuleNotNull() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNotNull = null;


        try {
            // InternalSQLParser.g:862:48: (iv_ruleNotNull= ruleNotNull EOF )
            // InternalSQLParser.g:863:2: iv_ruleNotNull= ruleNotNull EOF
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
    // InternalSQLParser.g:869:1: ruleNotNull returns [EObject current=null] : ( () otherlv_1= NOT otherlv_2= NULL ) ;
    public final EObject ruleNotNull() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSQLParser.g:875:2: ( ( () otherlv_1= NOT otherlv_2= NULL ) )
            // InternalSQLParser.g:876:2: ( () otherlv_1= NOT otherlv_2= NULL )
            {
            // InternalSQLParser.g:876:2: ( () otherlv_1= NOT otherlv_2= NULL )
            // InternalSQLParser.g:877:3: () otherlv_1= NOT otherlv_2= NULL
            {
            // InternalSQLParser.g:877:3: ()
            // InternalSQLParser.g:878:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getNotNullAccess().getNotNullAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,NOT,FOLLOW_23); 

            			newLeafNode(otherlv_1, grammarAccess.getNotNullAccess().getNOTKeyword_1());
            		
            otherlv_2=(Token)match(input,NULL,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getNotNullAccess().getNULLKeyword_2());
            		

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


    // $ANTLR start "entryRuleTableConstraint"
    // InternalSQLParser.g:896:1: entryRuleTableConstraint returns [EObject current=null] : iv_ruleTableConstraint= ruleTableConstraint EOF ;
    public final EObject entryRuleTableConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableConstraint = null;


        try {
            // InternalSQLParser.g:896:56: (iv_ruleTableConstraint= ruleTableConstraint EOF )
            // InternalSQLParser.g:897:2: iv_ruleTableConstraint= ruleTableConstraint EOF
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
    // InternalSQLParser.g:903:1: ruleTableConstraint returns [EObject current=null] : (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey ) ;
    public final EObject ruleTableConstraint() throws RecognitionException {
        EObject current = null;

        EObject this_PrimaryKey_0 = null;

        EObject this_UniqueKey_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:909:2: ( (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey ) )
            // InternalSQLParser.g:910:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey )
            {
            // InternalSQLParser.g:910:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==PRIMARY) ) {
                alt15=1;
            }
            else if ( (LA15_0==UNIQUE) ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalSQLParser.g:911:3: this_PrimaryKey_0= rulePrimaryKey
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
                    // InternalSQLParser.g:920:3: this_UniqueKey_1= ruleUniqueKey
                    {

                    			newCompositeNode(grammarAccess.getTableConstraintAccess().getUniqueKeyParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_UniqueKey_1=ruleUniqueKey();

                    state._fsp--;


                    			current = this_UniqueKey_1;
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


    // $ANTLR start "entryRulePrimaryKey"
    // InternalSQLParser.g:932:1: entryRulePrimaryKey returns [EObject current=null] : iv_rulePrimaryKey= rulePrimaryKey EOF ;
    public final EObject entryRulePrimaryKey() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryKey = null;


        try {
            // InternalSQLParser.g:932:51: (iv_rulePrimaryKey= rulePrimaryKey EOF )
            // InternalSQLParser.g:933:2: iv_rulePrimaryKey= rulePrimaryKey EOF
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
    // InternalSQLParser.g:939:1: rulePrimaryKey returns [EObject current=null] : (otherlv_0= PRIMARY otherlv_1= KEY otherlv_2= LeftParenthesis ( ( ruleName ) ) (otherlv_4= Comma ( ( ruleName ) ) )* otherlv_6= RightParenthesis ) ;
    public final EObject rulePrimaryKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;


        	enterRule();

        try {
            // InternalSQLParser.g:945:2: ( (otherlv_0= PRIMARY otherlv_1= KEY otherlv_2= LeftParenthesis ( ( ruleName ) ) (otherlv_4= Comma ( ( ruleName ) ) )* otherlv_6= RightParenthesis ) )
            // InternalSQLParser.g:946:2: (otherlv_0= PRIMARY otherlv_1= KEY otherlv_2= LeftParenthesis ( ( ruleName ) ) (otherlv_4= Comma ( ( ruleName ) ) )* otherlv_6= RightParenthesis )
            {
            // InternalSQLParser.g:946:2: (otherlv_0= PRIMARY otherlv_1= KEY otherlv_2= LeftParenthesis ( ( ruleName ) ) (otherlv_4= Comma ( ( ruleName ) ) )* otherlv_6= RightParenthesis )
            // InternalSQLParser.g:947:3: otherlv_0= PRIMARY otherlv_1= KEY otherlv_2= LeftParenthesis ( ( ruleName ) ) (otherlv_4= Comma ( ( ruleName ) ) )* otherlv_6= RightParenthesis
            {
            otherlv_0=(Token)match(input,PRIMARY,FOLLOW_22); 

            			newLeafNode(otherlv_0, grammarAccess.getPrimaryKeyAccess().getPRIMARYKeyword_0());
            		
            otherlv_1=(Token)match(input,KEY,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getPrimaryKeyAccess().getKEYKeyword_1());
            		
            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getPrimaryKeyAccess().getLeftParenthesisKeyword_2());
            		
            // InternalSQLParser.g:959:3: ( ( ruleName ) )
            // InternalSQLParser.g:960:4: ( ruleName )
            {
            // InternalSQLParser.g:960:4: ( ruleName )
            // InternalSQLParser.g:961:5: ruleName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPrimaryKeyRule());
            					}
            				

            					newCompositeNode(grammarAccess.getPrimaryKeyAccess().getColumnsColumnCrossReference_3_0());
            				
            pushFollow(FOLLOW_11);
            ruleName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSQLParser.g:975:3: (otherlv_4= Comma ( ( ruleName ) ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==Comma) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalSQLParser.g:976:4: otherlv_4= Comma ( ( ruleName ) )
            	    {
            	    otherlv_4=(Token)match(input,Comma,FOLLOW_5); 

            	    				newLeafNode(otherlv_4, grammarAccess.getPrimaryKeyAccess().getCommaKeyword_4_0());
            	    			
            	    // InternalSQLParser.g:980:4: ( ( ruleName ) )
            	    // InternalSQLParser.g:981:5: ( ruleName )
            	    {
            	    // InternalSQLParser.g:981:5: ( ruleName )
            	    // InternalSQLParser.g:982:6: ruleName
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getPrimaryKeyRule());
            	    						}
            	    					

            	    						newCompositeNode(grammarAccess.getPrimaryKeyAccess().getColumnsColumnCrossReference_4_1_0());
            	    					
            	    pushFollow(FOLLOW_11);
            	    ruleName();

            	    state._fsp--;


            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            otherlv_6=(Token)match(input,RightParenthesis,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getPrimaryKeyAccess().getRightParenthesisKeyword_5());
            		

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
    // InternalSQLParser.g:1005:1: entryRuleUniqueKey returns [EObject current=null] : iv_ruleUniqueKey= ruleUniqueKey EOF ;
    public final EObject entryRuleUniqueKey() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueKey = null;


        try {
            // InternalSQLParser.g:1005:50: (iv_ruleUniqueKey= ruleUniqueKey EOF )
            // InternalSQLParser.g:1006:2: iv_ruleUniqueKey= ruleUniqueKey EOF
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
    // InternalSQLParser.g:1012:1: ruleUniqueKey returns [EObject current=null] : (otherlv_0= UNIQUE otherlv_1= LeftParenthesis ( ( ruleName ) ) (otherlv_3= Comma ( ( ruleName ) ) )* otherlv_5= RightParenthesis ) ;
    public final EObject ruleUniqueKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalSQLParser.g:1018:2: ( (otherlv_0= UNIQUE otherlv_1= LeftParenthesis ( ( ruleName ) ) (otherlv_3= Comma ( ( ruleName ) ) )* otherlv_5= RightParenthesis ) )
            // InternalSQLParser.g:1019:2: (otherlv_0= UNIQUE otherlv_1= LeftParenthesis ( ( ruleName ) ) (otherlv_3= Comma ( ( ruleName ) ) )* otherlv_5= RightParenthesis )
            {
            // InternalSQLParser.g:1019:2: (otherlv_0= UNIQUE otherlv_1= LeftParenthesis ( ( ruleName ) ) (otherlv_3= Comma ( ( ruleName ) ) )* otherlv_5= RightParenthesis )
            // InternalSQLParser.g:1020:3: otherlv_0= UNIQUE otherlv_1= LeftParenthesis ( ( ruleName ) ) (otherlv_3= Comma ( ( ruleName ) ) )* otherlv_5= RightParenthesis
            {
            otherlv_0=(Token)match(input,UNIQUE,FOLLOW_9); 

            			newLeafNode(otherlv_0, grammarAccess.getUniqueKeyAccess().getUNIQUEKeyword_0());
            		
            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getUniqueKeyAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:1028:3: ( ( ruleName ) )
            // InternalSQLParser.g:1029:4: ( ruleName )
            {
            // InternalSQLParser.g:1029:4: ( ruleName )
            // InternalSQLParser.g:1030:5: ruleName
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getUniqueKeyRule());
            					}
            				

            					newCompositeNode(grammarAccess.getUniqueKeyAccess().getColumnsColumnCrossReference_2_0());
            				
            pushFollow(FOLLOW_11);
            ruleName();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSQLParser.g:1044:3: (otherlv_3= Comma ( ( ruleName ) ) )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==Comma) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // InternalSQLParser.g:1045:4: otherlv_3= Comma ( ( ruleName ) )
            	    {
            	    otherlv_3=(Token)match(input,Comma,FOLLOW_5); 

            	    				newLeafNode(otherlv_3, grammarAccess.getUniqueKeyAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalSQLParser.g:1049:4: ( ( ruleName ) )
            	    // InternalSQLParser.g:1050:5: ( ruleName )
            	    {
            	    // InternalSQLParser.g:1050:5: ( ruleName )
            	    // InternalSQLParser.g:1051:6: ruleName
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getUniqueKeyRule());
            	    						}
            	    					

            	    						newCompositeNode(grammarAccess.getUniqueKeyAccess().getColumnsColumnCrossReference_3_1_0());
            	    					
            	    pushFollow(FOLLOW_11);
            	    ruleName();

            	    state._fsp--;


            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            otherlv_5=(Token)match(input,RightParenthesis,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getUniqueKeyAccess().getRightParenthesisKeyword_4());
            		

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


    // $ANTLR start "entryRuleName"
    // InternalSQLParser.g:1074:1: entryRuleName returns [String current=null] : iv_ruleName= ruleName EOF ;
    public final String entryRuleName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleName = null;


        try {
            // InternalSQLParser.g:1074:44: (iv_ruleName= ruleName EOF )
            // InternalSQLParser.g:1075:2: iv_ruleName= ruleName EOF
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
    // InternalSQLParser.g:1081:1: ruleName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA ) ;
    public final AntlrDatatypeRuleToken ruleName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token this_QUOTED_ID_1=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSQLParser.g:1087:2: ( (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA ) )
            // InternalSQLParser.g:1088:2: (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA )
            {
            // InternalSQLParser.g:1088:2: (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= INTEGER | kw= SMALLINT | kw= NUMERIC | kw= REAL | kw= FLOAT | kw= PRECISION | kw= CHAR | kw= CHARACTER | kw= TIME | kw= DOUBLE | kw= VARYING | kw= WITHOUT | kw= ZONE | kw= UUID | kw= DATE | kw= SERIAL | kw= BIGSERIAL | kw= TIMESTAMPTZ | kw= JSONB | kw= BYTEA )
            int alt18=32;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt18=1;
                }
                break;
            case RULE_QUOTED_ID:
                {
                alt18=2;
                }
                break;
            case KEY:
                {
                alt18=3;
                }
                break;
            case TEXT:
                {
                alt18=4;
                }
                break;
            case IF:
                {
                alt18=5;
                }
                break;
            case EXISTS:
                {
                alt18=6;
                }
                break;
            case INT:
                {
                alt18=7;
                }
                break;
            case BIGINT:
                {
                alt18=8;
                }
                break;
            case DECIMAL:
                {
                alt18=9;
                }
                break;
            case TIMESTAMP:
                {
                alt18=10;
                }
                break;
            case BOOLEAN:
                {
                alt18=11;
                }
                break;
            case VARCHAR:
                {
                alt18=12;
                }
                break;
            case INTEGER:
                {
                alt18=13;
                }
                break;
            case SMALLINT:
                {
                alt18=14;
                }
                break;
            case NUMERIC:
                {
                alt18=15;
                }
                break;
            case REAL:
                {
                alt18=16;
                }
                break;
            case FLOAT:
                {
                alt18=17;
                }
                break;
            case PRECISION:
                {
                alt18=18;
                }
                break;
            case CHAR:
                {
                alt18=19;
                }
                break;
            case CHARACTER:
                {
                alt18=20;
                }
                break;
            case TIME:
                {
                alt18=21;
                }
                break;
            case DOUBLE:
                {
                alt18=22;
                }
                break;
            case VARYING:
                {
                alt18=23;
                }
                break;
            case WITHOUT:
                {
                alt18=24;
                }
                break;
            case ZONE:
                {
                alt18=25;
                }
                break;
            case UUID:
                {
                alt18=26;
                }
                break;
            case DATE:
                {
                alt18=27;
                }
                break;
            case SERIAL:
                {
                alt18=28;
                }
                break;
            case BIGSERIAL:
                {
                alt18=29;
                }
                break;
            case TIMESTAMPTZ:
                {
                alt18=30;
                }
                break;
            case JSONB:
                {
                alt18=31;
                }
                break;
            case BYTEA:
                {
                alt18=32;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalSQLParser.g:1089:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getNameAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:1097:3: this_QUOTED_ID_1= RULE_QUOTED_ID
                    {
                    this_QUOTED_ID_1=(Token)match(input,RULE_QUOTED_ID,FOLLOW_2); 

                    			current.merge(this_QUOTED_ID_1);
                    		

                    			newLeafNode(this_QUOTED_ID_1, grammarAccess.getNameAccess().getQUOTED_IDTerminalRuleCall_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:1105:3: kw= KEY
                    {
                    kw=(Token)match(input,KEY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getKEYKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:1111:3: kw= TEXT
                    {
                    kw=(Token)match(input,TEXT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getTEXTKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:1117:3: kw= IF
                    {
                    kw=(Token)match(input,IF,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getIFKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:1123:3: kw= EXISTS
                    {
                    kw=(Token)match(input,EXISTS,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getEXISTSKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:1129:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getINTKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalSQLParser.g:1135:3: kw= BIGINT
                    {
                    kw=(Token)match(input,BIGINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getBIGINTKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalSQLParser.g:1141:3: kw= DECIMAL
                    {
                    kw=(Token)match(input,DECIMAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getDECIMALKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalSQLParser.g:1147:3: kw= TIMESTAMP
                    {
                    kw=(Token)match(input,TIMESTAMP,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getTIMESTAMPKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalSQLParser.g:1153:3: kw= BOOLEAN
                    {
                    kw=(Token)match(input,BOOLEAN,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getBOOLEANKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalSQLParser.g:1159:3: kw= VARCHAR
                    {
                    kw=(Token)match(input,VARCHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getVARCHARKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalSQLParser.g:1165:3: kw= INTEGER
                    {
                    kw=(Token)match(input,INTEGER,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getINTEGERKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalSQLParser.g:1171:3: kw= SMALLINT
                    {
                    kw=(Token)match(input,SMALLINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getSMALLINTKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalSQLParser.g:1177:3: kw= NUMERIC
                    {
                    kw=(Token)match(input,NUMERIC,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getNUMERICKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalSQLParser.g:1183:3: kw= REAL
                    {
                    kw=(Token)match(input,REAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getREALKeyword_15());
                    		

                    }
                    break;
                case 17 :
                    // InternalSQLParser.g:1189:3: kw= FLOAT
                    {
                    kw=(Token)match(input,FLOAT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getFLOATKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalSQLParser.g:1195:3: kw= PRECISION
                    {
                    kw=(Token)match(input,PRECISION,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getPRECISIONKeyword_17());
                    		

                    }
                    break;
                case 19 :
                    // InternalSQLParser.g:1201:3: kw= CHAR
                    {
                    kw=(Token)match(input,CHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getCHARKeyword_18());
                    		

                    }
                    break;
                case 20 :
                    // InternalSQLParser.g:1207:3: kw= CHARACTER
                    {
                    kw=(Token)match(input,CHARACTER,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getCHARACTERKeyword_19());
                    		

                    }
                    break;
                case 21 :
                    // InternalSQLParser.g:1213:3: kw= TIME
                    {
                    kw=(Token)match(input,TIME,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getTIMEKeyword_20());
                    		

                    }
                    break;
                case 22 :
                    // InternalSQLParser.g:1219:3: kw= DOUBLE
                    {
                    kw=(Token)match(input,DOUBLE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getDOUBLEKeyword_21());
                    		

                    }
                    break;
                case 23 :
                    // InternalSQLParser.g:1225:3: kw= VARYING
                    {
                    kw=(Token)match(input,VARYING,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getVARYINGKeyword_22());
                    		

                    }
                    break;
                case 24 :
                    // InternalSQLParser.g:1231:3: kw= WITHOUT
                    {
                    kw=(Token)match(input,WITHOUT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getWITHOUTKeyword_23());
                    		

                    }
                    break;
                case 25 :
                    // InternalSQLParser.g:1237:3: kw= ZONE
                    {
                    kw=(Token)match(input,ZONE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getZONEKeyword_24());
                    		

                    }
                    break;
                case 26 :
                    // InternalSQLParser.g:1243:3: kw= UUID
                    {
                    kw=(Token)match(input,UUID,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getUUIDKeyword_25());
                    		

                    }
                    break;
                case 27 :
                    // InternalSQLParser.g:1249:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getDATEKeyword_26());
                    		

                    }
                    break;
                case 28 :
                    // InternalSQLParser.g:1255:3: kw= SERIAL
                    {
                    kw=(Token)match(input,SERIAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getSERIALKeyword_27());
                    		

                    }
                    break;
                case 29 :
                    // InternalSQLParser.g:1261:3: kw= BIGSERIAL
                    {
                    kw=(Token)match(input,BIGSERIAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getBIGSERIALKeyword_28());
                    		

                    }
                    break;
                case 30 :
                    // InternalSQLParser.g:1267:3: kw= TIMESTAMPTZ
                    {
                    kw=(Token)match(input,TIMESTAMPTZ,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getTIMESTAMPTZKeyword_29());
                    		

                    }
                    break;
                case 31 :
                    // InternalSQLParser.g:1273:3: kw= JSONB
                    {
                    kw=(Token)match(input,JSONB,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getJSONBKeyword_30());
                    		

                    }
                    break;
                case 32 :
                    // InternalSQLParser.g:1279:3: kw= BYTEA
                    {
                    kw=(Token)match(input,BYTEA,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getBYTEAKeyword_31());
                    		

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


    // $ANTLR start "ruleAllTypes"
    // InternalSQLParser.g:1288:1: ruleAllTypes returns [Enumerator current=null] : ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) ) ;
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
            // InternalSQLParser.g:1294:2: ( ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) ) )
            // InternalSQLParser.g:1295:2: ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) )
            {
            // InternalSQLParser.g:1295:2: ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) | (enumLiteral_8= INTEGER ) | (enumLiteral_9= SMALLINT ) | (enumLiteral_10= SERIAL ) | (enumLiteral_11= BIGSERIAL ) | (enumLiteral_12= NUMERIC ) | (enumLiteral_13= REAL ) | (enumLiteral_14= FLOAT ) | (enumLiteral_15= TIMESTAMPTZ ) | (enumLiteral_16= JSONB ) | (enumLiteral_17= BYTEA ) )
            int alt19=18;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt19=1;
                }
                break;
            case BIGINT:
                {
                alt19=2;
                }
                break;
            case UUID:
                {
                alt19=3;
                }
                break;
            case DECIMAL:
                {
                alt19=4;
                }
                break;
            case TIMESTAMP:
                {
                alt19=5;
                }
                break;
            case DATE:
                {
                alt19=6;
                }
                break;
            case BOOLEAN:
                {
                alt19=7;
                }
                break;
            case TEXT:
                {
                alt19=8;
                }
                break;
            case INTEGER:
                {
                alt19=9;
                }
                break;
            case SMALLINT:
                {
                alt19=10;
                }
                break;
            case SERIAL:
                {
                alt19=11;
                }
                break;
            case BIGSERIAL:
                {
                alt19=12;
                }
                break;
            case NUMERIC:
                {
                alt19=13;
                }
                break;
            case REAL:
                {
                alt19=14;
                }
                break;
            case FLOAT:
                {
                alt19=15;
                }
                break;
            case TIMESTAMPTZ:
                {
                alt19=16;
                }
                break;
            case JSONB:
                {
                alt19=17;
                }
                break;
            case BYTEA:
                {
                alt19=18;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // InternalSQLParser.g:1296:3: (enumLiteral_0= INT )
                    {
                    // InternalSQLParser.g:1296:3: (enumLiteral_0= INT )
                    // InternalSQLParser.g:1297:4: enumLiteral_0= INT
                    {
                    enumLiteral_0=(Token)match(input,INT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:1304:3: (enumLiteral_1= BIGINT )
                    {
                    // InternalSQLParser.g:1304:3: (enumLiteral_1= BIGINT )
                    // InternalSQLParser.g:1305:4: enumLiteral_1= BIGINT
                    {
                    enumLiteral_1=(Token)match(input,BIGINT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:1312:3: (enumLiteral_2= UUID )
                    {
                    // InternalSQLParser.g:1312:3: (enumLiteral_2= UUID )
                    // InternalSQLParser.g:1313:4: enumLiteral_2= UUID
                    {
                    enumLiteral_2=(Token)match(input,UUID,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:1320:3: (enumLiteral_3= DECIMAL )
                    {
                    // InternalSQLParser.g:1320:3: (enumLiteral_3= DECIMAL )
                    // InternalSQLParser.g:1321:4: enumLiteral_3= DECIMAL
                    {
                    enumLiteral_3=(Token)match(input,DECIMAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:1328:3: (enumLiteral_4= TIMESTAMP )
                    {
                    // InternalSQLParser.g:1328:3: (enumLiteral_4= TIMESTAMP )
                    // InternalSQLParser.g:1329:4: enumLiteral_4= TIMESTAMP
                    {
                    enumLiteral_4=(Token)match(input,TIMESTAMP,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:1336:3: (enumLiteral_5= DATE )
                    {
                    // InternalSQLParser.g:1336:3: (enumLiteral_5= DATE )
                    // InternalSQLParser.g:1337:4: enumLiteral_5= DATE
                    {
                    enumLiteral_5=(Token)match(input,DATE,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:1344:3: (enumLiteral_6= BOOLEAN )
                    {
                    // InternalSQLParser.g:1344:3: (enumLiteral_6= BOOLEAN )
                    // InternalSQLParser.g:1345:4: enumLiteral_6= BOOLEAN
                    {
                    enumLiteral_6=(Token)match(input,BOOLEAN,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalSQLParser.g:1352:3: (enumLiteral_7= TEXT )
                    {
                    // InternalSQLParser.g:1352:3: (enumLiteral_7= TEXT )
                    // InternalSQLParser.g:1353:4: enumLiteral_7= TEXT
                    {
                    enumLiteral_7=(Token)match(input,TEXT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTEXTEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getAllTypesAccess().getTEXTEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;
                case 9 :
                    // InternalSQLParser.g:1360:3: (enumLiteral_8= INTEGER )
                    {
                    // InternalSQLParser.g:1360:3: (enumLiteral_8= INTEGER )
                    // InternalSQLParser.g:1361:4: enumLiteral_8= INTEGER
                    {
                    enumLiteral_8=(Token)match(input,INTEGER,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getINTEGEREnumLiteralDeclaration_8().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_8, grammarAccess.getAllTypesAccess().getINTEGEREnumLiteralDeclaration_8());
                    			

                    }


                    }
                    break;
                case 10 :
                    // InternalSQLParser.g:1368:3: (enumLiteral_9= SMALLINT )
                    {
                    // InternalSQLParser.g:1368:3: (enumLiteral_9= SMALLINT )
                    // InternalSQLParser.g:1369:4: enumLiteral_9= SMALLINT
                    {
                    enumLiteral_9=(Token)match(input,SMALLINT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getSMALLINTEnumLiteralDeclaration_9().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_9, grammarAccess.getAllTypesAccess().getSMALLINTEnumLiteralDeclaration_9());
                    			

                    }


                    }
                    break;
                case 11 :
                    // InternalSQLParser.g:1376:3: (enumLiteral_10= SERIAL )
                    {
                    // InternalSQLParser.g:1376:3: (enumLiteral_10= SERIAL )
                    // InternalSQLParser.g:1377:4: enumLiteral_10= SERIAL
                    {
                    enumLiteral_10=(Token)match(input,SERIAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getSERIALEnumLiteralDeclaration_10().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_10, grammarAccess.getAllTypesAccess().getSERIALEnumLiteralDeclaration_10());
                    			

                    }


                    }
                    break;
                case 12 :
                    // InternalSQLParser.g:1384:3: (enumLiteral_11= BIGSERIAL )
                    {
                    // InternalSQLParser.g:1384:3: (enumLiteral_11= BIGSERIAL )
                    // InternalSQLParser.g:1385:4: enumLiteral_11= BIGSERIAL
                    {
                    enumLiteral_11=(Token)match(input,BIGSERIAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBIGSERIALEnumLiteralDeclaration_11().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_11, grammarAccess.getAllTypesAccess().getBIGSERIALEnumLiteralDeclaration_11());
                    			

                    }


                    }
                    break;
                case 13 :
                    // InternalSQLParser.g:1392:3: (enumLiteral_12= NUMERIC )
                    {
                    // InternalSQLParser.g:1392:3: (enumLiteral_12= NUMERIC )
                    // InternalSQLParser.g:1393:4: enumLiteral_12= NUMERIC
                    {
                    enumLiteral_12=(Token)match(input,NUMERIC,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getNUMERICEnumLiteralDeclaration_12().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_12, grammarAccess.getAllTypesAccess().getNUMERICEnumLiteralDeclaration_12());
                    			

                    }


                    }
                    break;
                case 14 :
                    // InternalSQLParser.g:1400:3: (enumLiteral_13= REAL )
                    {
                    // InternalSQLParser.g:1400:3: (enumLiteral_13= REAL )
                    // InternalSQLParser.g:1401:4: enumLiteral_13= REAL
                    {
                    enumLiteral_13=(Token)match(input,REAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getREALEnumLiteralDeclaration_13().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_13, grammarAccess.getAllTypesAccess().getREALEnumLiteralDeclaration_13());
                    			

                    }


                    }
                    break;
                case 15 :
                    // InternalSQLParser.g:1408:3: (enumLiteral_14= FLOAT )
                    {
                    // InternalSQLParser.g:1408:3: (enumLiteral_14= FLOAT )
                    // InternalSQLParser.g:1409:4: enumLiteral_14= FLOAT
                    {
                    enumLiteral_14=(Token)match(input,FLOAT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getFLOATEnumLiteralDeclaration_14().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_14, grammarAccess.getAllTypesAccess().getFLOATEnumLiteralDeclaration_14());
                    			

                    }


                    }
                    break;
                case 16 :
                    // InternalSQLParser.g:1416:3: (enumLiteral_15= TIMESTAMPTZ )
                    {
                    // InternalSQLParser.g:1416:3: (enumLiteral_15= TIMESTAMPTZ )
                    // InternalSQLParser.g:1417:4: enumLiteral_15= TIMESTAMPTZ
                    {
                    enumLiteral_15=(Token)match(input,TIMESTAMPTZ,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTIMESTAMPTZEnumLiteralDeclaration_15().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_15, grammarAccess.getAllTypesAccess().getTIMESTAMPTZEnumLiteralDeclaration_15());
                    			

                    }


                    }
                    break;
                case 17 :
                    // InternalSQLParser.g:1424:3: (enumLiteral_16= JSONB )
                    {
                    // InternalSQLParser.g:1424:3: (enumLiteral_16= JSONB )
                    // InternalSQLParser.g:1425:4: enumLiteral_16= JSONB
                    {
                    enumLiteral_16=(Token)match(input,JSONB,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getJSONBEnumLiteralDeclaration_16().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_16, grammarAccess.getAllTypesAccess().getJSONBEnumLiteralDeclaration_16());
                    			

                    }


                    }
                    break;
                case 18 :
                    // InternalSQLParser.g:1432:3: (enumLiteral_17= BYTEA )
                    {
                    // InternalSQLParser.g:1432:3: (enumLiteral_17= BYTEA )
                    // InternalSQLParser.g:1433:4: enumLiteral_17= BYTEA
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
    // InternalSQLParser.g:1443:1: ruleDoublePrecision returns [Enumerator current=null] : (enumLiteral_0= PRECISION ) ;
    public final Enumerator ruleDoublePrecision() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:1449:2: ( (enumLiteral_0= PRECISION ) )
            // InternalSQLParser.g:1450:2: (enumLiteral_0= PRECISION )
            {
            // InternalSQLParser.g:1450:2: (enumLiteral_0= PRECISION )
            // InternalSQLParser.g:1451:3: enumLiteral_0= PRECISION
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
    // InternalSQLParser.g:1460:1: ruleZoneNoTz returns [Enumerator current=null] : (enumLiteral_0= ZONE ) ;
    public final Enumerator ruleZoneNoTz() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:1466:2: ( (enumLiteral_0= ZONE ) )
            // InternalSQLParser.g:1467:2: (enumLiteral_0= ZONE )
            {
            // InternalSQLParser.g:1467:2: (enumLiteral_0= ZONE )
            // InternalSQLParser.g:1468:3: enumLiteral_0= ZONE
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
    // InternalSQLParser.g:1477:1: ruleZoneTz returns [Enumerator current=null] : (enumLiteral_0= ZONE ) ;
    public final Enumerator ruleZoneTz() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;


        	enterRule();

        try {
            // InternalSQLParser.g:1483:2: ( (enumLiteral_0= ZONE ) )
            // InternalSQLParser.g:1484:2: (enumLiteral_0= ZONE )
            {
            // InternalSQLParser.g:1484:2: (enumLiteral_0= ZONE )
            // InternalSQLParser.g:1485:3: enumLiteral_0= ZONE
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

    // Delegated rules


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA8 dfa8 = new DFA8(this);
    static final String dfa_1s = "\43\uffff";
    static final String dfa_2s = "\1\4\40\51\2\uffff";
    static final String dfa_3s = "\1\57\40\54\2\uffff";
    static final String dfa_4s = "\41\uffff\1\1\1\2";
    static final String dfa_5s = "\43\uffff}>";
    static final String[] dfa_6s = {
            "\1\36\1\35\1\24\1\22\1\12\1\16\1\13\1\11\1\15\1\17\1\uffff\1\14\1\27\1\30\1\10\1\uffff\1\26\1\6\1\34\1\uffff\1\40\1\21\1\37\1\uffff\1\23\1\33\1\uffff\1\20\1\4\1\25\1\32\1\uffff\1\31\1\7\1\3\1\uffff\1\5\5\uffff\1\1\1\2",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
            "\1\42\2\uffff\1\41",
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
    static final String dfa_9s = "\1\4\1\uffff\3\16\1\uffff\1\20\5\uffff";
    static final String dfa_10s = "\1\45\1\uffff\3\53\1\uffff\1\51\5\uffff";
    static final String dfa_11s = "\1\uffff\1\1\3\uffff\1\2\1\uffff\1\4\1\5\1\3\1\7\1\6";
    static final String dfa_12s = "\14\uffff}>";
    static final String[] dfa_13s = {
            "\2\1\1\6\1\uffff\1\3\2\1\1\2\1\1\1\4\1\uffff\1\5\2\uffff\1\1\1\uffff\1\10\1\uffff\1\1\1\uffff\3\1\1\uffff\1\7\1\1\1\uffff\2\1\1\uffff\1\1\2\uffff\1\1",
            "",
            "\1\1\10\uffff\1\1\17\uffff\1\1\1\uffff\1\11\2\1",
            "\1\1\2\uffff\1\13\5\uffff\1\1\13\uffff\1\12\3\uffff\1\1\2\uffff\2\1",
            "\1\1\10\uffff\1\1\17\uffff\1\1\1\uffff\1\11\2\1",
            "",
            "\1\5\30\uffff\1\7",
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

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = dfa_7;
            this.eof = dfa_8;
            this.min = dfa_9;
            this.max = dfa_10;
            this.accept = dfa_11;
            this.special = dfa_12;
            this.transition = dfa_13;
        }
        public String getDescription() {
            return "327:3: ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) | ( (lv_numeric_3_0= ruleNumeric ) ) | ( (lv_char_4_0= ruleChar ) ) | (otherlv_5= DOUBLE ( (lv_simpleType_6_0= ruleDoublePrecision ) ) ) | (otherlv_7= TIMESTAMP otherlv_8= WITHOUT otherlv_9= TIME ( (lv_simpleType_10_0= ruleZoneNoTz ) ) ) | (otherlv_11= TIMESTAMP otherlv_12= WITH otherlv_13= TIME ( (lv_simpleType_14_0= ruleZoneTz ) ) ) )";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000200000080002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000C177B777BFF0L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000C177B7F7FFF0L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x00000C0000000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x00000025B754BF70L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000008000804002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000040000000L});

}