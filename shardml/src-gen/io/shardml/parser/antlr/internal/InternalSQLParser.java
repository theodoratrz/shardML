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
    // InternalSQL.g:108:1: ruleTable returns [EObject current=null] : (otherlv_0= 'CREATE' otherlv_1= 'TABLE' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( (lv_columns_4_0= ruleColumn ) ) (otherlv_5= ',' ( (lv_columns_6_0= ruleColumn ) ) )* otherlv_7= ')' ) ;
    public final EObject ruleTable() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        EObject lv_columns_4_0 = null;

        EObject lv_columns_6_0 = null;



        	enterRule();

        try {
            // InternalSQL.g:114:2: ( (otherlv_0= 'CREATE' otherlv_1= 'TABLE' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( (lv_columns_4_0= ruleColumn ) ) (otherlv_5= ',' ( (lv_columns_6_0= ruleColumn ) ) )* otherlv_7= ')' ) )
            // InternalSQL.g:115:2: (otherlv_0= 'CREATE' otherlv_1= 'TABLE' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( (lv_columns_4_0= ruleColumn ) ) (otherlv_5= ',' ( (lv_columns_6_0= ruleColumn ) ) )* otherlv_7= ')' )
            {
            // InternalSQL.g:115:2: (otherlv_0= 'CREATE' otherlv_1= 'TABLE' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( (lv_columns_4_0= ruleColumn ) ) (otherlv_5= ',' ( (lv_columns_6_0= ruleColumn ) ) )* otherlv_7= ')' )
            // InternalSQL.g:116:3: otherlv_0= 'CREATE' otherlv_1= 'TABLE' ( (lv_name_2_0= RULE_ID ) ) otherlv_3= '(' ( (lv_columns_4_0= ruleColumn ) ) (otherlv_5= ',' ( (lv_columns_6_0= ruleColumn ) ) )* otherlv_7= ')'
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

            otherlv_3=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_3, grammarAccess.getTableAccess().getLeftParenthesisKeyword_3());
            		
            // InternalSQL.g:146:3: ( (lv_columns_4_0= ruleColumn ) )
            // InternalSQL.g:147:4: (lv_columns_4_0= ruleColumn )
            {
            // InternalSQL.g:147:4: (lv_columns_4_0= ruleColumn )
            // InternalSQL.g:148:5: lv_columns_4_0= ruleColumn
            {

            					newCompositeNode(grammarAccess.getTableAccess().getColumnsColumnParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_7);
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

            // InternalSQL.g:165:3: (otherlv_5= ',' ( (lv_columns_6_0= ruleColumn ) ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==14) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalSQL.g:166:4: otherlv_5= ',' ( (lv_columns_6_0= ruleColumn ) )
            	    {
            	    otherlv_5=(Token)match(input,14,FOLLOW_5); 

            	    				newLeafNode(otherlv_5, grammarAccess.getTableAccess().getCommaKeyword_5_0());
            	    			
            	    // InternalSQL.g:170:4: ( (lv_columns_6_0= ruleColumn ) )
            	    // InternalSQL.g:171:5: (lv_columns_6_0= ruleColumn )
            	    {
            	    // InternalSQL.g:171:5: (lv_columns_6_0= ruleColumn )
            	    // InternalSQL.g:172:6: lv_columns_6_0= ruleColumn
            	    {

            	    						newCompositeNode(grammarAccess.getTableAccess().getColumnsColumnParserRuleCall_5_1_0());
            	    					
            	    pushFollow(FOLLOW_7);
            	    lv_columns_6_0=ruleColumn();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getTableRule());
            	    						}
            	    						add(
            	    							current,
            	    							"columns",
            	    							lv_columns_6_0,
            	    							"io.shardml.SQL.Column");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            otherlv_7=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getTableAccess().getRightParenthesisKeyword_6());
            		

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
    // InternalSQL.g:198:1: entryRuleColumn returns [EObject current=null] : iv_ruleColumn= ruleColumn EOF ;
    public final EObject entryRuleColumn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumn = null;


        try {
            // InternalSQL.g:198:47: (iv_ruleColumn= ruleColumn EOF )
            // InternalSQL.g:199:2: iv_ruleColumn= ruleColumn EOF
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
    // InternalSQL.g:205:1: ruleColumn returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* ) ;
    public final EObject ruleColumn() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Enumerator lv_simpleType_1_0 = null;

        EObject lv_varchar_2_0 = null;

        EObject lv_constraint_3_0 = null;



        	enterRule();

        try {
            // InternalSQL.g:211:2: ( ( ( (lv_name_0_0= RULE_ID ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* ) )
            // InternalSQL.g:212:2: ( ( (lv_name_0_0= RULE_ID ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* )
            {
            // InternalSQL.g:212:2: ( ( (lv_name_0_0= RULE_ID ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )* )
            // InternalSQL.g:213:3: ( (lv_name_0_0= RULE_ID ) ) ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) ) ( (lv_constraint_3_0= ruleConstraint ) )*
            {
            // InternalSQL.g:213:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalSQL.g:214:4: (lv_name_0_0= RULE_ID )
            {
            // InternalSQL.g:214:4: (lv_name_0_0= RULE_ID )
            // InternalSQL.g:215:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_8); 

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

            // InternalSQL.g:231:3: ( ( (lv_simpleType_1_0= ruleAllTypes ) ) | ( (lv_varchar_2_0= ruleVarchar ) ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=22 && LA3_0<=29)) ) {
                alt3=1;
            }
            else if ( (LA3_0==16) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalSQL.g:232:4: ( (lv_simpleType_1_0= ruleAllTypes ) )
                    {
                    // InternalSQL.g:232:4: ( (lv_simpleType_1_0= ruleAllTypes ) )
                    // InternalSQL.g:233:5: (lv_simpleType_1_0= ruleAllTypes )
                    {
                    // InternalSQL.g:233:5: (lv_simpleType_1_0= ruleAllTypes )
                    // InternalSQL.g:234:6: lv_simpleType_1_0= ruleAllTypes
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getSimpleTypeAllTypesEnumRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_9);
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
                    // InternalSQL.g:252:4: ( (lv_varchar_2_0= ruleVarchar ) )
                    {
                    // InternalSQL.g:252:4: ( (lv_varchar_2_0= ruleVarchar ) )
                    // InternalSQL.g:253:5: (lv_varchar_2_0= ruleVarchar )
                    {
                    // InternalSQL.g:253:5: (lv_varchar_2_0= ruleVarchar )
                    // InternalSQL.g:254:6: lv_varchar_2_0= ruleVarchar
                    {

                    						newCompositeNode(grammarAccess.getColumnAccess().getVarcharVarcharParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_9);
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

            // InternalSQL.g:272:3: ( (lv_constraint_3_0= ruleConstraint ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==17||(LA4_0>=19 && LA4_0<=20)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalSQL.g:273:4: (lv_constraint_3_0= ruleConstraint )
            	    {
            	    // InternalSQL.g:273:4: (lv_constraint_3_0= ruleConstraint )
            	    // InternalSQL.g:274:5: lv_constraint_3_0= ruleConstraint
            	    {

            	    					newCompositeNode(grammarAccess.getColumnAccess().getConstraintConstraintParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_9);
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
            	    break loop4;
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
    // InternalSQL.g:295:1: entryRuleVarchar returns [EObject current=null] : iv_ruleVarchar= ruleVarchar EOF ;
    public final EObject entryRuleVarchar() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarchar = null;


        try {
            // InternalSQL.g:295:48: (iv_ruleVarchar= ruleVarchar EOF )
            // InternalSQL.g:296:2: iv_ruleVarchar= ruleVarchar EOF
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
    // InternalSQL.g:302:1: ruleVarchar returns [EObject current=null] : (otherlv_0= 'VARCHAR' otherlv_1= '(' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ')' ) ;
    public final EObject ruleVarchar() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token lv_size_2_0=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalSQL.g:308:2: ( (otherlv_0= 'VARCHAR' otherlv_1= '(' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ')' ) )
            // InternalSQL.g:309:2: (otherlv_0= 'VARCHAR' otherlv_1= '(' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ')' )
            {
            // InternalSQL.g:309:2: (otherlv_0= 'VARCHAR' otherlv_1= '(' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ')' )
            // InternalSQL.g:310:3: otherlv_0= 'VARCHAR' otherlv_1= '(' ( (lv_size_2_0= RULE_INT ) ) otherlv_3= ')'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_6); 

            			newLeafNode(otherlv_0, grammarAccess.getVarcharAccess().getVARCHARKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_10); 

            			newLeafNode(otherlv_1, grammarAccess.getVarcharAccess().getLeftParenthesisKeyword_1());
            		
            // InternalSQL.g:318:3: ( (lv_size_2_0= RULE_INT ) )
            // InternalSQL.g:319:4: (lv_size_2_0= RULE_INT )
            {
            // InternalSQL.g:319:4: (lv_size_2_0= RULE_INT )
            // InternalSQL.g:320:5: lv_size_2_0= RULE_INT
            {
            lv_size_2_0=(Token)match(input,RULE_INT,FOLLOW_11); 

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
    // InternalSQL.g:344:1: entryRuleConstraint returns [EObject current=null] : iv_ruleConstraint= ruleConstraint EOF ;
    public final EObject entryRuleConstraint() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleConstraint = null;


        try {
            // InternalSQL.g:344:51: (iv_ruleConstraint= ruleConstraint EOF )
            // InternalSQL.g:345:2: iv_ruleConstraint= ruleConstraint EOF
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
    // InternalSQL.g:351:1: ruleConstraint returns [EObject current=null] : (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull ) ;
    public final EObject ruleConstraint() throws RecognitionException {
        EObject current = null;

        EObject this_PrimaryAnnotation_0 = null;

        EObject this_UniqueAnnotation_1 = null;

        EObject this_NotNull_2 = null;



        	enterRule();

        try {
            // InternalSQL.g:357:2: ( (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull ) )
            // InternalSQL.g:358:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull )
            {
            // InternalSQL.g:358:2: (this_PrimaryAnnotation_0= rulePrimaryAnnotation | this_UniqueAnnotation_1= ruleUniqueAnnotation | this_NotNull_2= ruleNotNull )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 17:
                {
                alt5=1;
                }
                break;
            case 19:
                {
                alt5=2;
                }
                break;
            case 20:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // InternalSQL.g:359:3: this_PrimaryAnnotation_0= rulePrimaryAnnotation
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
                    // InternalSQL.g:368:3: this_UniqueAnnotation_1= ruleUniqueAnnotation
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
                    // InternalSQL.g:377:3: this_NotNull_2= ruleNotNull
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
    // InternalSQL.g:389:1: entryRulePrimaryAnnotation returns [EObject current=null] : iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF ;
    public final EObject entryRulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimaryAnnotation = null;


        try {
            // InternalSQL.g:389:58: (iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF )
            // InternalSQL.g:390:2: iv_rulePrimaryAnnotation= rulePrimaryAnnotation EOF
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
    // InternalSQL.g:396:1: rulePrimaryAnnotation returns [EObject current=null] : ( () otherlv_1= 'PRIMARY' otherlv_2= 'KEY' ) ;
    public final EObject rulePrimaryAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSQL.g:402:2: ( ( () otherlv_1= 'PRIMARY' otherlv_2= 'KEY' ) )
            // InternalSQL.g:403:2: ( () otherlv_1= 'PRIMARY' otherlv_2= 'KEY' )
            {
            // InternalSQL.g:403:2: ( () otherlv_1= 'PRIMARY' otherlv_2= 'KEY' )
            // InternalSQL.g:404:3: () otherlv_1= 'PRIMARY' otherlv_2= 'KEY'
            {
            // InternalSQL.g:404:3: ()
            // InternalSQL.g:405:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getPrimaryAnnotationAccess().getPrimaryAnnotationAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,17,FOLLOW_12); 

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
    // InternalSQL.g:423:1: entryRuleUniqueAnnotation returns [EObject current=null] : iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF ;
    public final EObject entryRuleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUniqueAnnotation = null;


        try {
            // InternalSQL.g:423:57: (iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF )
            // InternalSQL.g:424:2: iv_ruleUniqueAnnotation= ruleUniqueAnnotation EOF
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
    // InternalSQL.g:430:1: ruleUniqueAnnotation returns [EObject current=null] : ( () otherlv_1= 'UNIQUE' ) ;
    public final EObject ruleUniqueAnnotation() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalSQL.g:436:2: ( ( () otherlv_1= 'UNIQUE' ) )
            // InternalSQL.g:437:2: ( () otherlv_1= 'UNIQUE' )
            {
            // InternalSQL.g:437:2: ( () otherlv_1= 'UNIQUE' )
            // InternalSQL.g:438:3: () otherlv_1= 'UNIQUE'
            {
            // InternalSQL.g:438:3: ()
            // InternalSQL.g:439:4: 
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
    // InternalSQL.g:453:1: entryRuleNotNull returns [EObject current=null] : iv_ruleNotNull= ruleNotNull EOF ;
    public final EObject entryRuleNotNull() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNotNull = null;


        try {
            // InternalSQL.g:453:48: (iv_ruleNotNull= ruleNotNull EOF )
            // InternalSQL.g:454:2: iv_ruleNotNull= ruleNotNull EOF
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
    // InternalSQL.g:460:1: ruleNotNull returns [EObject current=null] : ( () otherlv_1= 'NOT' otherlv_2= 'NULL' ) ;
    public final EObject ruleNotNull() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalSQL.g:466:2: ( ( () otherlv_1= 'NOT' otherlv_2= 'NULL' ) )
            // InternalSQL.g:467:2: ( () otherlv_1= 'NOT' otherlv_2= 'NULL' )
            {
            // InternalSQL.g:467:2: ( () otherlv_1= 'NOT' otherlv_2= 'NULL' )
            // InternalSQL.g:468:3: () otherlv_1= 'NOT' otherlv_2= 'NULL'
            {
            // InternalSQL.g:468:3: ()
            // InternalSQL.g:469:4: 
            {

            				current = forceCreateModelElement(
            					grammarAccess.getNotNullAccess().getNotNullAction_0(),
            					current);
            			

            }

            otherlv_1=(Token)match(input,20,FOLLOW_13); 

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


    // $ANTLR start "ruleAllTypes"
    // InternalSQL.g:487:1: ruleAllTypes returns [Enumerator current=null] : ( (enumLiteral_0= 'INT' ) | (enumLiteral_1= 'BIGINT' ) | (enumLiteral_2= 'UUID' ) | (enumLiteral_3= 'DECIMAL' ) | (enumLiteral_4= 'TIMESTAMP' ) | (enumLiteral_5= 'DATE' ) | (enumLiteral_6= 'BOOLEAN' ) | (enumLiteral_7= 'TEXT' ) ) ;
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
            // InternalSQL.g:493:2: ( ( (enumLiteral_0= 'INT' ) | (enumLiteral_1= 'BIGINT' ) | (enumLiteral_2= 'UUID' ) | (enumLiteral_3= 'DECIMAL' ) | (enumLiteral_4= 'TIMESTAMP' ) | (enumLiteral_5= 'DATE' ) | (enumLiteral_6= 'BOOLEAN' ) | (enumLiteral_7= 'TEXT' ) ) )
            // InternalSQL.g:494:2: ( (enumLiteral_0= 'INT' ) | (enumLiteral_1= 'BIGINT' ) | (enumLiteral_2= 'UUID' ) | (enumLiteral_3= 'DECIMAL' ) | (enumLiteral_4= 'TIMESTAMP' ) | (enumLiteral_5= 'DATE' ) | (enumLiteral_6= 'BOOLEAN' ) | (enumLiteral_7= 'TEXT' ) )
            {
            // InternalSQL.g:494:2: ( (enumLiteral_0= 'INT' ) | (enumLiteral_1= 'BIGINT' ) | (enumLiteral_2= 'UUID' ) | (enumLiteral_3= 'DECIMAL' ) | (enumLiteral_4= 'TIMESTAMP' ) | (enumLiteral_5= 'DATE' ) | (enumLiteral_6= 'BOOLEAN' ) | (enumLiteral_7= 'TEXT' ) )
            int alt6=8;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt6=1;
                }
                break;
            case 23:
                {
                alt6=2;
                }
                break;
            case 24:
                {
                alt6=3;
                }
                break;
            case 25:
                {
                alt6=4;
                }
                break;
            case 26:
                {
                alt6=5;
                }
                break;
            case 27:
                {
                alt6=6;
                }
                break;
            case 28:
                {
                alt6=7;
                }
                break;
            case 29:
                {
                alt6=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalSQL.g:495:3: (enumLiteral_0= 'INT' )
                    {
                    // InternalSQL.g:495:3: (enumLiteral_0= 'INT' )
                    // InternalSQL.g:496:4: enumLiteral_0= 'INT'
                    {
                    enumLiteral_0=(Token)match(input,22,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalSQL.g:503:3: (enumLiteral_1= 'BIGINT' )
                    {
                    // InternalSQL.g:503:3: (enumLiteral_1= 'BIGINT' )
                    // InternalSQL.g:504:4: enumLiteral_1= 'BIGINT'
                    {
                    enumLiteral_1=(Token)match(input,23,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalSQL.g:511:3: (enumLiteral_2= 'UUID' )
                    {
                    // InternalSQL.g:511:3: (enumLiteral_2= 'UUID' )
                    // InternalSQL.g:512:4: enumLiteral_2= 'UUID'
                    {
                    enumLiteral_2=(Token)match(input,24,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalSQL.g:519:3: (enumLiteral_3= 'DECIMAL' )
                    {
                    // InternalSQL.g:519:3: (enumLiteral_3= 'DECIMAL' )
                    // InternalSQL.g:520:4: enumLiteral_3= 'DECIMAL'
                    {
                    enumLiteral_3=(Token)match(input,25,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalSQL.g:527:3: (enumLiteral_4= 'TIMESTAMP' )
                    {
                    // InternalSQL.g:527:3: (enumLiteral_4= 'TIMESTAMP' )
                    // InternalSQL.g:528:4: enumLiteral_4= 'TIMESTAMP'
                    {
                    enumLiteral_4=(Token)match(input,26,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalSQL.g:535:3: (enumLiteral_5= 'DATE' )
                    {
                    // InternalSQL.g:535:3: (enumLiteral_5= 'DATE' )
                    // InternalSQL.g:536:4: enumLiteral_5= 'DATE'
                    {
                    enumLiteral_5=(Token)match(input,27,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalSQL.g:543:3: (enumLiteral_6= 'BOOLEAN' )
                    {
                    // InternalSQL.g:543:3: (enumLiteral_6= 'BOOLEAN' )
                    // InternalSQL.g:544:4: enumLiteral_6= 'BOOLEAN'
                    {
                    enumLiteral_6=(Token)match(input,28,FOLLOW_2); 

                    				current = grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalSQL.g:551:3: (enumLiteral_7= 'TEXT' )
                    {
                    // InternalSQL.g:551:3: (enumLiteral_7= 'TEXT' )
                    // InternalSQL.g:552:4: enumLiteral_7= 'TEXT'
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
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x000000003FC10000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00000000001A0002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000200000L});

}