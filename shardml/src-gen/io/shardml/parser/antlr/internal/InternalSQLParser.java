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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "TIMESTAMP", "BOOLEAN", "DECIMAL", "PRIMARY", "VARCHAR", "BIGINT", "CREATE", "EXISTS", "UNIQUE", "TABLE", "DATE", "NULL", "TEXT", "UUID", "INT", "KEY", "NOT", "IF", "LeftParenthesis", "RightParenthesis", "Comma", "FullStop", "Semicolon", "RULE_ID", "RULE_QUOTED_ID", "RULE_STRING", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
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

            if ( ((LA5_0>=TIMESTAMP && LA5_0<=DECIMAL)||(LA5_0>=VARCHAR && LA5_0<=BIGINT)||LA5_0==EXISTS||LA5_0==DATE||(LA5_0>=TEXT && LA5_0<=KEY)||LA5_0==IF||(LA5_0>=RULE_ID && LA5_0<=RULE_QUOTED_ID)) ) {
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

            	    if ( ((LA6_0>=TIMESTAMP && LA6_0<=DECIMAL)||(LA6_0>=VARCHAR && LA6_0<=BIGINT)||LA6_0==EXISTS||LA6_0==DATE||(LA6_0>=TEXT && LA6_0<=KEY)||LA6_0==IF||(LA6_0>=RULE_ID && LA6_0<=RULE_QUOTED_ID)) ) {
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
    // InternalSQLParser.g:300:1: ruleColumn returns [EObject current=null] : ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* ) ;
    public final EObject ruleColumn() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_name_0_0 = null;

        Enumerator lv_simpleType_1_0 = null;

        EObject lv_varchar_2_0 = null;

        EObject lv_constraint_3_0 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:306:2: ( ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* ) )
            // InternalSQLParser.g:307:2: ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* )
            {
            // InternalSQLParser.g:307:2: ( ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* )
            // InternalSQLParser.g:308:3: ( (lv_name_0_0= ruleName ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )*
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

            // InternalSQLParser.g:327:3: ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=TIMESTAMP && LA8_0<=DECIMAL)||LA8_0==BIGINT||LA8_0==DATE||(LA8_0>=TEXT && LA8_0<=INT)) ) {
                alt8=1;
            }
            else if ( (LA8_0==VARCHAR) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
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

            }

            // InternalSQLParser.g:368:3: ( (lv_constraint_3_0= ruleConstraint ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==PRIMARY||LA9_0==UNIQUE||LA9_0==NOT) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalSQLParser.g:369:4: (lv_constraint_3_0= ruleConstraint )
            	    {
            	    // InternalSQLParser.g:369:4: (lv_constraint_3_0= ruleConstraint )
            	    // InternalSQLParser.g:370:5: lv_constraint_3_0= ruleConstraint
            	    {

            	    					newCompositeNode(grammarAccess.getColumnAccess().getConstraintConstraintParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_13);
            	    lv_constraint_3_0=ruleConstraint();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getColumnRule());
            	    					}
            	    					add(
            	    						current,
            	    						"constraint",
            	    						lv_constraint_3_0,
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
    // InternalSQLParser.g:391:1: entryRuleVarchar returns [EObject current=null] : iv_ruleVarchar= ruleVarchar EOF ;
    public final EObject entryRuleVarchar() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarchar = null;


        try {
            // InternalSQLParser.g:391:48: (iv_ruleVarchar= ruleVarchar EOF )
            // InternalSQLParser.g:392:2: iv_ruleVarchar= ruleVarchar EOF
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
    // InternalSQLParser.g:398:1: ruleVarchar returns [EObject current=null] : (otherlv_0= VARCHAR otherlv_1= LeftParenthesis ( (lv_size_2_0= RULE_INT ) ) otherlv_3= RightParenthesis ) ;
    public final EObject ruleVarchar() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_size_2_0=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSQLParser.g:404:2: ( (otherlv_0= VARCHAR otherlv_1= LeftParenthesis ( (lv_size_2_0= RULE_INT ) ) otherlv_3= RightParenthesis ) )
            // InternalSQLParser.g:405:2: (otherlv_0= VARCHAR otherlv_1= LeftParenthesis ( (lv_size_2_0= RULE_INT ) ) otherlv_3= RightParenthesis )
            {
            // InternalSQLParser.g:405:2: (otherlv_0= VARCHAR otherlv_1= LeftParenthesis ( (lv_size_2_0= RULE_INT ) ) otherlv_3= RightParenthesis )
            // InternalSQLParser.g:406:3: otherlv_0= VARCHAR otherlv_1= LeftParenthesis ( (lv_size_2_0= RULE_INT ) ) otherlv_3= RightParenthesis
            {
            otherlv_0=(Token)match(input,VARCHAR,FOLLOW_9); 

            			newLeafNode(otherlv_0, grammarAccess.getVarcharAccess().getVARCHARKeyword_0());
            		
            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_14); 

            			newLeafNode(otherlv_1, grammarAccess.getVarcharAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:414:3: ( (lv_size_2_0= RULE_INT ) )
            // InternalSQLParser.g:415:4: (lv_size_2_0= RULE_INT )
            {
            // InternalSQLParser.g:415:4: (lv_size_2_0= RULE_INT )
            // InternalSQLParser.g:416:5: lv_size_2_0= RULE_INT
            {
            lv_size_2_0=(Token)match(input,RULE_INT,FOLLOW_15); 

            					newLeafNode(lv_size_2_0, grammarAccess.getVarcharAccess().getSizeINTTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVarcharRule());
            					}
            					setWithLastConsumed(
            						current,
            						"size",
            						lv_size_2_0,
            						"io.shardml.SQL.INT");
            				

            }


            }

            otherlv_3=(Token)match(input,RightParenthesis,FOLLOW_2); 

            			newLeafNode(otherlv_3, grammarAccess.getVarcharAccess().getRightParenthesisKeyword_3());
            		

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


    // $ANTLR start "entryRuleConstraint"
    // InternalSQLParser.g:440:1: entryRuleConstraint returns [EObject current=null] : iv_ruleConstraint= ruleConstraint EOF ;
    public final EObject entryRuleConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstraint = null;


        try {
            // InternalSQLParser.g:440:51: (iv_ruleConstraint= ruleConstraint EOF )
            // InternalSQLParser.g:441:2: iv_ruleConstraint= ruleConstraint EOF
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
    // InternalSQLParser.g:447:1: ruleConstraint returns [EObject current=null] : (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull ) ;
    public final EObject ruleConstraint() throws RecognitionException {
        EObject current = null;

        EObject this_PrimaryAnnotation_0 = null;

        EObject this_UniqueAnnotation_1 = null;

        EObject this_NotNull_2 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:453:2: ( (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull ) )
            // InternalSQLParser.g:454:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull )
            {
            // InternalSQLParser.g:454:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull )
            int alt10=3;
            switch ( input.LA(1) ) {
            case PRIMARY:
                {
                alt10=1;
                }
                break;
            case UNIQUE:
                {
                alt10=2;
                }
                break;
            case NOT:
                {
                alt10=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalSQLParser.g:455:3: this_PrimaryAnnotation_0= rulePrimaryAnnotation
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
                    // InternalSQLParser.g:464:3: this_UniqueAnnotation_1= ruleUniqueAnnotation
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
                    // InternalSQLParser.g:473:3: this_NotNull_2= ruleNotNull
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
    // InternalSQLParser.g:485:1: entryRulePrimaryAnnotation returns [EObject current=null] : iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF ;
    public final EObject entryRulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryAnnotation = null;


        try {
            // InternalSQLParser.g:485:58: (iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF )
            // InternalSQLParser.g:486:2: iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF
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
    // InternalSQLParser.g:492:1: rulePrimaryAnnotation returns [EObject current=null] : ( () otherlv_1= PRIMARY otherlv_2= KEY ) ;
    public final EObject rulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSQLParser.g:498:2: ( ( () otherlv_1= PRIMARY otherlv_2= KEY ) )
            // InternalSQLParser.g:499:2: ( () otherlv_1= PRIMARY otherlv_2= KEY )
            {
            // InternalSQLParser.g:499:2: ( () otherlv_1= PRIMARY otherlv_2= KEY )
            // InternalSQLParser.g:500:3: () otherlv_1= PRIMARY otherlv_2= KEY
            {
            // InternalSQLParser.g:500:3: ()
            // InternalSQLParser.g:501:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getPrimaryAnnotationAccess().getPrimaryAnnotationAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,PRIMARY,FOLLOW_16); 

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
    // InternalSQLParser.g:519:1: entryRuleUniqueAnnotation returns [EObject current=null] : iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF ;
    public final EObject entryRuleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueAnnotation = null;


        try {
            // InternalSQLParser.g:519:57: (iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF )
            // InternalSQLParser.g:520:2: iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF
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
    // InternalSQLParser.g:526:1: ruleUniqueAnnotation returns [EObject current=null] : ( () otherlv_1= UNIQUE ) ;
    public final EObject ruleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalSQLParser.g:532:2: ( ( () otherlv_1= UNIQUE ) )
            // InternalSQLParser.g:533:2: ( () otherlv_1= UNIQUE )
            {
            // InternalSQLParser.g:533:2: ( () otherlv_1= UNIQUE )
            // InternalSQLParser.g:534:3: () otherlv_1= UNIQUE
            {
            // InternalSQLParser.g:534:3: ()
            // InternalSQLParser.g:535:4: 
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
    // InternalSQLParser.g:549:1: entryRuleNotNull returns [EObject current=null] : iv_ruleNotNull= ruleNotNull EOF ;
    public final EObject entryRuleNotNull() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNotNull = null;


        try {
            // InternalSQLParser.g:549:48: (iv_ruleNotNull= ruleNotNull EOF )
            // InternalSQLParser.g:550:2: iv_ruleNotNull= ruleNotNull EOF
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
    // InternalSQLParser.g:556:1: ruleNotNull returns [EObject current=null] : ( () otherlv_1= NOT otherlv_2= NULL ) ;
    public final EObject ruleNotNull() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSQLParser.g:562:2: ( ( () otherlv_1= NOT otherlv_2= NULL ) )
            // InternalSQLParser.g:563:2: ( () otherlv_1= NOT otherlv_2= NULL )
            {
            // InternalSQLParser.g:563:2: ( () otherlv_1= NOT otherlv_2= NULL )
            // InternalSQLParser.g:564:3: () otherlv_1= NOT otherlv_2= NULL
            {
            // InternalSQLParser.g:564:3: ()
            // InternalSQLParser.g:565:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getNotNullAccess().getNotNullAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,NOT,FOLLOW_17); 

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
    // InternalSQLParser.g:583:1: entryRuleTableConstraint returns [EObject current=null] : iv_ruleTableConstraint= ruleTableConstraint EOF ;
    public final EObject entryRuleTableConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableConstraint = null;


        try {
            // InternalSQLParser.g:583:56: (iv_ruleTableConstraint= ruleTableConstraint EOF )
            // InternalSQLParser.g:584:2: iv_ruleTableConstraint= ruleTableConstraint EOF
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
    // InternalSQLParser.g:590:1: ruleTableConstraint returns [EObject current=null] : (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey ) ;
    public final EObject ruleTableConstraint() throws RecognitionException {
        EObject current = null;

        EObject this_PrimaryKey_0 = null;

        EObject this_UniqueKey_1 = null;



        	enterRule();

        try {
            // InternalSQLParser.g:596:2: ( (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey ) )
            // InternalSQLParser.g:597:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey )
            {
            // InternalSQLParser.g:597:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==PRIMARY) ) {
                alt11=1;
            }
            else if ( (LA11_0==UNIQUE) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalSQLParser.g:598:3: this_PrimaryKey_0= rulePrimaryKey
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
                    // InternalSQLParser.g:607:3: this_UniqueKey_1= ruleUniqueKey
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
    // InternalSQLParser.g:619:1: entryRulePrimaryKey returns [EObject current=null] : iv_rulePrimaryKey= rulePrimaryKey EOF ;
    public final EObject entryRulePrimaryKey() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryKey = null;


        try {
            // InternalSQLParser.g:619:51: (iv_rulePrimaryKey= rulePrimaryKey EOF )
            // InternalSQLParser.g:620:2: iv_rulePrimaryKey= rulePrimaryKey EOF
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
    // InternalSQLParser.g:626:1: rulePrimaryKey returns [EObject current=null] : (otherlv_0= PRIMARY otherlv_1= KEY otherlv_2= LeftParenthesis ( ( ruleName ) ) (otherlv_4= Comma ( ( ruleName ) ) )* otherlv_6= RightParenthesis ) ;
    public final EObject rulePrimaryKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;


        	enterRule();

        try {
            // InternalSQLParser.g:632:2: ( (otherlv_0= PRIMARY otherlv_1= KEY otherlv_2= LeftParenthesis ( ( ruleName ) ) (otherlv_4= Comma ( ( ruleName ) ) )* otherlv_6= RightParenthesis ) )
            // InternalSQLParser.g:633:2: (otherlv_0= PRIMARY otherlv_1= KEY otherlv_2= LeftParenthesis ( ( ruleName ) ) (otherlv_4= Comma ( ( ruleName ) ) )* otherlv_6= RightParenthesis )
            {
            // InternalSQLParser.g:633:2: (otherlv_0= PRIMARY otherlv_1= KEY otherlv_2= LeftParenthesis ( ( ruleName ) ) (otherlv_4= Comma ( ( ruleName ) ) )* otherlv_6= RightParenthesis )
            // InternalSQLParser.g:634:3: otherlv_0= PRIMARY otherlv_1= KEY otherlv_2= LeftParenthesis ( ( ruleName ) ) (otherlv_4= Comma ( ( ruleName ) ) )* otherlv_6= RightParenthesis
            {
            otherlv_0=(Token)match(input,PRIMARY,FOLLOW_16); 

            			newLeafNode(otherlv_0, grammarAccess.getPrimaryKeyAccess().getPRIMARYKeyword_0());
            		
            otherlv_1=(Token)match(input,KEY,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getPrimaryKeyAccess().getKEYKeyword_1());
            		
            otherlv_2=(Token)match(input,LeftParenthesis,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getPrimaryKeyAccess().getLeftParenthesisKeyword_2());
            		
            // InternalSQLParser.g:646:3: ( ( ruleName ) )
            // InternalSQLParser.g:647:4: ( ruleName )
            {
            // InternalSQLParser.g:647:4: ( ruleName )
            // InternalSQLParser.g:648:5: ruleName
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

            // InternalSQLParser.g:662:3: (otherlv_4= Comma ( ( ruleName ) ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==Comma) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalSQLParser.g:663:4: otherlv_4= Comma ( ( ruleName ) )
            	    {
            	    otherlv_4=(Token)match(input,Comma,FOLLOW_5); 

            	    				newLeafNode(otherlv_4, grammarAccess.getPrimaryKeyAccess().getCommaKeyword_4_0());
            	    			
            	    // InternalSQLParser.g:667:4: ( ( ruleName ) )
            	    // InternalSQLParser.g:668:5: ( ruleName )
            	    {
            	    // InternalSQLParser.g:668:5: ( ruleName )
            	    // InternalSQLParser.g:669:6: ruleName
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
            	    break loop12;
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
    // InternalSQLParser.g:692:1: entryRuleUniqueKey returns [EObject current=null] : iv_ruleUniqueKey= ruleUniqueKey EOF ;
    public final EObject entryRuleUniqueKey() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueKey = null;


        try {
            // InternalSQLParser.g:692:50: (iv_ruleUniqueKey= ruleUniqueKey EOF )
            // InternalSQLParser.g:693:2: iv_ruleUniqueKey= ruleUniqueKey EOF
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
    // InternalSQLParser.g:699:1: ruleUniqueKey returns [EObject current=null] : (otherlv_0= UNIQUE otherlv_1= LeftParenthesis ( ( ruleName ) ) (otherlv_3= Comma ( ( ruleName ) ) )* otherlv_5= RightParenthesis ) ;
    public final EObject ruleUniqueKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalSQLParser.g:705:2: ( (otherlv_0= UNIQUE otherlv_1= LeftParenthesis ( ( ruleName ) ) (otherlv_3= Comma ( ( ruleName ) ) )* otherlv_5= RightParenthesis ) )
            // InternalSQLParser.g:706:2: (otherlv_0= UNIQUE otherlv_1= LeftParenthesis ( ( ruleName ) ) (otherlv_3= Comma ( ( ruleName ) ) )* otherlv_5= RightParenthesis )
            {
            // InternalSQLParser.g:706:2: (otherlv_0= UNIQUE otherlv_1= LeftParenthesis ( ( ruleName ) ) (otherlv_3= Comma ( ( ruleName ) ) )* otherlv_5= RightParenthesis )
            // InternalSQLParser.g:707:3: otherlv_0= UNIQUE otherlv_1= LeftParenthesis ( ( ruleName ) ) (otherlv_3= Comma ( ( ruleName ) ) )* otherlv_5= RightParenthesis
            {
            otherlv_0=(Token)match(input,UNIQUE,FOLLOW_9); 

            			newLeafNode(otherlv_0, grammarAccess.getUniqueKeyAccess().getUNIQUEKeyword_0());
            		
            otherlv_1=(Token)match(input,LeftParenthesis,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getUniqueKeyAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQLParser.g:715:3: ( ( ruleName ) )
            // InternalSQLParser.g:716:4: ( ruleName )
            {
            // InternalSQLParser.g:716:4: ( ruleName )
            // InternalSQLParser.g:717:5: ruleName
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

            // InternalSQLParser.g:731:3: (otherlv_3= Comma ( ( ruleName ) ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==Comma) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalSQLParser.g:732:4: otherlv_3= Comma ( ( ruleName ) )
            	    {
            	    otherlv_3=(Token)match(input,Comma,FOLLOW_5); 

            	    				newLeafNode(otherlv_3, grammarAccess.getUniqueKeyAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalSQLParser.g:736:4: ( ( ruleName ) )
            	    // InternalSQLParser.g:737:5: ( ruleName )
            	    {
            	    // InternalSQLParser.g:737:5: ( ruleName )
            	    // InternalSQLParser.g:738:6: ruleName
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
            	    break loop13;
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
    // InternalSQLParser.g:761:1: entryRuleName returns [String current=null] : iv_ruleName= ruleName EOF ;
    public final String entryRuleName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleName = null;


        try {
            // InternalSQLParser.g:761:44: (iv_ruleName= ruleName EOF )
            // InternalSQLParser.g:762:2: iv_ruleName= ruleName EOF
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
    // InternalSQLParser.g:768:1: ruleName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= UUID | kw= DATE ) ;
    public final AntlrDatatypeRuleToken ruleName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token this_QUOTED_ID_1=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalSQLParser.g:774:2: ( (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= UUID | kw= DATE ) )
            // InternalSQLParser.g:775:2: (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= UUID | kw= DATE )
            {
            // InternalSQLParser.g:775:2: (this_ID_0= RULE_ID | this_QUOTED_ID_1= RULE_QUOTED_ID | kw= KEY | kw= TEXT | kw= IF | kw= EXISTS | kw= INT | kw= BIGINT | kw= DECIMAL | kw= TIMESTAMP | kw= BOOLEAN | kw= VARCHAR | kw= UUID | kw= DATE )
            int alt14=14;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt14=1;
                }
                break;
            case RULE_QUOTED_ID:
                {
                alt14=2;
                }
                break;
            case KEY:
                {
                alt14=3;
                }
                break;
            case TEXT:
                {
                alt14=4;
                }
                break;
            case IF:
                {
                alt14=5;
                }
                break;
            case EXISTS:
                {
                alt14=6;
                }
                break;
            case INT:
                {
                alt14=7;
                }
                break;
            case BIGINT:
                {
                alt14=8;
                }
                break;
            case DECIMAL:
                {
                alt14=9;
                }
                break;
            case TIMESTAMP:
                {
                alt14=10;
                }
                break;
            case BOOLEAN:
                {
                alt14=11;
                }
                break;
            case VARCHAR:
                {
                alt14=12;
                }
                break;
            case UUID:
                {
                alt14=13;
                }
                break;
            case DATE:
                {
                alt14=14;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // InternalSQLParser.g:776:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getNameAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:784:3: this_QUOTED_ID_1= RULE_QUOTED_ID
                    {
                    this_QUOTED_ID_1=(Token)match(input,RULE_QUOTED_ID,FOLLOW_2); 

                    			current.merge(this_QUOTED_ID_1);
                    		

                    			newLeafNode(this_QUOTED_ID_1, grammarAccess.getNameAccess().getQUOTED_IDTerminalRuleCall_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:792:3: kw= KEY
                    {
                    kw=(Token)match(input,KEY,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getKEYKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:798:3: kw= TEXT
                    {
                    kw=(Token)match(input,TEXT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getTEXTKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:804:3: kw= IF
                    {
                    kw=(Token)match(input,IF,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getIFKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:810:3: kw= EXISTS
                    {
                    kw=(Token)match(input,EXISTS,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getEXISTSKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:816:3: kw= INT
                    {
                    kw=(Token)match(input,INT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getINTKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalSQLParser.g:822:3: kw= BIGINT
                    {
                    kw=(Token)match(input,BIGINT,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getBIGINTKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalSQLParser.g:828:3: kw= DECIMAL
                    {
                    kw=(Token)match(input,DECIMAL,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getDECIMALKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalSQLParser.g:834:3: kw= TIMESTAMP
                    {
                    kw=(Token)match(input,TIMESTAMP,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getTIMESTAMPKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalSQLParser.g:840:3: kw= BOOLEAN
                    {
                    kw=(Token)match(input,BOOLEAN,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getBOOLEANKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalSQLParser.g:846:3: kw= VARCHAR
                    {
                    kw=(Token)match(input,VARCHAR,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getVARCHARKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalSQLParser.g:852:3: kw= UUID
                    {
                    kw=(Token)match(input,UUID,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getUUIDKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalSQLParser.g:858:3: kw= DATE
                    {
                    kw=(Token)match(input,DATE,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNameAccess().getDATEKeyword_13());
                    		

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
    // InternalSQLParser.g:867:1: ruleAllTypes returns [Enumerator current=null] : ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) ) ;
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


        	enterRule();

        try {
            // InternalSQLParser.g:873:2: ( ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) ) )
            // InternalSQLParser.g:874:2: ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) )
            {
            // InternalSQLParser.g:874:2: ( (enumLiteral_0= INT ) | (enumLiteral_1= BIGINT ) | (enumLiteral_2= UUID ) | (enumLiteral_3= DECIMAL ) | (enumLiteral_4= TIMESTAMP ) | (enumLiteral_5= DATE ) | (enumLiteral_6= BOOLEAN ) | (enumLiteral_7= TEXT ) )
            int alt15=8;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt15=1;
                }
                break;
            case BIGINT:
                {
                alt15=2;
                }
                break;
            case UUID:
                {
                alt15=3;
                }
                break;
            case DECIMAL:
                {
                alt15=4;
                }
                break;
            case TIMESTAMP:
                {
                alt15=5;
                }
                break;
            case DATE:
                {
                alt15=6;
                }
                break;
            case BOOLEAN:
                {
                alt15=7;
                }
                break;
            case TEXT:
                {
                alt15=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // InternalSQLParser.g:875:3: (enumLiteral_0= INT )
                    {
                    // InternalSQLParser.g:875:3: (enumLiteral_0= INT )
                    // InternalSQLParser.g:876:4: enumLiteral_0= INT
                    {
                    enumLiteral_0=(Token)match(input,INT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQLParser.g:883:3: (enumLiteral_1= BIGINT )
                    {
                    // InternalSQLParser.g:883:3: (enumLiteral_1= BIGINT )
                    // InternalSQLParser.g:884:4: enumLiteral_1= BIGINT
                    {
                    enumLiteral_1=(Token)match(input,BIGINT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalSQLParser.g:891:3: (enumLiteral_2= UUID )
                    {
                    // InternalSQLParser.g:891:3: (enumLiteral_2= UUID )
                    // InternalSQLParser.g:892:4: enumLiteral_2= UUID
                    {
                    enumLiteral_2=(Token)match(input,UUID,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalSQLParser.g:899:3: (enumLiteral_3= DECIMAL )
                    {
                    // InternalSQLParser.g:899:3: (enumLiteral_3= DECIMAL )
                    // InternalSQLParser.g:900:4: enumLiteral_3= DECIMAL
                    {
                    enumLiteral_3=(Token)match(input,DECIMAL,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalSQLParser.g:907:3: (enumLiteral_4= TIMESTAMP )
                    {
                    // InternalSQLParser.g:907:3: (enumLiteral_4= TIMESTAMP )
                    // InternalSQLParser.g:908:4: enumLiteral_4= TIMESTAMP
                    {
                    enumLiteral_4=(Token)match(input,TIMESTAMP,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalSQLParser.g:915:3: (enumLiteral_5= DATE )
                    {
                    // InternalSQLParser.g:915:3: (enumLiteral_5= DATE )
                    // InternalSQLParser.g:916:4: enumLiteral_5= DATE
                    {
                    enumLiteral_5=(Token)match(input,DATE,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalSQLParser.g:923:3: (enumLiteral_6= BOOLEAN )
                    {
                    // InternalSQLParser.g:923:3: (enumLiteral_6= BOOLEAN )
                    // InternalSQLParser.g:924:4: enumLiteral_6= BOOLEAN
                    {
                    enumLiteral_6=(Token)match(input,BOOLEAN,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalSQLParser.g:931:3: (enumLiteral_7= TEXT )
                    {
                    // InternalSQLParser.g:931:3: (enumLiteral_7= TEXT )
                    // InternalSQLParser.g:932:4: enumLiteral_7= TEXT
                    {
                    enumLiteral_7=(Token)match(input,TEXT,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTEXTEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getAllTypesAccess().getTEXTEnumLiteralDeclaration_7());
                    			

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

    // Delegated rules


    protected DFA4 dfa4 = new DFA4(this);
    static final String dfa_1s = "\21\uffff";
    static final String dfa_2s = "\1\4\16\26\2\uffff";
    static final String dfa_3s = "\1\34\16\31\2\uffff";
    static final String dfa_4s = "\17\uffff\1\1\1\2";
    static final String dfa_5s = "\21\uffff}>";
    static final String[] dfa_6s = {
            "\1\12\1\13\1\11\1\uffff\1\14\1\10\1\uffff\1\6\2\uffff\1\16\1\uffff\1\4\1\15\1\7\1\3\1\uffff\1\5\5\uffff\1\1\1\2",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
            "\1\20\2\uffff\1\17",
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
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000004000402L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x00000000182F4B70L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00000000182F5BF0L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000001800000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000074370L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000101082L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000008000L});

}