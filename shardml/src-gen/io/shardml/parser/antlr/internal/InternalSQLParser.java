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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'CREATE'", "'TABLE'", "'('", "','", "')'", "'VARCHAR'", "'PRIMARY'", "'KEY'", "'UNIQUE'", "'NOT'", "'NULL'", "'INT'", "'BIGINT'", "'UUID'", "'DECIMAL'", "'TIMESTAMP'", "'DATE'", "'BOOLEAN'", "'TEXT'"
    };
    public static final int RULE_STRING=6;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int RULE_ID=4;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=5;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalSQLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSQLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSQLParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSQL.g"; }



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
    // InternalSQL.g:65:1: entryRuleSchema returns [EObject current=null] : iv_ruleSchema= ruleSchema EOF ;
    public final EObject entryRuleSchema() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSchema = null;


        try {
            // InternalSQL.g:65:47: (iv_ruleSchema= ruleSchema EOF )
            // InternalSQL.g:66:2: iv_ruleSchema= ruleSchema EOF
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
    // InternalSQL.g:72:1: ruleSchema returns [EObject current=null] : ( (lv_tables_0_0= ruleTable ) )* ;
    public final EObject ruleSchema() throws RecognitionException {
        EObject current = null;

        EObject lv_tables_0_0 = null;



        	enterRule();

        try {
            // InternalSQL.g:78:2: ( ( (lv_tables_0_0= ruleTable ) )* )
            // InternalSQL.g:79:2: ( (lv_tables_0_0= ruleTable ) )*
            {
            // InternalSQL.g:79:2: ( (lv_tables_0_0= ruleTable ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSQL.g:80:3: (lv_tables_0_0= ruleTable )
            	    {
            	    // InternalSQL.g:80:3: (lv_tables_0_0= ruleTable )
            	    // InternalSQL.g:81:4: lv_tables_0_0= ruleTable
            	    {

            	    				newCompositeNode(grammarAccess.getSchemaAccess().getTablesTableParserRuleCall_0());
            	    			
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
            	    break;

            	default :
            	    break loop1;
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
    // InternalSQL.g:101:1: entryRuleTable returns [EObject current=null] : iv_ruleTable= ruleTable EOF ;
    public final EObject entryRuleTable() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTable = null;


        try {
            // InternalSQL.g:101:46: (iv_ruleTable= ruleTable EOF )
            // InternalSQL.g:102:2: iv_ruleTable= ruleTable EOF
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
    // InternalSQL.g:108:1: ruleTable returns [EObject current=null] : (otherlv_0= 'CREATE' otherlv_1= 'TABLE' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_columns_4_0= ruleColumn ) ) | ( (lv_constraints_5_0= ruleTableConstraint ) ) ) (otherlv_6= ',' ( ( (lv_columns_7_0= ruleColumn ) ) | ( (lv_constraints_8_0= ruleTableConstraint ) ) ) )* otherlv_9= ')' ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_6=null;
        Token otherlv_9=null;
        EObject lv_columns_4_0 = null;

        EObject lv_constraints_5_0 = null;

        EObject lv_columns_7_0 = null;

        EObject lv_constraints_8_0 = null;



        	enterRule();

        try {
            // InternalSQL.g:114:2: ( (otherlv_0= 'CREATE' otherlv_1= 'TABLE' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_columns_4_0= ruleColumn ) ) | ( (lv_constraints_5_0= ruleTableConstraint ) ) ) (otherlv_6= ',' ( ( (lv_columns_7_0= ruleColumn ) ) | ( (lv_constraints_8_0= ruleTableConstraint ) ) ) )* otherlv_9= ')' ) )
            // InternalSQL.g:115:2: (otherlv_0= 'CREATE' otherlv_1= 'TABLE' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_columns_4_0= ruleColumn ) ) | ( (lv_constraints_5_0= ruleTableConstraint ) ) ) (otherlv_6= ',' ( ( (lv_columns_7_0= ruleColumn ) ) | ( (lv_constraints_8_0= ruleTableConstraint ) ) ) )* otherlv_9= ')' )
            {
            // InternalSQL.g:115:2: (otherlv_0= 'CREATE' otherlv_1= 'TABLE' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_columns_4_0= ruleColumn ) ) | ( (lv_constraints_5_0= ruleTableConstraint ) ) ) (otherlv_6= ',' ( ( (lv_columns_7_0= ruleColumn ) ) | ( (lv_constraints_8_0= ruleTableConstraint ) ) ) )* otherlv_9= ')' )
            // InternalSQL.g:116:3: otherlv_0= 'CREATE' otherlv_1= 'TABLE' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( ( (lv_columns_4_0= ruleColumn ) ) | ( (lv_constraints_5_0= ruleTableConstraint ) ) ) (otherlv_6= ',' ( ( (lv_columns_7_0= ruleColumn ) ) | ( (lv_constraints_8_0= ruleTableConstraint ) ) ) )* otherlv_9= ')'
            {
            otherlv_0=(Token)match(input,11,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getTableAccess().getCREATEKeyword_0());
            		
            otherlv_1=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getTableAccess().getTABLEKeyword_1());
            		
            // InternalSQL.g:124:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalSQL.g:125:4: (lv_name_2_0= RULE_ID )
            {
            // InternalSQL.g:125:4: (lv_name_2_0= RULE_ID )
            // InternalSQL.g:126:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_6); 

            					newLeafNode(lv_name_2_0, grammarAccess.getTableAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTableRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_3=(Token)match(input,13,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getTableAccess().getLeftParenthesisKeyword_3());
            		
            // InternalSQL.g:146:3: ( ( (lv_columns_4_0= ruleColumn ) ) | ( (lv_constraints_5_0= ruleTableConstraint ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==RULE_ID) ) {
                alt2=1;
            }
            else if ( (LA2_0==17||LA2_0==19) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalSQL.g:147:4: ( (lv_columns_4_0= ruleColumn ) )
                    {
                    // InternalSQL.g:147:4: ( (lv_columns_4_0= ruleColumn ) )
                    // InternalSQL.g:148:5: (lv_columns_4_0= ruleColumn )
                    {
                    // InternalSQL.g:148:5: (lv_columns_4_0= ruleColumn )
                    // InternalSQL.g:149:6: lv_columns_4_0= ruleColumn
                    {

                    						newCompositeNode(grammarAccess.getTableAccess().getColumnsColumnParserRuleCall_4_0_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_columns_4_0=ruleColumn();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTableRule());
                    						}
                    						add(
                    							current,
                    							"columns",
                    							lv_columns_4_0,
                    							"io.shardml.SQL.Column");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSQL.g:167:4: ( (lv_constraints_5_0= ruleTableConstraint ) )
                    {
                    // InternalSQL.g:167:4: ( (lv_constraints_5_0= ruleTableConstraint ) )
                    // InternalSQL.g:168:5: (lv_constraints_5_0= ruleTableConstraint )
                    {
                    // InternalSQL.g:168:5: (lv_constraints_5_0= ruleTableConstraint )
                    // InternalSQL.g:169:6: lv_constraints_5_0= ruleTableConstraint
                    {

                    						newCompositeNode(grammarAccess.getTableAccess().getConstraintsTableConstraintParserRuleCall_4_1_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_constraints_5_0=ruleTableConstraint();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTableRule());
                    						}
                    						add(
                    							current,
                    							"constraints",
                    							lv_constraints_5_0,
                    							"io.shardml.SQL.TableConstraint");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalSQL.g:187:3: (otherlv_6= ',' ( ( (lv_columns_7_0= ruleColumn ) ) | ( (lv_constraints_8_0= ruleTableConstraint ) ) ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==14) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalSQL.g:188:4: otherlv_6= ',' ( ( (lv_columns_7_0= ruleColumn ) ) | ( (lv_constraints_8_0= ruleTableConstraint ) ) )
            	    {
            	    otherlv_6=(Token)match(input,14,FOLLOW_7); 

            	    				newLeafNode(otherlv_6, grammarAccess.getTableAccess().getCommaKeyword_5_0());
            	    			
            	    // InternalSQL.g:192:4: ( ( (lv_columns_7_0= ruleColumn ) ) | ( (lv_constraints_8_0= ruleTableConstraint ) ) )
            	    int alt3=2;
            	    int LA3_0 = input.LA(1);

            	    if ( (LA3_0==RULE_ID) ) {
            	        alt3=1;
            	    }
            	    else if ( (LA3_0==17||LA3_0==19) ) {
            	        alt3=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 3, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt3) {
            	        case 1 :
            	            // InternalSQL.g:193:5: ( (lv_columns_7_0= ruleColumn ) )
            	            {
            	            // InternalSQL.g:193:5: ( (lv_columns_7_0= ruleColumn ) )
            	            // InternalSQL.g:194:6: (lv_columns_7_0= ruleColumn )
            	            {
            	            // InternalSQL.g:194:6: (lv_columns_7_0= ruleColumn )
            	            // InternalSQL.g:195:7: lv_columns_7_0= ruleColumn
            	            {

            	            							newCompositeNode(grammarAccess.getTableAccess().getColumnsColumnParserRuleCall_5_1_0_0());
            	            						
            	            pushFollow(FOLLOW_8);
            	            lv_columns_7_0=ruleColumn();

            	            state._fsp--;


            	            							if (current==null) {
            	            								current = createModelElementForParent(grammarAccess.getTableRule());
            	            							}
            	            							add(
            	            								current,
            	            								"columns",
            	            								lv_columns_7_0,
            	            								"io.shardml.SQL.Column");
            	            							afterParserOrEnumRuleCall();
            	            						

            	            }


            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalSQL.g:213:5: ( (lv_constraints_8_0= ruleTableConstraint ) )
            	            {
            	            // InternalSQL.g:213:5: ( (lv_constraints_8_0= ruleTableConstraint ) )
            	            // InternalSQL.g:214:6: (lv_constraints_8_0= ruleTableConstraint )
            	            {
            	            // InternalSQL.g:214:6: (lv_constraints_8_0= ruleTableConstraint )
            	            // InternalSQL.g:215:7: lv_constraints_8_0= ruleTableConstraint
            	            {

            	            							newCompositeNode(grammarAccess.getTableAccess().getConstraintsTableConstraintParserRuleCall_5_1_1_0());
            	            						
            	            pushFollow(FOLLOW_8);
            	            lv_constraints_8_0=ruleTableConstraint();

            	            state._fsp--;


            	            							if (current==null) {
            	            								current = createModelElementForParent(grammarAccess.getTableRule());
            	            							}
            	            							add(
            	            								current,
            	            								"constraints",
            	            								lv_constraints_8_0,
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
            	    break loop4;
                }
            } while (true);

            otherlv_9=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getTableAccess().getRightParenthesisKeyword_6());
            		

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
    // InternalSQL.g:242:1: entryRuleColumn returns [EObject current=null] : iv_ruleColumn= ruleColumn EOF ;
    public final EObject entryRuleColumn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumn = null;


        try {
            // InternalSQL.g:242:47: (iv_ruleColumn= ruleColumn EOF )
            // InternalSQL.g:243:2: iv_ruleColumn= ruleColumn EOF
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
    // InternalSQL.g:249:1: ruleColumn returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* ) ;
    public final EObject ruleColumn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Enumerator lv_simpleType_1_0 = null;

        EObject lv_varchar_2_0 = null;

        EObject lv_constraint_3_0 = null;



        	enterRule();

        try {
            // InternalSQL.g:255:2: ( ( ( (lv_name_0_0= RULE_ID ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* ) )
            // InternalSQL.g:256:2: ( ( (lv_name_0_0= RULE_ID ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* )
            {
            // InternalSQL.g:256:2: ( ( (lv_name_0_0= RULE_ID ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* )
            // InternalSQL.g:257:3: ( (lv_name_0_0= RULE_ID ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )*
            {
            // InternalSQL.g:257:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalSQL.g:258:4: (lv_name_0_0= RULE_ID )
            {
            // InternalSQL.g:258:4: (lv_name_0_0= RULE_ID )
            // InternalSQL.g:259:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_9); 

            					newLeafNode(lv_name_0_0, grammarAccess.getColumnAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getColumnRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalSQL.g:275:3: ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0>=22 && LA5_0<=29)) ) {
                alt5=1;
            }
            else if ( (LA5_0==16) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalSQL.g:276:4: ( (lv_simpleType_1_0= ruleAllTypes ) )
                    {
                    // InternalSQL.g:276:4: ( (lv_simpleType_1_0= ruleAllTypes ) )
                    // InternalSQL.g:277:5: (lv_simpleType_1_0= ruleAllTypes )
                    {
                    // InternalSQL.g:277:5: (lv_simpleType_1_0= ruleAllTypes )
                    // InternalSQL.g:278:6: lv_simpleType_1_0= ruleAllTypes
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeAllTypesEnumRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_10);
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
                    // InternalSQL.g:296:4: ( (lv_varchar_2_0= ruleVarchar ) )
                    {
                    // InternalSQL.g:296:4: ( (lv_varchar_2_0= ruleVarchar ) )
                    // InternalSQL.g:297:5: (lv_varchar_2_0= ruleVarchar )
                    {
                    // InternalSQL.g:297:5: (lv_varchar_2_0= ruleVarchar )
                    // InternalSQL.g:298:6: lv_varchar_2_0= ruleVarchar
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getVarcharVarcharParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_10);
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

            // InternalSQL.g:316:3: ( (lv_constraint_3_0= ruleConstraint ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==17||(LA6_0>=19 && LA6_0<=20)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalSQL.g:317:4: (lv_constraint_3_0= ruleConstraint )
            	    {
            	    // InternalSQL.g:317:4: (lv_constraint_3_0= ruleConstraint )
            	    // InternalSQL.g:318:5: lv_constraint_3_0= ruleConstraint
            	    {

            	    					newCompositeNode(grammarAccess.getColumnAccess().getConstraintConstraintParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_10);
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
            	    break loop6;
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
    // InternalSQL.g:339:1: entryRuleVarchar returns [EObject current=null] : iv_ruleVarchar= ruleVarchar EOF ;
    public final EObject entryRuleVarchar() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarchar = null;


        try {
            // InternalSQL.g:339:48: (iv_ruleVarchar= ruleVarchar EOF )
            // InternalSQL.g:340:2: iv_ruleVarchar= ruleVarchar EOF
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
    // InternalSQL.g:346:1: ruleVarchar returns [EObject current=null] : (otherlv_0= 'VARCHAR' otherlv_1= '(' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ')' ) ;
    public final EObject ruleVarchar() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_size_2_0=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSQL.g:352:2: ( (otherlv_0= 'VARCHAR' otherlv_1= '(' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ')' ) )
            // InternalSQL.g:353:2: (otherlv_0= 'VARCHAR' otherlv_1= '(' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ')' )
            {
            // InternalSQL.g:353:2: (otherlv_0= 'VARCHAR' otherlv_1= '(' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ')' )
            // InternalSQL.g:354:3: otherlv_0= 'VARCHAR' otherlv_1= '(' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ')'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getVarcharAccess().getVARCHARKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_11); 

            			newLeafNode(otherlv_1, grammarAccess.getVarcharAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQL.g:362:3: ( (lv_size_2_0= RULE_INT ) )
            // InternalSQL.g:363:4: (lv_size_2_0= RULE_INT )
            {
            // InternalSQL.g:363:4: (lv_size_2_0= RULE_INT )
            // InternalSQL.g:364:5: lv_size_2_0= RULE_INT
            {
            lv_size_2_0=(Token)match(input,RULE_INT,FOLLOW_12); 

            					newLeafNode(lv_size_2_0, grammarAccess.getVarcharAccess().getSizeINTTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVarcharRule());
            					}
            					setWithLastConsumed(
            						current,
            						"size",
            						lv_size_2_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_3=(Token)match(input,15,FOLLOW_2); 

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
    // InternalSQL.g:388:1: entryRuleConstraint returns [EObject current=null] : iv_ruleConstraint= ruleConstraint EOF ;
    public final EObject entryRuleConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstraint = null;


        try {
            // InternalSQL.g:388:51: (iv_ruleConstraint= ruleConstraint EOF )
            // InternalSQL.g:389:2: iv_ruleConstraint= ruleConstraint EOF
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
    // InternalSQL.g:395:1: ruleConstraint returns [EObject current=null] : (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull ) ;
    public final EObject ruleConstraint() throws RecognitionException {
        EObject current = null;

        EObject this_PrimaryAnnotation_0 = null;

        EObject this_UniqueAnnotation_1 = null;

        EObject this_NotNull_2 = null;



        	enterRule();

        try {
            // InternalSQL.g:401:2: ( (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull ) )
            // InternalSQL.g:402:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull )
            {
            // InternalSQL.g:402:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull )
            int alt7=3;
            switch ( input.LA(1) ) {
            case 17:
                {
                alt7=1;
                }
                break;
            case 19:
                {
                alt7=2;
                }
                break;
            case 20:
                {
                alt7=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // InternalSQL.g:403:3: this_PrimaryAnnotation_0= rulePrimaryAnnotation
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
                    // InternalSQL.g:412:3: this_UniqueAnnotation_1= ruleUniqueAnnotation
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
                    // InternalSQL.g:421:3: this_NotNull_2= ruleNotNull
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
    // InternalSQL.g:433:1: entryRulePrimaryAnnotation returns [EObject current=null] : iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF ;
    public final EObject entryRulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryAnnotation = null;


        try {
            // InternalSQL.g:433:58: (iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF )
            // InternalSQL.g:434:2: iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF
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
    // InternalSQL.g:440:1: rulePrimaryAnnotation returns [EObject current=null] : ( () otherlv_1= 'PRIMARY' otherlv_2= 'KEY' ) ;
    public final EObject rulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSQL.g:446:2: ( ( () otherlv_1= 'PRIMARY' otherlv_2= 'KEY' ) )
            // InternalSQL.g:447:2: ( () otherlv_1= 'PRIMARY' otherlv_2= 'KEY' )
            {
            // InternalSQL.g:447:2: ( () otherlv_1= 'PRIMARY' otherlv_2= 'KEY' )
            // InternalSQL.g:448:3: () otherlv_1= 'PRIMARY' otherlv_2= 'KEY'
            {
            // InternalSQL.g:448:3: ()
            // InternalSQL.g:449:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getPrimaryAnnotationAccess().getPrimaryAnnotationAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,17,FOLLOW_13); 

            			newLeafNode(otherlv_1, grammarAccess.getPrimaryAnnotationAccess().getPRIMARYKeyword_1());
            		
            otherlv_2=(Token)match(input,18,FOLLOW_2); 

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
    // InternalSQL.g:467:1: entryRuleUniqueAnnotation returns [EObject current=null] : iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF ;
    public final EObject entryRuleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueAnnotation = null;


        try {
            // InternalSQL.g:467:57: (iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF )
            // InternalSQL.g:468:2: iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF
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
    // InternalSQL.g:474:1: ruleUniqueAnnotation returns [EObject current=null] : ( () otherlv_1= 'UNIQUE' ) ;
    public final EObject ruleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalSQL.g:480:2: ( ( () otherlv_1= 'UNIQUE' ) )
            // InternalSQL.g:481:2: ( () otherlv_1= 'UNIQUE' )
            {
            // InternalSQL.g:481:2: ( () otherlv_1= 'UNIQUE' )
            // InternalSQL.g:482:3: () otherlv_1= 'UNIQUE'
            {
            // InternalSQL.g:482:3: ()
            // InternalSQL.g:483:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getUniqueAnnotationAccess().getUniqueAnnotationAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,19,FOLLOW_2); 

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
    // InternalSQL.g:497:1: entryRuleNotNull returns [EObject current=null] : iv_ruleNotNull= ruleNotNull EOF ;
    public final EObject entryRuleNotNull() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNotNull = null;


        try {
            // InternalSQL.g:497:48: (iv_ruleNotNull= ruleNotNull EOF )
            // InternalSQL.g:498:2: iv_ruleNotNull= ruleNotNull EOF
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
    // InternalSQL.g:504:1: ruleNotNull returns [EObject current=null] : ( () otherlv_1= 'NOT' otherlv_2= 'NULL' ) ;
    public final EObject ruleNotNull() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSQL.g:510:2: ( ( () otherlv_1= 'NOT' otherlv_2= 'NULL' ) )
            // InternalSQL.g:511:2: ( () otherlv_1= 'NOT' otherlv_2= 'NULL' )
            {
            // InternalSQL.g:511:2: ( () otherlv_1= 'NOT' otherlv_2= 'NULL' )
            // InternalSQL.g:512:3: () otherlv_1= 'NOT' otherlv_2= 'NULL'
            {
            // InternalSQL.g:512:3: ()
            // InternalSQL.g:513:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getNotNullAccess().getNotNullAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,20,FOLLOW_14); 

            			newLeafNode(otherlv_1, grammarAccess.getNotNullAccess().getNOTKeyword_1());
            		
            otherlv_2=(Token)match(input,21,FOLLOW_2); 

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
    // InternalSQL.g:531:1: entryRuleTableConstraint returns [EObject current=null] : iv_ruleTableConstraint= ruleTableConstraint EOF ;
    public final EObject entryRuleTableConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableConstraint = null;


        try {
            // InternalSQL.g:531:56: (iv_ruleTableConstraint= ruleTableConstraint EOF )
            // InternalSQL.g:532:2: iv_ruleTableConstraint= ruleTableConstraint EOF
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
    // InternalSQL.g:538:1: ruleTableConstraint returns [EObject current=null] : (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey ) ;
    public final EObject ruleTableConstraint() throws RecognitionException {
        EObject current = null;

        EObject this_PrimaryKey_0 = null;

        EObject this_UniqueKey_1 = null;



        	enterRule();

        try {
            // InternalSQL.g:544:2: ( (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey ) )
            // InternalSQL.g:545:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey )
            {
            // InternalSQL.g:545:2: (this_PrimaryKey_0= rulePrimaryKey | this_UniqueKey_1= ruleUniqueKey )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==17) ) {
                alt8=1;
            }
            else if ( (LA8_0==19) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalSQL.g:546:3: this_PrimaryKey_0= rulePrimaryKey
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
                    // InternalSQL.g:555:3: this_UniqueKey_1= ruleUniqueKey
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
    // InternalSQL.g:567:1: entryRulePrimaryKey returns [EObject current=null] : iv_rulePrimaryKey= rulePrimaryKey EOF ;
    public final EObject entryRulePrimaryKey() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryKey = null;


        try {
            // InternalSQL.g:567:51: (iv_rulePrimaryKey= rulePrimaryKey EOF )
            // InternalSQL.g:568:2: iv_rulePrimaryKey= rulePrimaryKey EOF
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
    // InternalSQL.g:574:1: rulePrimaryKey returns [EObject current=null] : (otherlv_0= 'PRIMARY' otherlv_1= 'KEY' otherlv_2= '(' ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* otherlv_6= ')' ) ;
    public final EObject rulePrimaryKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;


        	enterRule();

        try {
            // InternalSQL.g:580:2: ( (otherlv_0= 'PRIMARY' otherlv_1= 'KEY' otherlv_2= '(' ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* otherlv_6= ')' ) )
            // InternalSQL.g:581:2: (otherlv_0= 'PRIMARY' otherlv_1= 'KEY' otherlv_2= '(' ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* otherlv_6= ')' )
            {
            // InternalSQL.g:581:2: (otherlv_0= 'PRIMARY' otherlv_1= 'KEY' otherlv_2= '(' ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* otherlv_6= ')' )
            // InternalSQL.g:582:3: otherlv_0= 'PRIMARY' otherlv_1= 'KEY' otherlv_2= '(' ( (otherlv_3= RULE_ID ) ) (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )* otherlv_6= ')'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_13); 

            			newLeafNode(otherlv_0, grammarAccess.getPrimaryKeyAccess().getPRIMARYKeyword_0());
            		
            otherlv_1=(Token)match(input,18,FOLLOW_6); 

            			newLeafNode(otherlv_1, grammarAccess.getPrimaryKeyAccess().getKEYKeyword_1());
            		
            otherlv_2=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getPrimaryKeyAccess().getLeftParenthesisKeyword_2());
            		
            // InternalSQL.g:594:3: ( (otherlv_3= RULE_ID ) )
            // InternalSQL.g:595:4: (otherlv_3= RULE_ID )
            {
            // InternalSQL.g:595:4: (otherlv_3= RULE_ID )
            // InternalSQL.g:596:5: otherlv_3= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPrimaryKeyRule());
            					}
            				
            otherlv_3=(Token)match(input,RULE_ID,FOLLOW_8); 

            					newLeafNode(otherlv_3, grammarAccess.getPrimaryKeyAccess().getColumnsColumnCrossReference_3_0());
            				

            }


            }

            // InternalSQL.g:607:3: (otherlv_4= ',' ( (otherlv_5= RULE_ID ) ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==14) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalSQL.g:608:4: otherlv_4= ',' ( (otherlv_5= RULE_ID ) )
            	    {
            	    otherlv_4=(Token)match(input,14,FOLLOW_5); 

            	    				newLeafNode(otherlv_4, grammarAccess.getPrimaryKeyAccess().getCommaKeyword_4_0());
            	    			
            	    // InternalSQL.g:612:4: ( (otherlv_5= RULE_ID ) )
            	    // InternalSQL.g:613:5: (otherlv_5= RULE_ID )
            	    {
            	    // InternalSQL.g:613:5: (otherlv_5= RULE_ID )
            	    // InternalSQL.g:614:6: otherlv_5= RULE_ID
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getPrimaryKeyRule());
            	    						}
            	    					
            	    otherlv_5=(Token)match(input,RULE_ID,FOLLOW_8); 

            	    						newLeafNode(otherlv_5, grammarAccess.getPrimaryKeyAccess().getColumnsColumnCrossReference_4_1_0());
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            otherlv_6=(Token)match(input,15,FOLLOW_2); 

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
    // InternalSQL.g:634:1: entryRuleUniqueKey returns [EObject current=null] : iv_ruleUniqueKey= ruleUniqueKey EOF ;
    public final EObject entryRuleUniqueKey() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueKey = null;


        try {
            // InternalSQL.g:634:50: (iv_ruleUniqueKey= ruleUniqueKey EOF )
            // InternalSQL.g:635:2: iv_ruleUniqueKey= ruleUniqueKey EOF
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
    // InternalSQL.g:641:1: ruleUniqueKey returns [EObject current=null] : (otherlv_0= 'UNIQUE' otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) (otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) )* otherlv_5= ')' ) ;
    public final EObject ruleUniqueKey() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalSQL.g:647:2: ( (otherlv_0= 'UNIQUE' otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) (otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) )* otherlv_5= ')' ) )
            // InternalSQL.g:648:2: (otherlv_0= 'UNIQUE' otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) (otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) )* otherlv_5= ')' )
            {
            // InternalSQL.g:648:2: (otherlv_0= 'UNIQUE' otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) (otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) )* otherlv_5= ')' )
            // InternalSQL.g:649:3: otherlv_0= 'UNIQUE' otherlv_1= '(' ( (otherlv_2= RULE_ID ) ) (otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) )* otherlv_5= ')'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getUniqueKeyAccess().getUNIQUEKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getUniqueKeyAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQL.g:657:3: ( (otherlv_2= RULE_ID ) )
            // InternalSQL.g:658:4: (otherlv_2= RULE_ID )
            {
            // InternalSQL.g:658:4: (otherlv_2= RULE_ID )
            // InternalSQL.g:659:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getUniqueKeyRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_8); 

            					newLeafNode(otherlv_2, grammarAccess.getUniqueKeyAccess().getColumnsColumnCrossReference_2_0());
            				

            }


            }

            // InternalSQL.g:670:3: (otherlv_3= ',' ( (otherlv_4= RULE_ID ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==14) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalSQL.g:671:4: otherlv_3= ',' ( (otherlv_4= RULE_ID ) )
            	    {
            	    otherlv_3=(Token)match(input,14,FOLLOW_5); 

            	    				newLeafNode(otherlv_3, grammarAccess.getUniqueKeyAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalSQL.g:675:4: ( (otherlv_4= RULE_ID ) )
            	    // InternalSQL.g:676:5: (otherlv_4= RULE_ID )
            	    {
            	    // InternalSQL.g:676:5: (otherlv_4= RULE_ID )
            	    // InternalSQL.g:677:6: otherlv_4= RULE_ID
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getUniqueKeyRule());
            	    						}
            	    					
            	    otherlv_4=(Token)match(input,RULE_ID,FOLLOW_8); 

            	    						newLeafNode(otherlv_4, grammarAccess.getUniqueKeyAccess().getColumnsColumnCrossReference_3_1_0());
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            otherlv_5=(Token)match(input,15,FOLLOW_2); 

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


    // $ANTLR start "ruleAllTypes"
    // InternalSQL.g:697:1: ruleAllTypes returns [Enumerator current=null] : ( (enumLiteral_0= 'INT' ) | (enumLiteral_1= 'BIGINT' ) | (enumLiteral_2= 'UUID' ) | (enumLiteral_3= 'DECIMAL' ) | (enumLiteral_4= 'TIMESTAMP' ) | (enumLiteral_5= 'DATE' ) | (enumLiteral_6= 'BOOLEAN' ) | (enumLiteral_7= 'TEXT' ) ) ;
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
            // InternalSQL.g:703:2: ( ( (enumLiteral_0= 'INT' ) | (enumLiteral_1= 'BIGINT' ) | (enumLiteral_2= 'UUID' ) | (enumLiteral_3= 'DECIMAL' ) | (enumLiteral_4= 'TIMESTAMP' ) | (enumLiteral_5= 'DATE' ) | (enumLiteral_6= 'BOOLEAN' ) | (enumLiteral_7= 'TEXT' ) ) )
            // InternalSQL.g:704:2: ( (enumLiteral_0= 'INT' ) | (enumLiteral_1= 'BIGINT' ) | (enumLiteral_2= 'UUID' ) | (enumLiteral_3= 'DECIMAL' ) | (enumLiteral_4= 'TIMESTAMP' ) | (enumLiteral_5= 'DATE' ) | (enumLiteral_6= 'BOOLEAN' ) | (enumLiteral_7= 'TEXT' ) )
            {
            // InternalSQL.g:704:2: ( (enumLiteral_0= 'INT' ) | (enumLiteral_1= 'BIGINT' ) | (enumLiteral_2= 'UUID' ) | (enumLiteral_3= 'DECIMAL' ) | (enumLiteral_4= 'TIMESTAMP' ) | (enumLiteral_5= 'DATE' ) | (enumLiteral_6= 'BOOLEAN' ) | (enumLiteral_7= 'TEXT' ) )
            int alt11=8;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt11=1;
                }
                break;
            case 23:
                {
                alt11=2;
                }
                break;
            case 24:
                {
                alt11=3;
                }
                break;
            case 25:
                {
                alt11=4;
                }
                break;
            case 26:
                {
                alt11=5;
                }
                break;
            case 27:
                {
                alt11=6;
                }
                break;
            case 28:
                {
                alt11=7;
                }
                break;
            case 29:
                {
                alt11=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // InternalSQL.g:705:3: (enumLiteral_0= 'INT' )
                    {
                    // InternalSQL.g:705:3: (enumLiteral_0= 'INT' )
                    // InternalSQL.g:706:4: enumLiteral_0= 'INT'
                    {
                    enumLiteral_0=(Token)match(input,22,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQL.g:713:3: (enumLiteral_1= 'BIGINT' )
                    {
                    // InternalSQL.g:713:3: (enumLiteral_1= 'BIGINT' )
                    // InternalSQL.g:714:4: enumLiteral_1= 'BIGINT'
                    {
                    enumLiteral_1=(Token)match(input,23,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalSQL.g:721:3: (enumLiteral_2= 'UUID' )
                    {
                    // InternalSQL.g:721:3: (enumLiteral_2= 'UUID' )
                    // InternalSQL.g:722:4: enumLiteral_2= 'UUID'
                    {
                    enumLiteral_2=(Token)match(input,24,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalSQL.g:729:3: (enumLiteral_3= 'DECIMAL' )
                    {
                    // InternalSQL.g:729:3: (enumLiteral_3= 'DECIMAL' )
                    // InternalSQL.g:730:4: enumLiteral_3= 'DECIMAL'
                    {
                    enumLiteral_3=(Token)match(input,25,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalSQL.g:737:3: (enumLiteral_4= 'TIMESTAMP' )
                    {
                    // InternalSQL.g:737:3: (enumLiteral_4= 'TIMESTAMP' )
                    // InternalSQL.g:738:4: enumLiteral_4= 'TIMESTAMP'
                    {
                    enumLiteral_4=(Token)match(input,26,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalSQL.g:745:3: (enumLiteral_5= 'DATE' )
                    {
                    // InternalSQL.g:745:3: (enumLiteral_5= 'DATE' )
                    // InternalSQL.g:746:4: enumLiteral_5= 'DATE'
                    {
                    enumLiteral_5=(Token)match(input,27,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalSQL.g:753:3: (enumLiteral_6= 'BOOLEAN' )
                    {
                    // InternalSQL.g:753:3: (enumLiteral_6= 'BOOLEAN' )
                    // InternalSQL.g:754:4: enumLiteral_6= 'BOOLEAN'
                    {
                    enumLiteral_6=(Token)match(input,28,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalSQL.g:761:3: (enumLiteral_7= 'TEXT' )
                    {
                    // InternalSQL.g:761:3: (enumLiteral_7= 'TEXT' )
                    // InternalSQL.g:762:4: enumLiteral_7= 'TEXT'
                    {
                    enumLiteral_7=(Token)match(input,29,FOLLOW_2); 

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


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x00000000000A0010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x000000003FC10000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00000000001A0002L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000200000L});

}