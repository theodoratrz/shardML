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
import io.shardml.services.WorkloadGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalWorkloadParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_INT", "RULE_ID", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "'workload'", "'{'", "'}'", "'param'", "'='", "'domain'", "'size'", "':'", "'within'", "'table'", "'rows'", "'per'", "','", "'over'", "'unique'", "'.'", "'uniform'", "'zipf'", "'('", "')'", "'transaction'", "'weight'", "'let'", "'else'", "'with'", "'distinct'", "'from'", "'repeat'", "'where'", "'join'", "'on'", "'AND'", "'?'", "'$'", "'read'", "'write'", "'delete'", "'>'", "'<'", "'<='", "'>='", "'!='", "'OR'"
    };
    public static final int T__50=50;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int RULE_ID=6;
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
    public static final int RULE_STRING=4;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;

    // delegates
    // delegators


        public InternalWorkloadParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalWorkloadParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalWorkloadParser.tokenNames; }
    public String getGrammarFileName() { return "InternalWorkload.g"; }



     	private WorkloadGrammarAccess grammarAccess;

        public InternalWorkloadParser(TokenStream input, WorkloadGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "WorkloadModel";
       	}

       	@Override
       	protected WorkloadGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleWorkloadModel"
    // InternalWorkload.g:65:1: entryRuleWorkloadModel returns [EObject current=null] : iv_ruleWorkloadModel= ruleWorkloadModel EOF ;
    public final EObject entryRuleWorkloadModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWorkloadModel = null;


        try {
            // InternalWorkload.g:65:54: (iv_ruleWorkloadModel= ruleWorkloadModel EOF )
            // InternalWorkload.g:66:2: iv_ruleWorkloadModel= ruleWorkloadModel EOF
            {
             newCompositeNode(grammarAccess.getWorkloadModelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleWorkloadModel=ruleWorkloadModel();

            state._fsp--;

             current =iv_ruleWorkloadModel; 
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
    // $ANTLR end "entryRuleWorkloadModel"


    // $ANTLR start "ruleWorkloadModel"
    // InternalWorkload.g:72:1: ruleWorkloadModel returns [EObject current=null] : ( ( (lv_imports_0_0= ruleImport ) )* ( (lv_workload_1_0= ruleWorkload ) ) ) ;
    public final EObject ruleWorkloadModel() throws RecognitionException {
        EObject current = null;

        EObject lv_imports_0_0 = null;

        EObject lv_workload_1_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:78:2: ( ( ( (lv_imports_0_0= ruleImport ) )* ( (lv_workload_1_0= ruleWorkload ) ) ) )
            // InternalWorkload.g:79:2: ( ( (lv_imports_0_0= ruleImport ) )* ( (lv_workload_1_0= ruleWorkload ) ) )
            {
            // InternalWorkload.g:79:2: ( ( (lv_imports_0_0= ruleImport ) )* ( (lv_workload_1_0= ruleWorkload ) ) )
            // InternalWorkload.g:80:3: ( (lv_imports_0_0= ruleImport ) )* ( (lv_workload_1_0= ruleWorkload ) )
            {
            // InternalWorkload.g:80:3: ( (lv_imports_0_0= ruleImport ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalWorkload.g:81:4: (lv_imports_0_0= ruleImport )
            	    {
            	    // InternalWorkload.g:81:4: (lv_imports_0_0= ruleImport )
            	    // InternalWorkload.g:82:5: lv_imports_0_0= ruleImport
            	    {

            	    					newCompositeNode(grammarAccess.getWorkloadModelAccess().getImportsImportParserRuleCall_0_0());
            	    				
            	    pushFollow(FOLLOW_3);
            	    lv_imports_0_0=ruleImport();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getWorkloadModelRule());
            	    					}
            	    					add(
            	    						current,
            	    						"imports",
            	    						lv_imports_0_0,
            	    						"io.shardml.Workload.Import");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // InternalWorkload.g:99:3: ( (lv_workload_1_0= ruleWorkload ) )
            // InternalWorkload.g:100:4: (lv_workload_1_0= ruleWorkload )
            {
            // InternalWorkload.g:100:4: (lv_workload_1_0= ruleWorkload )
            // InternalWorkload.g:101:5: lv_workload_1_0= ruleWorkload
            {

            					newCompositeNode(grammarAccess.getWorkloadModelAccess().getWorkloadWorkloadParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_workload_1_0=ruleWorkload();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWorkloadModelRule());
            					}
            					set(
            						current,
            						"workload",
            						lv_workload_1_0,
            						"io.shardml.Workload.Workload");
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
    // $ANTLR end "ruleWorkloadModel"


    // $ANTLR start "entryRuleImport"
    // InternalWorkload.g:122:1: entryRuleImport returns [EObject current=null] : iv_ruleImport= ruleImport EOF ;
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImport = null;


        try {
            // InternalWorkload.g:122:47: (iv_ruleImport= ruleImport EOF )
            // InternalWorkload.g:123:2: iv_ruleImport= ruleImport EOF
            {
             newCompositeNode(grammarAccess.getImportRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleImport=ruleImport();

            state._fsp--;

             current =iv_ruleImport; 
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
    // $ANTLR end "entryRuleImport"


    // $ANTLR start "ruleImport"
    // InternalWorkload.g:129:1: ruleImport returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_importURI_1_0=null;


        	enterRule();

        try {
            // InternalWorkload.g:135:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) )
            // InternalWorkload.g:136:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            {
            // InternalWorkload.g:136:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            // InternalWorkload.g:137:3: otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,11,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
            		
            // InternalWorkload.g:141:3: ( (lv_importURI_1_0= RULE_STRING ) )
            // InternalWorkload.g:142:4: (lv_importURI_1_0= RULE_STRING )
            {
            // InternalWorkload.g:142:4: (lv_importURI_1_0= RULE_STRING )
            // InternalWorkload.g:143:5: lv_importURI_1_0= RULE_STRING
            {
            lv_importURI_1_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

            					newLeafNode(lv_importURI_1_0, grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getImportRule());
            					}
            					setWithLastConsumed(
            						current,
            						"importURI",
            						lv_importURI_1_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

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
    // $ANTLR end "ruleImport"


    // $ANTLR start "entryRuleWorkload"
    // InternalWorkload.g:163:1: entryRuleWorkload returns [EObject current=null] : iv_ruleWorkload= ruleWorkload EOF ;
    public final EObject entryRuleWorkload() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWorkload = null;


        try {
            // InternalWorkload.g:163:49: (iv_ruleWorkload= ruleWorkload EOF )
            // InternalWorkload.g:164:2: iv_ruleWorkload= ruleWorkload EOF
            {
             newCompositeNode(grammarAccess.getWorkloadRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleWorkload=ruleWorkload();

            state._fsp--;

             current =iv_ruleWorkload; 
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
    // $ANTLR end "entryRuleWorkload"


    // $ANTLR start "ruleWorkload"
    // InternalWorkload.g:170:1: ruleWorkload returns [EObject current=null] : (otherlv_0= 'workload' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_params_3_0= ruleParam ) )* ( (lv_domains_4_0= ruleDomain ) )* ( (lv_tables_5_0= ruleTableData ) )* ( (lv_transactions_6_0= ruleTransaction ) )* otherlv_7= '}' ) ;
    public final EObject ruleWorkload() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_params_3_0 = null;

        EObject lv_domains_4_0 = null;

        EObject lv_tables_5_0 = null;

        EObject lv_transactions_6_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:176:2: ( (otherlv_0= 'workload' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_params_3_0= ruleParam ) )* ( (lv_domains_4_0= ruleDomain ) )* ( (lv_tables_5_0= ruleTableData ) )* ( (lv_transactions_6_0= ruleTransaction ) )* otherlv_7= '}' ) )
            // InternalWorkload.g:177:2: (otherlv_0= 'workload' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_params_3_0= ruleParam ) )* ( (lv_domains_4_0= ruleDomain ) )* ( (lv_tables_5_0= ruleTableData ) )* ( (lv_transactions_6_0= ruleTransaction ) )* otherlv_7= '}' )
            {
            // InternalWorkload.g:177:2: (otherlv_0= 'workload' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_params_3_0= ruleParam ) )* ( (lv_domains_4_0= ruleDomain ) )* ( (lv_tables_5_0= ruleTableData ) )* ( (lv_transactions_6_0= ruleTransaction ) )* otherlv_7= '}' )
            // InternalWorkload.g:178:3: otherlv_0= 'workload' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' ( (lv_params_3_0= ruleParam ) )* ( (lv_domains_4_0= ruleDomain ) )* ( (lv_tables_5_0= ruleTableData ) )* ( (lv_transactions_6_0= ruleTransaction ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getWorkloadAccess().getWorkloadKeyword_0());
            		
            // InternalWorkload.g:182:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalWorkload.g:183:4: (lv_name_1_0= ruleValidID )
            {
            // InternalWorkload.g:183:4: (lv_name_1_0= ruleValidID )
            // InternalWorkload.g:184:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getWorkloadAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_6);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWorkloadRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"io.shardml.Workload.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_7); 

            			newLeafNode(otherlv_2, grammarAccess.getWorkloadAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalWorkload.g:205:3: ( (lv_params_3_0= ruleParam ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==15) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalWorkload.g:206:4: (lv_params_3_0= ruleParam )
            	    {
            	    // InternalWorkload.g:206:4: (lv_params_3_0= ruleParam )
            	    // InternalWorkload.g:207:5: lv_params_3_0= ruleParam
            	    {

            	    					newCompositeNode(grammarAccess.getWorkloadAccess().getParamsParamParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_7);
            	    lv_params_3_0=ruleParam();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getWorkloadRule());
            	    					}
            	    					add(
            	    						current,
            	    						"params",
            	    						lv_params_3_0,
            	    						"io.shardml.Workload.Param");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // InternalWorkload.g:224:3: ( (lv_domains_4_0= ruleDomain ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==17) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalWorkload.g:225:4: (lv_domains_4_0= ruleDomain )
            	    {
            	    // InternalWorkload.g:225:4: (lv_domains_4_0= ruleDomain )
            	    // InternalWorkload.g:226:5: lv_domains_4_0= ruleDomain
            	    {

            	    					newCompositeNode(grammarAccess.getWorkloadAccess().getDomainsDomainParserRuleCall_4_0());
            	    				
            	    pushFollow(FOLLOW_8);
            	    lv_domains_4_0=ruleDomain();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getWorkloadRule());
            	    					}
            	    					add(
            	    						current,
            	    						"domains",
            	    						lv_domains_4_0,
            	    						"io.shardml.Workload.Domain");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // InternalWorkload.g:243:3: ( (lv_tables_5_0= ruleTableData ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==21) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalWorkload.g:244:4: (lv_tables_5_0= ruleTableData )
            	    {
            	    // InternalWorkload.g:244:4: (lv_tables_5_0= ruleTableData )
            	    // InternalWorkload.g:245:5: lv_tables_5_0= ruleTableData
            	    {

            	    					newCompositeNode(grammarAccess.getWorkloadAccess().getTablesTableDataParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_9);
            	    lv_tables_5_0=ruleTableData();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getWorkloadRule());
            	    					}
            	    					add(
            	    						current,
            	    						"tables",
            	    						lv_tables_5_0,
            	    						"io.shardml.Workload.TableData");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            // InternalWorkload.g:262:3: ( (lv_transactions_6_0= ruleTransaction ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==32) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalWorkload.g:263:4: (lv_transactions_6_0= ruleTransaction )
            	    {
            	    // InternalWorkload.g:263:4: (lv_transactions_6_0= ruleTransaction )
            	    // InternalWorkload.g:264:5: lv_transactions_6_0= ruleTransaction
            	    {

            	    					newCompositeNode(grammarAccess.getWorkloadAccess().getTransactionsTransactionParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_10);
            	    lv_transactions_6_0=ruleTransaction();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getWorkloadRule());
            	    					}
            	    					add(
            	    						current,
            	    						"transactions",
            	    						lv_transactions_6_0,
            	    						"io.shardml.Workload.Transaction");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            otherlv_7=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getWorkloadAccess().getRightCurlyBracketKeyword_7());
            		

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
    // $ANTLR end "ruleWorkload"


    // $ANTLR start "entryRuleParam"
    // InternalWorkload.g:289:1: entryRuleParam returns [EObject current=null] : iv_ruleParam= ruleParam EOF ;
    public final EObject entryRuleParam() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam = null;


        try {
            // InternalWorkload.g:289:46: (iv_ruleParam= ruleParam EOF )
            // InternalWorkload.g:290:2: iv_ruleParam= ruleParam EOF
            {
             newCompositeNode(grammarAccess.getParamRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParam=ruleParam();

            state._fsp--;

             current =iv_ruleParam; 
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
    // $ANTLR end "entryRuleParam"


    // $ANTLR start "ruleParam"
    // InternalWorkload.g:296:1: ruleParam returns [EObject current=null] : (otherlv_0= 'param' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '=' ( (lv_value_3_0= ruleDecimal ) ) ) ;
    public final EObject ruleParam() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:302:2: ( (otherlv_0= 'param' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '=' ( (lv_value_3_0= ruleDecimal ) ) ) )
            // InternalWorkload.g:303:2: (otherlv_0= 'param' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '=' ( (lv_value_3_0= ruleDecimal ) ) )
            {
            // InternalWorkload.g:303:2: (otherlv_0= 'param' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '=' ( (lv_value_3_0= ruleDecimal ) ) )
            // InternalWorkload.g:304:3: otherlv_0= 'param' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '=' ( (lv_value_3_0= ruleDecimal ) )
            {
            otherlv_0=(Token)match(input,15,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getParamAccess().getParamKeyword_0());
            		
            // InternalWorkload.g:308:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalWorkload.g:309:4: (lv_name_1_0= ruleValidID )
            {
            // InternalWorkload.g:309:4: (lv_name_1_0= ruleValidID )
            // InternalWorkload.g:310:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getParamAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_11);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getParamRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"io.shardml.Workload.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,16,FOLLOW_12); 

            			newLeafNode(otherlv_2, grammarAccess.getParamAccess().getEqualsSignKeyword_2());
            		
            // InternalWorkload.g:331:3: ( (lv_value_3_0= ruleDecimal ) )
            // InternalWorkload.g:332:4: (lv_value_3_0= ruleDecimal )
            {
            // InternalWorkload.g:332:4: (lv_value_3_0= ruleDecimal )
            // InternalWorkload.g:333:5: lv_value_3_0= ruleDecimal
            {

            					newCompositeNode(grammarAccess.getParamAccess().getValueDecimalParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_value_3_0=ruleDecimal();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getParamRule());
            					}
            					set(
            						current,
            						"value",
            						lv_value_3_0,
            						"io.shardml.Workload.Decimal");
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
    // $ANTLR end "ruleParam"


    // $ANTLR start "entryRuleDomain"
    // InternalWorkload.g:354:1: entryRuleDomain returns [EObject current=null] : iv_ruleDomain= ruleDomain EOF ;
    public final EObject entryRuleDomain() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDomain = null;


        try {
            // InternalWorkload.g:354:47: (iv_ruleDomain= ruleDomain EOF )
            // InternalWorkload.g:355:2: iv_ruleDomain= ruleDomain EOF
            {
             newCompositeNode(grammarAccess.getDomainRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDomain=ruleDomain();

            state._fsp--;

             current =iv_ruleDomain; 
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
    // $ANTLR end "entryRuleDomain"


    // $ANTLR start "ruleDomain"
    // InternalWorkload.g:361:1: ruleDomain returns [EObject current=null] : (otherlv_0= 'domain' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'size' otherlv_4= ':' ( (lv_size_5_0= ruleCount ) ) (otherlv_6= 'within' ( ( ruleValidID ) ) ( (lv_children_8_0= ruleDistribution ) ) )? otherlv_9= '}' ) ;
    public final EObject ruleDomain() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_size_5_0 = null;

        EObject lv_children_8_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:367:2: ( (otherlv_0= 'domain' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'size' otherlv_4= ':' ( (lv_size_5_0= ruleCount ) ) (otherlv_6= 'within' ( ( ruleValidID ) ) ( (lv_children_8_0= ruleDistribution ) ) )? otherlv_9= '}' ) )
            // InternalWorkload.g:368:2: (otherlv_0= 'domain' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'size' otherlv_4= ':' ( (lv_size_5_0= ruleCount ) ) (otherlv_6= 'within' ( ( ruleValidID ) ) ( (lv_children_8_0= ruleDistribution ) ) )? otherlv_9= '}' )
            {
            // InternalWorkload.g:368:2: (otherlv_0= 'domain' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'size' otherlv_4= ':' ( (lv_size_5_0= ruleCount ) ) (otherlv_6= 'within' ( ( ruleValidID ) ) ( (lv_children_8_0= ruleDistribution ) ) )? otherlv_9= '}' )
            // InternalWorkload.g:369:3: otherlv_0= 'domain' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'size' otherlv_4= ':' ( (lv_size_5_0= ruleCount ) ) (otherlv_6= 'within' ( ( ruleValidID ) ) ( (lv_children_8_0= ruleDistribution ) ) )? otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getDomainAccess().getDomainKeyword_0());
            		
            // InternalWorkload.g:373:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalWorkload.g:374:4: (lv_name_1_0= ruleValidID )
            {
            // InternalWorkload.g:374:4: (lv_name_1_0= ruleValidID )
            // InternalWorkload.g:375:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getDomainAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_6);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDomainRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"io.shardml.Workload.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_13); 

            			newLeafNode(otherlv_2, grammarAccess.getDomainAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,18,FOLLOW_14); 

            			newLeafNode(otherlv_3, grammarAccess.getDomainAccess().getSizeKeyword_3());
            		
            otherlv_4=(Token)match(input,19,FOLLOW_12); 

            			newLeafNode(otherlv_4, grammarAccess.getDomainAccess().getColonKeyword_4());
            		
            // InternalWorkload.g:404:3: ( (lv_size_5_0= ruleCount ) )
            // InternalWorkload.g:405:4: (lv_size_5_0= ruleCount )
            {
            // InternalWorkload.g:405:4: (lv_size_5_0= ruleCount )
            // InternalWorkload.g:406:5: lv_size_5_0= ruleCount
            {

            					newCompositeNode(grammarAccess.getDomainAccess().getSizeCountParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_15);
            lv_size_5_0=ruleCount();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDomainRule());
            					}
            					set(
            						current,
            						"size",
            						lv_size_5_0,
            						"io.shardml.Workload.Count");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:423:3: (otherlv_6= 'within' ( ( ruleValidID ) ) ( (lv_children_8_0= ruleDistribution ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==20) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalWorkload.g:424:4: otherlv_6= 'within' ( ( ruleValidID ) ) ( (lv_children_8_0= ruleDistribution ) )
                    {
                    otherlv_6=(Token)match(input,20,FOLLOW_5); 

                    				newLeafNode(otherlv_6, grammarAccess.getDomainAccess().getWithinKeyword_6_0());
                    			
                    // InternalWorkload.g:428:4: ( ( ruleValidID ) )
                    // InternalWorkload.g:429:5: ( ruleValidID )
                    {
                    // InternalWorkload.g:429:5: ( ruleValidID )
                    // InternalWorkload.g:430:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDomainRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getDomainAccess().getParentDomainCrossReference_6_1_0());
                    					
                    pushFollow(FOLLOW_16);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalWorkload.g:444:4: ( (lv_children_8_0= ruleDistribution ) )
                    // InternalWorkload.g:445:5: (lv_children_8_0= ruleDistribution )
                    {
                    // InternalWorkload.g:445:5: (lv_children_8_0= ruleDistribution )
                    // InternalWorkload.g:446:6: lv_children_8_0= ruleDistribution
                    {

                    						newCompositeNode(grammarAccess.getDomainAccess().getChildrenDistributionParserRuleCall_6_2_0());
                    					
                    pushFollow(FOLLOW_17);
                    lv_children_8_0=ruleDistribution();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDomainRule());
                    						}
                    						set(
                    							current,
                    							"children",
                    							lv_children_8_0,
                    							"io.shardml.Workload.Distribution");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_9=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getDomainAccess().getRightCurlyBracketKeyword_7());
            		

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
    // $ANTLR end "ruleDomain"


    // $ANTLR start "entryRuleTableData"
    // InternalWorkload.g:472:1: entryRuleTableData returns [EObject current=null] : iv_ruleTableData= ruleTableData EOF ;
    public final EObject entryRuleTableData() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTableData = null;


        try {
            // InternalWorkload.g:472:50: (iv_ruleTableData= ruleTableData EOF )
            // InternalWorkload.g:473:2: iv_ruleTableData= ruleTableData EOF
            {
             newCompositeNode(grammarAccess.getTableDataRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTableData=ruleTableData();

            state._fsp--;

             current =iv_ruleTableData; 
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
    // $ANTLR end "entryRuleTableData"


    // $ANTLR start "ruleTableData"
    // InternalWorkload.g:479:1: ruleTableData returns [EObject current=null] : (otherlv_0= 'table' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'rows' otherlv_4= ':' ( (lv_rows_5_0= ruleRows ) ) ( (lv_columns_6_0= ruleColumnMap ) )* otherlv_7= '}' ) ;
    public final EObject ruleTableData() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        EObject lv_rows_5_0 = null;

        EObject lv_columns_6_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:485:2: ( (otherlv_0= 'table' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'rows' otherlv_4= ':' ( (lv_rows_5_0= ruleRows ) ) ( (lv_columns_6_0= ruleColumnMap ) )* otherlv_7= '}' ) )
            // InternalWorkload.g:486:2: (otherlv_0= 'table' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'rows' otherlv_4= ':' ( (lv_rows_5_0= ruleRows ) ) ( (lv_columns_6_0= ruleColumnMap ) )* otherlv_7= '}' )
            {
            // InternalWorkload.g:486:2: (otherlv_0= 'table' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'rows' otherlv_4= ':' ( (lv_rows_5_0= ruleRows ) ) ( (lv_columns_6_0= ruleColumnMap ) )* otherlv_7= '}' )
            // InternalWorkload.g:487:3: otherlv_0= 'table' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'rows' otherlv_4= ':' ( (lv_rows_5_0= ruleRows ) ) ( (lv_columns_6_0= ruleColumnMap ) )* otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,21,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getTableDataAccess().getTableKeyword_0());
            		
            // InternalWorkload.g:491:3: ( ( ruleValidID ) )
            // InternalWorkload.g:492:4: ( ruleValidID )
            {
            // InternalWorkload.g:492:4: ( ruleValidID )
            // InternalWorkload.g:493:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTableDataRule());
            					}
            				

            					newCompositeNode(grammarAccess.getTableDataAccess().getTableTableCrossReference_1_0());
            				
            pushFollow(FOLLOW_6);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_18); 

            			newLeafNode(otherlv_2, grammarAccess.getTableDataAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,22,FOLLOW_14); 

            			newLeafNode(otherlv_3, grammarAccess.getTableDataAccess().getRowsKeyword_3());
            		
            otherlv_4=(Token)match(input,19,FOLLOW_19); 

            			newLeafNode(otherlv_4, grammarAccess.getTableDataAccess().getColonKeyword_4());
            		
            // InternalWorkload.g:519:3: ( (lv_rows_5_0= ruleRows ) )
            // InternalWorkload.g:520:4: (lv_rows_5_0= ruleRows )
            {
            // InternalWorkload.g:520:4: (lv_rows_5_0= ruleRows )
            // InternalWorkload.g:521:5: lv_rows_5_0= ruleRows
            {

            					newCompositeNode(grammarAccess.getTableDataAccess().getRowsRowsParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_20);
            lv_rows_5_0=ruleRows();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTableDataRule());
            					}
            					set(
            						current,
            						"rows",
            						lv_rows_5_0,
            						"io.shardml.Workload.Rows");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:538:3: ( (lv_columns_6_0= ruleColumnMap ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==RULE_ID||(LA7_0>=11 && LA7_0<=12)||LA7_0==15||(LA7_0>=17 && LA7_0<=18)||(LA7_0>=20 && LA7_0<=23)||(LA7_0>=25 && LA7_0<=26)||(LA7_0>=28 && LA7_0<=29)||(LA7_0>=32 && LA7_0<=42)||(LA7_0>=46 && LA7_0<=48)) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalWorkload.g:539:4: (lv_columns_6_0= ruleColumnMap )
            	    {
            	    // InternalWorkload.g:539:4: (lv_columns_6_0= ruleColumnMap )
            	    // InternalWorkload.g:540:5: lv_columns_6_0= ruleColumnMap
            	    {

            	    					newCompositeNode(grammarAccess.getTableDataAccess().getColumnsColumnMapParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_20);
            	    lv_columns_6_0=ruleColumnMap();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getTableDataRule());
            	    					}
            	    					add(
            	    						current,
            	    						"columns",
            	    						lv_columns_6_0,
            	    						"io.shardml.Workload.ColumnMap");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            otherlv_7=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getTableDataAccess().getRightCurlyBracketKeyword_7());
            		

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
    // $ANTLR end "ruleTableData"


    // $ANTLR start "entryRuleRows"
    // InternalWorkload.g:565:1: entryRuleRows returns [EObject current=null] : iv_ruleRows= ruleRows EOF ;
    public final EObject entryRuleRows() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRows = null;


        try {
            // InternalWorkload.g:565:45: (iv_ruleRows= ruleRows EOF )
            // InternalWorkload.g:566:2: iv_ruleRows= ruleRows EOF
            {
             newCompositeNode(grammarAccess.getRowsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRows=ruleRows();

            state._fsp--;

             current =iv_ruleRows; 
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
    // $ANTLR end "entryRuleRows"


    // $ANTLR start "ruleRows"
    // InternalWorkload.g:572:1: ruleRows returns [EObject current=null] : (this_PerRows_0= rulePerRows | this_OverRows_1= ruleOverRows ) ;
    public final EObject ruleRows() throws RecognitionException {
        EObject current = null;

        EObject this_PerRows_0 = null;

        EObject this_OverRows_1 = null;



        	enterRule();

        try {
            // InternalWorkload.g:578:2: ( (this_PerRows_0= rulePerRows | this_OverRows_1= ruleOverRows ) )
            // InternalWorkload.g:579:2: (this_PerRows_0= rulePerRows | this_OverRows_1= ruleOverRows )
            {
            // InternalWorkload.g:579:2: (this_PerRows_0= rulePerRows | this_OverRows_1= ruleOverRows )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_INT) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==23) ) {
                    alt8=1;
                }
                else if ( (LA8_1==25) ) {
                    alt8=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA8_0==23) ) {
                alt8=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalWorkload.g:580:3: this_PerRows_0= rulePerRows
                    {

                    			newCompositeNode(grammarAccess.getRowsAccess().getPerRowsParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_PerRows_0=rulePerRows();

                    state._fsp--;


                    			current = this_PerRows_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalWorkload.g:589:3: this_OverRows_1= ruleOverRows
                    {

                    			newCompositeNode(grammarAccess.getRowsAccess().getOverRowsParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_OverRows_1=ruleOverRows();

                    state._fsp--;


                    			current = this_OverRows_1;
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
    // $ANTLR end "ruleRows"


    // $ANTLR start "entryRulePerRows"
    // InternalWorkload.g:601:1: entryRulePerRows returns [EObject current=null] : iv_rulePerRows= rulePerRows EOF ;
    public final EObject entryRulePerRows() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePerRows = null;


        try {
            // InternalWorkload.g:601:48: (iv_rulePerRows= rulePerRows EOF )
            // InternalWorkload.g:602:2: iv_rulePerRows= rulePerRows EOF
            {
             newCompositeNode(grammarAccess.getPerRowsRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePerRows=rulePerRows();

            state._fsp--;

             current =iv_rulePerRows; 
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
    // $ANTLR end "entryRulePerRows"


    // $ANTLR start "rulePerRows"
    // InternalWorkload.g:608:1: rulePerRows returns [EObject current=null] : ( ( (lv_count_0_0= ruleCount ) )? otherlv_1= 'per' ( ( ruleValidID ) ) (otherlv_3= ',' ( ( ruleValidID ) ) )* ) ;
    public final EObject rulePerRows() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_count_0_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:614:2: ( ( ( (lv_count_0_0= ruleCount ) )? otherlv_1= 'per' ( ( ruleValidID ) ) (otherlv_3= ',' ( ( ruleValidID ) ) )* ) )
            // InternalWorkload.g:615:2: ( ( (lv_count_0_0= ruleCount ) )? otherlv_1= 'per' ( ( ruleValidID ) ) (otherlv_3= ',' ( ( ruleValidID ) ) )* )
            {
            // InternalWorkload.g:615:2: ( ( (lv_count_0_0= ruleCount ) )? otherlv_1= 'per' ( ( ruleValidID ) ) (otherlv_3= ',' ( ( ruleValidID ) ) )* )
            // InternalWorkload.g:616:3: ( (lv_count_0_0= ruleCount ) )? otherlv_1= 'per' ( ( ruleValidID ) ) (otherlv_3= ',' ( ( ruleValidID ) ) )*
            {
            // InternalWorkload.g:616:3: ( (lv_count_0_0= ruleCount ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==RULE_INT) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalWorkload.g:617:4: (lv_count_0_0= ruleCount )
                    {
                    // InternalWorkload.g:617:4: (lv_count_0_0= ruleCount )
                    // InternalWorkload.g:618:5: lv_count_0_0= ruleCount
                    {

                    					newCompositeNode(grammarAccess.getPerRowsAccess().getCountCountParserRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_21);
                    lv_count_0_0=ruleCount();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getPerRowsRule());
                    					}
                    					set(
                    						current,
                    						"count",
                    						lv_count_0_0,
                    						"io.shardml.Workload.Count");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            otherlv_1=(Token)match(input,23,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getPerRowsAccess().getPerKeyword_1());
            		
            // InternalWorkload.g:639:3: ( ( ruleValidID ) )
            // InternalWorkload.g:640:4: ( ruleValidID )
            {
            // InternalWorkload.g:640:4: ( ruleValidID )
            // InternalWorkload.g:641:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPerRowsRule());
            					}
            				

            					newCompositeNode(grammarAccess.getPerRowsAccess().getDomainsDomainCrossReference_2_0());
            				
            pushFollow(FOLLOW_22);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:655:3: (otherlv_3= ',' ( ( ruleValidID ) ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==24) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalWorkload.g:656:4: otherlv_3= ',' ( ( ruleValidID ) )
            	    {
            	    otherlv_3=(Token)match(input,24,FOLLOW_5); 

            	    				newLeafNode(otherlv_3, grammarAccess.getPerRowsAccess().getCommaKeyword_3_0());
            	    			
            	    // InternalWorkload.g:660:4: ( ( ruleValidID ) )
            	    // InternalWorkload.g:661:5: ( ruleValidID )
            	    {
            	    // InternalWorkload.g:661:5: ( ruleValidID )
            	    // InternalWorkload.g:662:6: ruleValidID
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getPerRowsRule());
            	    						}
            	    					

            	    						newCompositeNode(grammarAccess.getPerRowsAccess().getDomainsDomainCrossReference_3_1_0());
            	    					
            	    pushFollow(FOLLOW_22);
            	    ruleValidID();

            	    state._fsp--;


            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


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
    // $ANTLR end "rulePerRows"


    // $ANTLR start "entryRuleOverRows"
    // InternalWorkload.g:681:1: entryRuleOverRows returns [EObject current=null] : iv_ruleOverRows= ruleOverRows EOF ;
    public final EObject entryRuleOverRows() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOverRows = null;


        try {
            // InternalWorkload.g:681:49: (iv_ruleOverRows= ruleOverRows EOF )
            // InternalWorkload.g:682:2: iv_ruleOverRows= ruleOverRows EOF
            {
             newCompositeNode(grammarAccess.getOverRowsRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOverRows=ruleOverRows();

            state._fsp--;

             current =iv_ruleOverRows; 
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
    // $ANTLR end "entryRuleOverRows"


    // $ANTLR start "ruleOverRows"
    // InternalWorkload.g:688:1: ruleOverRows returns [EObject current=null] : ( ( (lv_count_0_0= ruleCount ) ) otherlv_1= 'over' ( ( ruleValidID ) ) ( (lv_distribution_3_0= ruleDistribution ) ) ) ;
    public final EObject ruleOverRows() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_count_0_0 = null;

        EObject lv_distribution_3_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:694:2: ( ( ( (lv_count_0_0= ruleCount ) ) otherlv_1= 'over' ( ( ruleValidID ) ) ( (lv_distribution_3_0= ruleDistribution ) ) ) )
            // InternalWorkload.g:695:2: ( ( (lv_count_0_0= ruleCount ) ) otherlv_1= 'over' ( ( ruleValidID ) ) ( (lv_distribution_3_0= ruleDistribution ) ) )
            {
            // InternalWorkload.g:695:2: ( ( (lv_count_0_0= ruleCount ) ) otherlv_1= 'over' ( ( ruleValidID ) ) ( (lv_distribution_3_0= ruleDistribution ) ) )
            // InternalWorkload.g:696:3: ( (lv_count_0_0= ruleCount ) ) otherlv_1= 'over' ( ( ruleValidID ) ) ( (lv_distribution_3_0= ruleDistribution ) )
            {
            // InternalWorkload.g:696:3: ( (lv_count_0_0= ruleCount ) )
            // InternalWorkload.g:697:4: (lv_count_0_0= ruleCount )
            {
            // InternalWorkload.g:697:4: (lv_count_0_0= ruleCount )
            // InternalWorkload.g:698:5: lv_count_0_0= ruleCount
            {

            					newCompositeNode(grammarAccess.getOverRowsAccess().getCountCountParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_23);
            lv_count_0_0=ruleCount();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOverRowsRule());
            					}
            					set(
            						current,
            						"count",
            						lv_count_0_0,
            						"io.shardml.Workload.Count");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,25,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getOverRowsAccess().getOverKeyword_1());
            		
            // InternalWorkload.g:719:3: ( ( ruleValidID ) )
            // InternalWorkload.g:720:4: ( ruleValidID )
            {
            // InternalWorkload.g:720:4: ( ruleValidID )
            // InternalWorkload.g:721:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getOverRowsRule());
            					}
            				

            					newCompositeNode(grammarAccess.getOverRowsAccess().getDomainDomainCrossReference_2_0());
            				
            pushFollow(FOLLOW_16);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:735:3: ( (lv_distribution_3_0= ruleDistribution ) )
            // InternalWorkload.g:736:4: (lv_distribution_3_0= ruleDistribution )
            {
            // InternalWorkload.g:736:4: (lv_distribution_3_0= ruleDistribution )
            // InternalWorkload.g:737:5: lv_distribution_3_0= ruleDistribution
            {

            					newCompositeNode(grammarAccess.getOverRowsAccess().getDistributionDistributionParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_2);
            lv_distribution_3_0=ruleDistribution();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOverRowsRule());
            					}
            					set(
            						current,
            						"distribution",
            						lv_distribution_3_0,
            						"io.shardml.Workload.Distribution");
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
    // $ANTLR end "ruleOverRows"


    // $ANTLR start "entryRuleColumnMap"
    // InternalWorkload.g:758:1: entryRuleColumnMap returns [EObject current=null] : iv_ruleColumnMap= ruleColumnMap EOF ;
    public final EObject entryRuleColumnMap() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumnMap = null;


        try {
            // InternalWorkload.g:758:50: (iv_ruleColumnMap= ruleColumnMap EOF )
            // InternalWorkload.g:759:2: iv_ruleColumnMap= ruleColumnMap EOF
            {
             newCompositeNode(grammarAccess.getColumnMapRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleColumnMap=ruleColumnMap();

            state._fsp--;

             current =iv_ruleColumnMap; 
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
    // $ANTLR end "entryRuleColumnMap"


    // $ANTLR start "ruleColumnMap"
    // InternalWorkload.g:765:1: ruleColumnMap returns [EObject current=null] : ( ( () otherlv_1= 'unique' ( ( ruleValidID ) ) ) | ( () ( ( ruleValidID ) ) otherlv_5= ':' ( (lv_path_6_0= ruleKeyPath ) ) ) ) ;
    public final EObject ruleColumnMap() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_5=null;
        EObject lv_path_6_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:771:2: ( ( ( () otherlv_1= 'unique' ( ( ruleValidID ) ) ) | ( () ( ( ruleValidID ) ) otherlv_5= ':' ( (lv_path_6_0= ruleKeyPath ) ) ) ) )
            // InternalWorkload.g:772:2: ( ( () otherlv_1= 'unique' ( ( ruleValidID ) ) ) | ( () ( ( ruleValidID ) ) otherlv_5= ':' ( (lv_path_6_0= ruleKeyPath ) ) ) )
            {
            // InternalWorkload.g:772:2: ( ( () otherlv_1= 'unique' ( ( ruleValidID ) ) ) | ( () ( ( ruleValidID ) ) otherlv_5= ':' ( (lv_path_6_0= ruleKeyPath ) ) ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==26) ) {
                int LA11_1 = input.LA(2);

                if ( (LA11_1==RULE_ID||(LA11_1>=11 && LA11_1<=12)||LA11_1==15||(LA11_1>=17 && LA11_1<=18)||(LA11_1>=20 && LA11_1<=23)||(LA11_1>=25 && LA11_1<=26)||(LA11_1>=28 && LA11_1<=29)||(LA11_1>=32 && LA11_1<=42)||(LA11_1>=46 && LA11_1<=48)) ) {
                    alt11=1;
                }
                else if ( (LA11_1==19) ) {
                    alt11=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA11_0==RULE_ID||(LA11_0>=11 && LA11_0<=12)||LA11_0==15||(LA11_0>=17 && LA11_0<=18)||(LA11_0>=20 && LA11_0<=23)||LA11_0==25||(LA11_0>=28 && LA11_0<=29)||(LA11_0>=32 && LA11_0<=42)||(LA11_0>=46 && LA11_0<=48)) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // InternalWorkload.g:773:3: ( () otherlv_1= 'unique' ( ( ruleValidID ) ) )
                    {
                    // InternalWorkload.g:773:3: ( () otherlv_1= 'unique' ( ( ruleValidID ) ) )
                    // InternalWorkload.g:774:4: () otherlv_1= 'unique' ( ( ruleValidID ) )
                    {
                    // InternalWorkload.g:774:4: ()
                    // InternalWorkload.g:775:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getColumnMapAccess().getUniqueColumnAction_0_0(),
                    						current);
                    				

                    }

                    otherlv_1=(Token)match(input,26,FOLLOW_5); 

                    				newLeafNode(otherlv_1, grammarAccess.getColumnMapAccess().getUniqueKeyword_0_1());
                    			
                    // InternalWorkload.g:785:4: ( ( ruleValidID ) )
                    // InternalWorkload.g:786:5: ( ruleValidID )
                    {
                    // InternalWorkload.g:786:5: ( ruleValidID )
                    // InternalWorkload.g:787:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getColumnMapRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getColumnMapAccess().getColumnColumnCrossReference_0_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalWorkload.g:803:3: ( () ( ( ruleValidID ) ) otherlv_5= ':' ( (lv_path_6_0= ruleKeyPath ) ) )
                    {
                    // InternalWorkload.g:803:3: ( () ( ( ruleValidID ) ) otherlv_5= ':' ( (lv_path_6_0= ruleKeyPath ) ) )
                    // InternalWorkload.g:804:4: () ( ( ruleValidID ) ) otherlv_5= ':' ( (lv_path_6_0= ruleKeyPath ) )
                    {
                    // InternalWorkload.g:804:4: ()
                    // InternalWorkload.g:805:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getColumnMapAccess().getMappedColumnAction_1_0(),
                    						current);
                    				

                    }

                    // InternalWorkload.g:811:4: ( ( ruleValidID ) )
                    // InternalWorkload.g:812:5: ( ruleValidID )
                    {
                    // InternalWorkload.g:812:5: ( ruleValidID )
                    // InternalWorkload.g:813:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getColumnMapRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getColumnMapAccess().getColumnColumnCrossReference_1_1_0());
                    					
                    pushFollow(FOLLOW_14);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_5=(Token)match(input,19,FOLLOW_5); 

                    				newLeafNode(otherlv_5, grammarAccess.getColumnMapAccess().getColonKeyword_1_2());
                    			
                    // InternalWorkload.g:831:4: ( (lv_path_6_0= ruleKeyPath ) )
                    // InternalWorkload.g:832:5: (lv_path_6_0= ruleKeyPath )
                    {
                    // InternalWorkload.g:832:5: (lv_path_6_0= ruleKeyPath )
                    // InternalWorkload.g:833:6: lv_path_6_0= ruleKeyPath
                    {

                    						newCompositeNode(grammarAccess.getColumnMapAccess().getPathKeyPathParserRuleCall_1_3_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_path_6_0=ruleKeyPath();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getColumnMapRule());
                    						}
                    						set(
                    							current,
                    							"path",
                    							lv_path_6_0,
                    							"io.shardml.Workload.KeyPath");
                    						afterParserOrEnumRuleCall();
                    					

                    }


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
    // $ANTLR end "ruleColumnMap"


    // $ANTLR start "entryRuleKeyPath"
    // InternalWorkload.g:855:1: entryRuleKeyPath returns [EObject current=null] : iv_ruleKeyPath= ruleKeyPath EOF ;
    public final EObject entryRuleKeyPath() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleKeyPath = null;


        try {
            // InternalWorkload.g:855:48: (iv_ruleKeyPath= ruleKeyPath EOF )
            // InternalWorkload.g:856:2: iv_ruleKeyPath= ruleKeyPath EOF
            {
             newCompositeNode(grammarAccess.getKeyPathRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleKeyPath=ruleKeyPath();

            state._fsp--;

             current =iv_ruleKeyPath; 
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
    // $ANTLR end "entryRuleKeyPath"


    // $ANTLR start "ruleKeyPath"
    // InternalWorkload.g:862:1: ruleKeyPath returns [EObject current=null] : ( ( ( ruleValidID ) ) (otherlv_1= '.' ( ( ruleValidID ) ) )* ) ;
    public final EObject ruleKeyPath() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalWorkload.g:868:2: ( ( ( ( ruleValidID ) ) (otherlv_1= '.' ( ( ruleValidID ) ) )* ) )
            // InternalWorkload.g:869:2: ( ( ( ruleValidID ) ) (otherlv_1= '.' ( ( ruleValidID ) ) )* )
            {
            // InternalWorkload.g:869:2: ( ( ( ruleValidID ) ) (otherlv_1= '.' ( ( ruleValidID ) ) )* )
            // InternalWorkload.g:870:3: ( ( ruleValidID ) ) (otherlv_1= '.' ( ( ruleValidID ) ) )*
            {
            // InternalWorkload.g:870:3: ( ( ruleValidID ) )
            // InternalWorkload.g:871:4: ( ruleValidID )
            {
            // InternalWorkload.g:871:4: ( ruleValidID )
            // InternalWorkload.g:872:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getKeyPathRule());
            					}
            				

            					newCompositeNode(grammarAccess.getKeyPathAccess().getDomainDomainCrossReference_0_0());
            				
            pushFollow(FOLLOW_24);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:886:3: (otherlv_1= '.' ( ( ruleValidID ) ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==27) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalWorkload.g:887:4: otherlv_1= '.' ( ( ruleValidID ) )
            	    {
            	    otherlv_1=(Token)match(input,27,FOLLOW_5); 

            	    				newLeafNode(otherlv_1, grammarAccess.getKeyPathAccess().getFullStopKeyword_1_0());
            	    			
            	    // InternalWorkload.g:891:4: ( ( ruleValidID ) )
            	    // InternalWorkload.g:892:5: ( ruleValidID )
            	    {
            	    // InternalWorkload.g:892:5: ( ruleValidID )
            	    // InternalWorkload.g:893:6: ruleValidID
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getKeyPathRule());
            	    						}
            	    					

            	    						newCompositeNode(grammarAccess.getKeyPathAccess().getParentsDomainCrossReference_1_1_0());
            	    					
            	    pushFollow(FOLLOW_24);
            	    ruleValidID();

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
    // $ANTLR end "ruleKeyPath"


    // $ANTLR start "entryRuleDistribution"
    // InternalWorkload.g:912:1: entryRuleDistribution returns [EObject current=null] : iv_ruleDistribution= ruleDistribution EOF ;
    public final EObject entryRuleDistribution() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDistribution = null;


        try {
            // InternalWorkload.g:912:53: (iv_ruleDistribution= ruleDistribution EOF )
            // InternalWorkload.g:913:2: iv_ruleDistribution= ruleDistribution EOF
            {
             newCompositeNode(grammarAccess.getDistributionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDistribution=ruleDistribution();

            state._fsp--;

             current =iv_ruleDistribution; 
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
    // $ANTLR end "entryRuleDistribution"


    // $ANTLR start "ruleDistribution"
    // InternalWorkload.g:919:1: ruleDistribution returns [EObject current=null] : ( ( () otherlv_1= 'uniform' ) | ( () otherlv_3= 'zipf' otherlv_4= '(' ( (lv_s_5_0= ruleNum ) ) otherlv_6= ')' ) ) ;
    public final EObject ruleDistribution() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject lv_s_5_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:925:2: ( ( ( () otherlv_1= 'uniform' ) | ( () otherlv_3= 'zipf' otherlv_4= '(' ( (lv_s_5_0= ruleNum ) ) otherlv_6= ')' ) ) )
            // InternalWorkload.g:926:2: ( ( () otherlv_1= 'uniform' ) | ( () otherlv_3= 'zipf' otherlv_4= '(' ( (lv_s_5_0= ruleNum ) ) otherlv_6= ')' ) )
            {
            // InternalWorkload.g:926:2: ( ( () otherlv_1= 'uniform' ) | ( () otherlv_3= 'zipf' otherlv_4= '(' ( (lv_s_5_0= ruleNum ) ) otherlv_6= ')' ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==28) ) {
                alt13=1;
            }
            else if ( (LA13_0==29) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // InternalWorkload.g:927:3: ( () otherlv_1= 'uniform' )
                    {
                    // InternalWorkload.g:927:3: ( () otherlv_1= 'uniform' )
                    // InternalWorkload.g:928:4: () otherlv_1= 'uniform'
                    {
                    // InternalWorkload.g:928:4: ()
                    // InternalWorkload.g:929:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getDistributionAccess().getUniformAction_0_0(),
                    						current);
                    				

                    }

                    otherlv_1=(Token)match(input,28,FOLLOW_2); 

                    				newLeafNode(otherlv_1, grammarAccess.getDistributionAccess().getUniformKeyword_0_1());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalWorkload.g:941:3: ( () otherlv_3= 'zipf' otherlv_4= '(' ( (lv_s_5_0= ruleNum ) ) otherlv_6= ')' )
                    {
                    // InternalWorkload.g:941:3: ( () otherlv_3= 'zipf' otherlv_4= '(' ( (lv_s_5_0= ruleNum ) ) otherlv_6= ')' )
                    // InternalWorkload.g:942:4: () otherlv_3= 'zipf' otherlv_4= '(' ( (lv_s_5_0= ruleNum ) ) otherlv_6= ')'
                    {
                    // InternalWorkload.g:942:4: ()
                    // InternalWorkload.g:943:5: 
                    {

                    					current = forceCreateModelElement(
                    						grammarAccess.getDistributionAccess().getZipfAction_1_0(),
                    						current);
                    				

                    }

                    otherlv_3=(Token)match(input,29,FOLLOW_25); 

                    				newLeafNode(otherlv_3, grammarAccess.getDistributionAccess().getZipfKeyword_1_1());
                    			
                    otherlv_4=(Token)match(input,30,FOLLOW_26); 

                    				newLeafNode(otherlv_4, grammarAccess.getDistributionAccess().getLeftParenthesisKeyword_1_2());
                    			
                    // InternalWorkload.g:957:4: ( (lv_s_5_0= ruleNum ) )
                    // InternalWorkload.g:958:5: (lv_s_5_0= ruleNum )
                    {
                    // InternalWorkload.g:958:5: (lv_s_5_0= ruleNum )
                    // InternalWorkload.g:959:6: lv_s_5_0= ruleNum
                    {

                    						newCompositeNode(grammarAccess.getDistributionAccess().getSNumParserRuleCall_1_3_0());
                    					
                    pushFollow(FOLLOW_27);
                    lv_s_5_0=ruleNum();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDistributionRule());
                    						}
                    						set(
                    							current,
                    							"s",
                    							lv_s_5_0,
                    							"io.shardml.Workload.Num");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_6=(Token)match(input,31,FOLLOW_2); 

                    				newLeafNode(otherlv_6, grammarAccess.getDistributionAccess().getRightParenthesisKeyword_1_4());
                    			

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
    // $ANTLR end "ruleDistribution"


    // $ANTLR start "entryRuleTransaction"
    // InternalWorkload.g:985:1: entryRuleTransaction returns [EObject current=null] : iv_ruleTransaction= ruleTransaction EOF ;
    public final EObject entryRuleTransaction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTransaction = null;


        try {
            // InternalWorkload.g:985:52: (iv_ruleTransaction= ruleTransaction EOF )
            // InternalWorkload.g:986:2: iv_ruleTransaction= ruleTransaction EOF
            {
             newCompositeNode(grammarAccess.getTransactionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTransaction=ruleTransaction();

            state._fsp--;

             current =iv_ruleTransaction; 
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
    // $ANTLR end "entryRuleTransaction"


    // $ANTLR start "ruleTransaction"
    // InternalWorkload.g:992:1: ruleTransaction returns [EObject current=null] : (otherlv_0= 'transaction' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= 'weight' ( (lv_weight_3_0= ruleNum ) ) otherlv_4= '{' ( (lv_steps_5_0= ruleStep ) )* otherlv_6= '}' ) ;
    public final EObject ruleTransaction() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_weight_3_0 = null;

        EObject lv_steps_5_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:998:2: ( (otherlv_0= 'transaction' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= 'weight' ( (lv_weight_3_0= ruleNum ) ) otherlv_4= '{' ( (lv_steps_5_0= ruleStep ) )* otherlv_6= '}' ) )
            // InternalWorkload.g:999:2: (otherlv_0= 'transaction' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= 'weight' ( (lv_weight_3_0= ruleNum ) ) otherlv_4= '{' ( (lv_steps_5_0= ruleStep ) )* otherlv_6= '}' )
            {
            // InternalWorkload.g:999:2: (otherlv_0= 'transaction' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= 'weight' ( (lv_weight_3_0= ruleNum ) ) otherlv_4= '{' ( (lv_steps_5_0= ruleStep ) )* otherlv_6= '}' )
            // InternalWorkload.g:1000:3: otherlv_0= 'transaction' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= 'weight' ( (lv_weight_3_0= ruleNum ) ) otherlv_4= '{' ( (lv_steps_5_0= ruleStep ) )* otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,32,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getTransactionAccess().getTransactionKeyword_0());
            		
            // InternalWorkload.g:1004:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalWorkload.g:1005:4: (lv_name_1_0= ruleValidID )
            {
            // InternalWorkload.g:1005:4: (lv_name_1_0= ruleValidID )
            // InternalWorkload.g:1006:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getTransactionAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_28);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTransactionRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"io.shardml.Workload.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,33,FOLLOW_26); 

            			newLeafNode(otherlv_2, grammarAccess.getTransactionAccess().getWeightKeyword_2());
            		
            // InternalWorkload.g:1027:3: ( (lv_weight_3_0= ruleNum ) )
            // InternalWorkload.g:1028:4: (lv_weight_3_0= ruleNum )
            {
            // InternalWorkload.g:1028:4: (lv_weight_3_0= ruleNum )
            // InternalWorkload.g:1029:5: lv_weight_3_0= ruleNum
            {

            					newCompositeNode(grammarAccess.getTransactionAccess().getWeightNumParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_6);
            lv_weight_3_0=ruleNum();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getTransactionRule());
            					}
            					set(
            						current,
            						"weight",
            						lv_weight_3_0,
            						"io.shardml.Workload.Num");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_4=(Token)match(input,13,FOLLOW_29); 

            			newLeafNode(otherlv_4, grammarAccess.getTransactionAccess().getLeftCurlyBracketKeyword_4());
            		
            // InternalWorkload.g:1050:3: ( (lv_steps_5_0= ruleStep ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==34||LA14_0==39||(LA14_0>=46 && LA14_0<=48)) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalWorkload.g:1051:4: (lv_steps_5_0= ruleStep )
            	    {
            	    // InternalWorkload.g:1051:4: (lv_steps_5_0= ruleStep )
            	    // InternalWorkload.g:1052:5: lv_steps_5_0= ruleStep
            	    {

            	    					newCompositeNode(grammarAccess.getTransactionAccess().getStepsStepParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_29);
            	    lv_steps_5_0=ruleStep();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getTransactionRule());
            	    					}
            	    					add(
            	    						current,
            	    						"steps",
            	    						lv_steps_5_0,
            	    						"io.shardml.Workload.Step");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            otherlv_6=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getTransactionAccess().getRightCurlyBracketKeyword_6());
            		

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
    // $ANTLR end "ruleTransaction"


    // $ANTLR start "entryRuleStep"
    // InternalWorkload.g:1077:1: entryRuleStep returns [EObject current=null] : iv_ruleStep= ruleStep EOF ;
    public final EObject entryRuleStep() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStep = null;


        try {
            // InternalWorkload.g:1077:45: (iv_ruleStep= ruleStep EOF )
            // InternalWorkload.g:1078:2: iv_ruleStep= ruleStep EOF
            {
             newCompositeNode(grammarAccess.getStepRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStep=ruleStep();

            state._fsp--;

             current =iv_ruleStep; 
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
    // $ANTLR end "entryRuleStep"


    // $ANTLR start "ruleStep"
    // InternalWorkload.g:1084:1: ruleStep returns [EObject current=null] : (this_Let_0= ruleLet | this_Repeat_1= ruleRepeat | this_Statement_2= ruleStatement ) ;
    public final EObject ruleStep() throws RecognitionException {
        EObject current = null;

        EObject this_Let_0 = null;

        EObject this_Repeat_1 = null;

        EObject this_Statement_2 = null;



        	enterRule();

        try {
            // InternalWorkload.g:1090:2: ( (this_Let_0= ruleLet | this_Repeat_1= ruleRepeat | this_Statement_2= ruleStatement ) )
            // InternalWorkload.g:1091:2: (this_Let_0= ruleLet | this_Repeat_1= ruleRepeat | this_Statement_2= ruleStatement )
            {
            // InternalWorkload.g:1091:2: (this_Let_0= ruleLet | this_Repeat_1= ruleRepeat | this_Statement_2= ruleStatement )
            int alt15=3;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt15=1;
                }
                break;
            case 39:
                {
                alt15=2;
                }
                break;
            case 46:
            case 47:
            case 48:
                {
                alt15=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // InternalWorkload.g:1092:3: this_Let_0= ruleLet
                    {

                    			newCompositeNode(grammarAccess.getStepAccess().getLetParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_Let_0=ruleLet();

                    state._fsp--;


                    			current = this_Let_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalWorkload.g:1101:3: this_Repeat_1= ruleRepeat
                    {

                    			newCompositeNode(grammarAccess.getStepAccess().getRepeatParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_Repeat_1=ruleRepeat();

                    state._fsp--;


                    			current = this_Repeat_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalWorkload.g:1110:3: this_Statement_2= ruleStatement
                    {

                    			newCompositeNode(grammarAccess.getStepAccess().getStatementParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_Statement_2=ruleStatement();

                    state._fsp--;


                    			current = this_Statement_2;
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
    // $ANTLR end "ruleStep"


    // $ANTLR start "entryRuleLet"
    // InternalWorkload.g:1122:1: entryRuleLet returns [EObject current=null] : iv_ruleLet= ruleLet EOF ;
    public final EObject entryRuleLet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLet = null;


        try {
            // InternalWorkload.g:1122:44: (iv_ruleLet= ruleLet EOF )
            // InternalWorkload.g:1123:2: iv_ruleLet= ruleLet EOF
            {
             newCompositeNode(grammarAccess.getLetRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLet=ruleLet();

            state._fsp--;

             current =iv_ruleLet; 
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
    // $ANTLR end "entryRuleLet"


    // $ANTLR start "ruleLet"
    // InternalWorkload.g:1129:1: ruleLet returns [EObject current=null] : (otherlv_0= 'let' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '=' ( ( (lv_draw_3_0= ruleDraw ) ) | ( ( (lv_base_4_0= ruleVarRef ) ) otherlv_5= ',' otherlv_6= 'else' otherlv_7= 'with' ( (lv_remote_8_0= ruleNum ) ) otherlv_9= ':' ( (lv_draw_10_0= ruleDraw ) ) ) ) ) ;
    public final EObject ruleLet() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_draw_3_0 = null;

        EObject lv_base_4_0 = null;

        EObject lv_remote_8_0 = null;

        EObject lv_draw_10_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:1135:2: ( (otherlv_0= 'let' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '=' ( ( (lv_draw_3_0= ruleDraw ) ) | ( ( (lv_base_4_0= ruleVarRef ) ) otherlv_5= ',' otherlv_6= 'else' otherlv_7= 'with' ( (lv_remote_8_0= ruleNum ) ) otherlv_9= ':' ( (lv_draw_10_0= ruleDraw ) ) ) ) ) )
            // InternalWorkload.g:1136:2: (otherlv_0= 'let' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '=' ( ( (lv_draw_3_0= ruleDraw ) ) | ( ( (lv_base_4_0= ruleVarRef ) ) otherlv_5= ',' otherlv_6= 'else' otherlv_7= 'with' ( (lv_remote_8_0= ruleNum ) ) otherlv_9= ':' ( (lv_draw_10_0= ruleDraw ) ) ) ) )
            {
            // InternalWorkload.g:1136:2: (otherlv_0= 'let' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '=' ( ( (lv_draw_3_0= ruleDraw ) ) | ( ( (lv_base_4_0= ruleVarRef ) ) otherlv_5= ',' otherlv_6= 'else' otherlv_7= 'with' ( (lv_remote_8_0= ruleNum ) ) otherlv_9= ':' ( (lv_draw_10_0= ruleDraw ) ) ) ) )
            // InternalWorkload.g:1137:3: otherlv_0= 'let' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '=' ( ( (lv_draw_3_0= ruleDraw ) ) | ( ( (lv_base_4_0= ruleVarRef ) ) otherlv_5= ',' otherlv_6= 'else' otherlv_7= 'with' ( (lv_remote_8_0= ruleNum ) ) otherlv_9= ':' ( (lv_draw_10_0= ruleDraw ) ) ) )
            {
            otherlv_0=(Token)match(input,34,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getLetAccess().getLetKeyword_0());
            		
            // InternalWorkload.g:1141:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalWorkload.g:1142:4: (lv_name_1_0= ruleValidID )
            {
            // InternalWorkload.g:1142:4: (lv_name_1_0= ruleValidID )
            // InternalWorkload.g:1143:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getLetAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_11);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLetRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"io.shardml.Workload.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,16,FOLLOW_30); 

            			newLeafNode(otherlv_2, grammarAccess.getLetAccess().getEqualsSignKeyword_2());
            		
            // InternalWorkload.g:1164:3: ( ( (lv_draw_3_0= ruleDraw ) ) | ( ( (lv_base_4_0= ruleVarRef ) ) otherlv_5= ',' otherlv_6= 'else' otherlv_7= 'with' ( (lv_remote_8_0= ruleNum ) ) otherlv_9= ':' ( (lv_draw_10_0= ruleDraw ) ) ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_ID||(LA16_0>=11 && LA16_0<=12)||LA16_0==15||(LA16_0>=17 && LA16_0<=18)||(LA16_0>=20 && LA16_0<=23)||(LA16_0>=25 && LA16_0<=26)||(LA16_0>=28 && LA16_0<=29)||(LA16_0>=32 && LA16_0<=42)||(LA16_0>=46 && LA16_0<=48)) ) {
                alt16=1;
            }
            else if ( (LA16_0==45) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalWorkload.g:1165:4: ( (lv_draw_3_0= ruleDraw ) )
                    {
                    // InternalWorkload.g:1165:4: ( (lv_draw_3_0= ruleDraw ) )
                    // InternalWorkload.g:1166:5: (lv_draw_3_0= ruleDraw )
                    {
                    // InternalWorkload.g:1166:5: (lv_draw_3_0= ruleDraw )
                    // InternalWorkload.g:1167:6: lv_draw_3_0= ruleDraw
                    {

                    						newCompositeNode(grammarAccess.getLetAccess().getDrawDrawParserRuleCall_3_0_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_draw_3_0=ruleDraw();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLetRule());
                    						}
                    						set(
                    							current,
                    							"draw",
                    							lv_draw_3_0,
                    							"io.shardml.Workload.Draw");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalWorkload.g:1185:4: ( ( (lv_base_4_0= ruleVarRef ) ) otherlv_5= ',' otherlv_6= 'else' otherlv_7= 'with' ( (lv_remote_8_0= ruleNum ) ) otherlv_9= ':' ( (lv_draw_10_0= ruleDraw ) ) )
                    {
                    // InternalWorkload.g:1185:4: ( ( (lv_base_4_0= ruleVarRef ) ) otherlv_5= ',' otherlv_6= 'else' otherlv_7= 'with' ( (lv_remote_8_0= ruleNum ) ) otherlv_9= ':' ( (lv_draw_10_0= ruleDraw ) ) )
                    // InternalWorkload.g:1186:5: ( (lv_base_4_0= ruleVarRef ) ) otherlv_5= ',' otherlv_6= 'else' otherlv_7= 'with' ( (lv_remote_8_0= ruleNum ) ) otherlv_9= ':' ( (lv_draw_10_0= ruleDraw ) )
                    {
                    // InternalWorkload.g:1186:5: ( (lv_base_4_0= ruleVarRef ) )
                    // InternalWorkload.g:1187:6: (lv_base_4_0= ruleVarRef )
                    {
                    // InternalWorkload.g:1187:6: (lv_base_4_0= ruleVarRef )
                    // InternalWorkload.g:1188:7: lv_base_4_0= ruleVarRef
                    {

                    							newCompositeNode(grammarAccess.getLetAccess().getBaseVarRefParserRuleCall_3_1_0_0());
                    						
                    pushFollow(FOLLOW_31);
                    lv_base_4_0=ruleVarRef();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getLetRule());
                    							}
                    							set(
                    								current,
                    								"base",
                    								lv_base_4_0,
                    								"io.shardml.Workload.VarRef");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }

                    otherlv_5=(Token)match(input,24,FOLLOW_32); 

                    					newLeafNode(otherlv_5, grammarAccess.getLetAccess().getCommaKeyword_3_1_1());
                    				
                    otherlv_6=(Token)match(input,35,FOLLOW_33); 

                    					newLeafNode(otherlv_6, grammarAccess.getLetAccess().getElseKeyword_3_1_2());
                    				
                    otherlv_7=(Token)match(input,36,FOLLOW_26); 

                    					newLeafNode(otherlv_7, grammarAccess.getLetAccess().getWithKeyword_3_1_3());
                    				
                    // InternalWorkload.g:1217:5: ( (lv_remote_8_0= ruleNum ) )
                    // InternalWorkload.g:1218:6: (lv_remote_8_0= ruleNum )
                    {
                    // InternalWorkload.g:1218:6: (lv_remote_8_0= ruleNum )
                    // InternalWorkload.g:1219:7: lv_remote_8_0= ruleNum
                    {

                    							newCompositeNode(grammarAccess.getLetAccess().getRemoteNumParserRuleCall_3_1_4_0());
                    						
                    pushFollow(FOLLOW_14);
                    lv_remote_8_0=ruleNum();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getLetRule());
                    							}
                    							set(
                    								current,
                    								"remote",
                    								lv_remote_8_0,
                    								"io.shardml.Workload.Num");
                    							afterParserOrEnumRuleCall();
                    						

                    }


                    }

                    otherlv_9=(Token)match(input,19,FOLLOW_5); 

                    					newLeafNode(otherlv_9, grammarAccess.getLetAccess().getColonKeyword_3_1_5());
                    				
                    // InternalWorkload.g:1240:5: ( (lv_draw_10_0= ruleDraw ) )
                    // InternalWorkload.g:1241:6: (lv_draw_10_0= ruleDraw )
                    {
                    // InternalWorkload.g:1241:6: (lv_draw_10_0= ruleDraw )
                    // InternalWorkload.g:1242:7: lv_draw_10_0= ruleDraw
                    {

                    							newCompositeNode(grammarAccess.getLetAccess().getDrawDrawParserRuleCall_3_1_6_0());
                    						
                    pushFollow(FOLLOW_2);
                    lv_draw_10_0=ruleDraw();

                    state._fsp--;


                    							if (current==null) {
                    								current = createModelElementForParent(grammarAccess.getLetRule());
                    							}
                    							set(
                    								current,
                    								"draw",
                    								lv_draw_10_0,
                    								"io.shardml.Workload.Draw");
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
    // $ANTLR end "ruleLet"


    // $ANTLR start "entryRuleDraw"
    // InternalWorkload.g:1265:1: entryRuleDraw returns [EObject current=null] : iv_ruleDraw= ruleDraw EOF ;
    public final EObject entryRuleDraw() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDraw = null;


        try {
            // InternalWorkload.g:1265:45: (iv_ruleDraw= ruleDraw EOF )
            // InternalWorkload.g:1266:2: iv_ruleDraw= ruleDraw EOF
            {
             newCompositeNode(grammarAccess.getDrawRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDraw=ruleDraw();

            state._fsp--;

             current =iv_ruleDraw; 
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
    // $ANTLR end "entryRuleDraw"


    // $ANTLR start "ruleDraw"
    // InternalWorkload.g:1272:1: ruleDraw returns [EObject current=null] : ( ( ( ruleValidID ) ) ( (lv_distribution_1_0= ruleDistribution ) ) (otherlv_2= 'distinct' otherlv_3= 'from' ( (lv_distinctFrom_4_0= ruleVarRef ) ) )? ) ;
    public final EObject ruleDraw() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject lv_distribution_1_0 = null;

        EObject lv_distinctFrom_4_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:1278:2: ( ( ( ( ruleValidID ) ) ( (lv_distribution_1_0= ruleDistribution ) ) (otherlv_2= 'distinct' otherlv_3= 'from' ( (lv_distinctFrom_4_0= ruleVarRef ) ) )? ) )
            // InternalWorkload.g:1279:2: ( ( ( ruleValidID ) ) ( (lv_distribution_1_0= ruleDistribution ) ) (otherlv_2= 'distinct' otherlv_3= 'from' ( (lv_distinctFrom_4_0= ruleVarRef ) ) )? )
            {
            // InternalWorkload.g:1279:2: ( ( ( ruleValidID ) ) ( (lv_distribution_1_0= ruleDistribution ) ) (otherlv_2= 'distinct' otherlv_3= 'from' ( (lv_distinctFrom_4_0= ruleVarRef ) ) )? )
            // InternalWorkload.g:1280:3: ( ( ruleValidID ) ) ( (lv_distribution_1_0= ruleDistribution ) ) (otherlv_2= 'distinct' otherlv_3= 'from' ( (lv_distinctFrom_4_0= ruleVarRef ) ) )?
            {
            // InternalWorkload.g:1280:3: ( ( ruleValidID ) )
            // InternalWorkload.g:1281:4: ( ruleValidID )
            {
            // InternalWorkload.g:1281:4: ( ruleValidID )
            // InternalWorkload.g:1282:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDrawRule());
            					}
            				

            					newCompositeNode(grammarAccess.getDrawAccess().getDomainDomainCrossReference_0_0());
            				
            pushFollow(FOLLOW_16);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:1296:3: ( (lv_distribution_1_0= ruleDistribution ) )
            // InternalWorkload.g:1297:4: (lv_distribution_1_0= ruleDistribution )
            {
            // InternalWorkload.g:1297:4: (lv_distribution_1_0= ruleDistribution )
            // InternalWorkload.g:1298:5: lv_distribution_1_0= ruleDistribution
            {

            					newCompositeNode(grammarAccess.getDrawAccess().getDistributionDistributionParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_34);
            lv_distribution_1_0=ruleDistribution();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDrawRule());
            					}
            					set(
            						current,
            						"distribution",
            						lv_distribution_1_0,
            						"io.shardml.Workload.Distribution");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:1315:3: (otherlv_2= 'distinct' otherlv_3= 'from' ( (lv_distinctFrom_4_0= ruleVarRef ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==37) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalWorkload.g:1316:4: otherlv_2= 'distinct' otherlv_3= 'from' ( (lv_distinctFrom_4_0= ruleVarRef ) )
                    {
                    otherlv_2=(Token)match(input,37,FOLLOW_35); 

                    				newLeafNode(otherlv_2, grammarAccess.getDrawAccess().getDistinctKeyword_2_0());
                    			
                    otherlv_3=(Token)match(input,38,FOLLOW_30); 

                    				newLeafNode(otherlv_3, grammarAccess.getDrawAccess().getFromKeyword_2_1());
                    			
                    // InternalWorkload.g:1324:4: ( (lv_distinctFrom_4_0= ruleVarRef ) )
                    // InternalWorkload.g:1325:5: (lv_distinctFrom_4_0= ruleVarRef )
                    {
                    // InternalWorkload.g:1325:5: (lv_distinctFrom_4_0= ruleVarRef )
                    // InternalWorkload.g:1326:6: lv_distinctFrom_4_0= ruleVarRef
                    {

                    						newCompositeNode(grammarAccess.getDrawAccess().getDistinctFromVarRefParserRuleCall_2_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_distinctFrom_4_0=ruleVarRef();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDrawRule());
                    						}
                    						set(
                    							current,
                    							"distinctFrom",
                    							lv_distinctFrom_4_0,
                    							"io.shardml.Workload.VarRef");
                    						afterParserOrEnumRuleCall();
                    					

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
    // $ANTLR end "ruleDraw"


    // $ANTLR start "entryRuleRepeat"
    // InternalWorkload.g:1348:1: entryRuleRepeat returns [EObject current=null] : iv_ruleRepeat= ruleRepeat EOF ;
    public final EObject entryRuleRepeat() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRepeat = null;


        try {
            // InternalWorkload.g:1348:47: (iv_ruleRepeat= ruleRepeat EOF )
            // InternalWorkload.g:1349:2: iv_ruleRepeat= ruleRepeat EOF
            {
             newCompositeNode(grammarAccess.getRepeatRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRepeat=ruleRepeat();

            state._fsp--;

             current =iv_ruleRepeat; 
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
    // $ANTLR end "entryRuleRepeat"


    // $ANTLR start "ruleRepeat"
    // InternalWorkload.g:1355:1: ruleRepeat returns [EObject current=null] : (otherlv_0= 'repeat' ( (lv_count_1_0= RULE_INT ) ) otherlv_2= '{' ( (lv_steps_3_0= ruleStep ) )* otherlv_4= '}' ) ;
    public final EObject ruleRepeat() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_count_1_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_steps_3_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:1361:2: ( (otherlv_0= 'repeat' ( (lv_count_1_0= RULE_INT ) ) otherlv_2= '{' ( (lv_steps_3_0= ruleStep ) )* otherlv_4= '}' ) )
            // InternalWorkload.g:1362:2: (otherlv_0= 'repeat' ( (lv_count_1_0= RULE_INT ) ) otherlv_2= '{' ( (lv_steps_3_0= ruleStep ) )* otherlv_4= '}' )
            {
            // InternalWorkload.g:1362:2: (otherlv_0= 'repeat' ( (lv_count_1_0= RULE_INT ) ) otherlv_2= '{' ( (lv_steps_3_0= ruleStep ) )* otherlv_4= '}' )
            // InternalWorkload.g:1363:3: otherlv_0= 'repeat' ( (lv_count_1_0= RULE_INT ) ) otherlv_2= '{' ( (lv_steps_3_0= ruleStep ) )* otherlv_4= '}'
            {
            otherlv_0=(Token)match(input,39,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getRepeatAccess().getRepeatKeyword_0());
            		
            // InternalWorkload.g:1367:3: ( (lv_count_1_0= RULE_INT ) )
            // InternalWorkload.g:1368:4: (lv_count_1_0= RULE_INT )
            {
            // InternalWorkload.g:1368:4: (lv_count_1_0= RULE_INT )
            // InternalWorkload.g:1369:5: lv_count_1_0= RULE_INT
            {
            lv_count_1_0=(Token)match(input,RULE_INT,FOLLOW_6); 

            					newLeafNode(lv_count_1_0, grammarAccess.getRepeatAccess().getCountINTTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRepeatRule());
            					}
            					setWithLastConsumed(
            						current,
            						"count",
            						lv_count_1_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_29); 

            			newLeafNode(otherlv_2, grammarAccess.getRepeatAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalWorkload.g:1389:3: ( (lv_steps_3_0= ruleStep ) )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==34||LA18_0==39||(LA18_0>=46 && LA18_0<=48)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // InternalWorkload.g:1390:4: (lv_steps_3_0= ruleStep )
            	    {
            	    // InternalWorkload.g:1390:4: (lv_steps_3_0= ruleStep )
            	    // InternalWorkload.g:1391:5: lv_steps_3_0= ruleStep
            	    {

            	    					newCompositeNode(grammarAccess.getRepeatAccess().getStepsStepParserRuleCall_3_0());
            	    				
            	    pushFollow(FOLLOW_29);
            	    lv_steps_3_0=ruleStep();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getRepeatRule());
            	    					}
            	    					add(
            	    						current,
            	    						"steps",
            	    						lv_steps_3_0,
            	    						"io.shardml.Workload.Step");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            otherlv_4=(Token)match(input,14,FOLLOW_2); 

            			newLeafNode(otherlv_4, grammarAccess.getRepeatAccess().getRightCurlyBracketKeyword_4());
            		

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
    // $ANTLR end "ruleRepeat"


    // $ANTLR start "entryRuleStatement"
    // InternalWorkload.g:1416:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalWorkload.g:1416:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalWorkload.g:1417:2: iv_ruleStatement= ruleStatement EOF
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
    // InternalWorkload.g:1423:1: ruleStatement returns [EObject current=null] : ( ( (lv_type_0_0= ruleQueryType ) ) ( ( ruleValidID ) ) ( (lv_joins_2_0= ruleJoin ) )* (otherlv_3= 'where' ( (lv_first_4_0= ruleCondition ) ) ( ( (lv_links_5_0= ruleLink ) ) ( (lv_rest_6_0= ruleCondition ) ) )* )? ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Enumerator lv_type_0_0 = null;

        EObject lv_joins_2_0 = null;

        EObject lv_first_4_0 = null;

        Enumerator lv_links_5_0 = null;

        EObject lv_rest_6_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:1429:2: ( ( ( (lv_type_0_0= ruleQueryType ) ) ( ( ruleValidID ) ) ( (lv_joins_2_0= ruleJoin ) )* (otherlv_3= 'where' ( (lv_first_4_0= ruleCondition ) ) ( ( (lv_links_5_0= ruleLink ) ) ( (lv_rest_6_0= ruleCondition ) ) )* )? ) )
            // InternalWorkload.g:1430:2: ( ( (lv_type_0_0= ruleQueryType ) ) ( ( ruleValidID ) ) ( (lv_joins_2_0= ruleJoin ) )* (otherlv_3= 'where' ( (lv_first_4_0= ruleCondition ) ) ( ( (lv_links_5_0= ruleLink ) ) ( (lv_rest_6_0= ruleCondition ) ) )* )? )
            {
            // InternalWorkload.g:1430:2: ( ( (lv_type_0_0= ruleQueryType ) ) ( ( ruleValidID ) ) ( (lv_joins_2_0= ruleJoin ) )* (otherlv_3= 'where' ( (lv_first_4_0= ruleCondition ) ) ( ( (lv_links_5_0= ruleLink ) ) ( (lv_rest_6_0= ruleCondition ) ) )* )? )
            // InternalWorkload.g:1431:3: ( (lv_type_0_0= ruleQueryType ) ) ( ( ruleValidID ) ) ( (lv_joins_2_0= ruleJoin ) )* (otherlv_3= 'where' ( (lv_first_4_0= ruleCondition ) ) ( ( (lv_links_5_0= ruleLink ) ) ( (lv_rest_6_0= ruleCondition ) ) )* )?
            {
            // InternalWorkload.g:1431:3: ( (lv_type_0_0= ruleQueryType ) )
            // InternalWorkload.g:1432:4: (lv_type_0_0= ruleQueryType )
            {
            // InternalWorkload.g:1432:4: (lv_type_0_0= ruleQueryType )
            // InternalWorkload.g:1433:5: lv_type_0_0= ruleQueryType
            {

            					newCompositeNode(grammarAccess.getStatementAccess().getTypeQueryTypeEnumRuleCall_0_0());
            				
            pushFollow(FOLLOW_5);
            lv_type_0_0=ruleQueryType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getStatementRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_0_0,
            						"io.shardml.Workload.QueryType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:1450:3: ( ( ruleValidID ) )
            // InternalWorkload.g:1451:4: ( ruleValidID )
            {
            // InternalWorkload.g:1451:4: ( ruleValidID )
            // InternalWorkload.g:1452:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStatementRule());
            					}
            				

            					newCompositeNode(grammarAccess.getStatementAccess().getTableTableCrossReference_1_0());
            				
            pushFollow(FOLLOW_36);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:1466:3: ( (lv_joins_2_0= ruleJoin ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==41) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // InternalWorkload.g:1467:4: (lv_joins_2_0= ruleJoin )
            	    {
            	    // InternalWorkload.g:1467:4: (lv_joins_2_0= ruleJoin )
            	    // InternalWorkload.g:1468:5: lv_joins_2_0= ruleJoin
            	    {

            	    					newCompositeNode(grammarAccess.getStatementAccess().getJoinsJoinParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_36);
            	    lv_joins_2_0=ruleJoin();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getStatementRule());
            	    					}
            	    					add(
            	    						current,
            	    						"joins",
            	    						lv_joins_2_0,
            	    						"io.shardml.Workload.Join");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

            // InternalWorkload.g:1485:3: (otherlv_3= 'where' ( (lv_first_4_0= ruleCondition ) ) ( ( (lv_links_5_0= ruleLink ) ) ( (lv_rest_6_0= ruleCondition ) ) )* )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==40) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalWorkload.g:1486:4: otherlv_3= 'where' ( (lv_first_4_0= ruleCondition ) ) ( ( (lv_links_5_0= ruleLink ) ) ( (lv_rest_6_0= ruleCondition ) ) )*
                    {
                    otherlv_3=(Token)match(input,40,FOLLOW_5); 

                    				newLeafNode(otherlv_3, grammarAccess.getStatementAccess().getWhereKeyword_3_0());
                    			
                    // InternalWorkload.g:1490:4: ( (lv_first_4_0= ruleCondition ) )
                    // InternalWorkload.g:1491:5: (lv_first_4_0= ruleCondition )
                    {
                    // InternalWorkload.g:1491:5: (lv_first_4_0= ruleCondition )
                    // InternalWorkload.g:1492:6: lv_first_4_0= ruleCondition
                    {

                    						newCompositeNode(grammarAccess.getStatementAccess().getFirstConditionParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_37);
                    lv_first_4_0=ruleCondition();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getStatementRule());
                    						}
                    						set(
                    							current,
                    							"first",
                    							lv_first_4_0,
                    							"io.shardml.Workload.Condition");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalWorkload.g:1509:4: ( ( (lv_links_5_0= ruleLink ) ) ( (lv_rest_6_0= ruleCondition ) ) )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==43||LA20_0==54) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // InternalWorkload.g:1510:5: ( (lv_links_5_0= ruleLink ) ) ( (lv_rest_6_0= ruleCondition ) )
                    	    {
                    	    // InternalWorkload.g:1510:5: ( (lv_links_5_0= ruleLink ) )
                    	    // InternalWorkload.g:1511:6: (lv_links_5_0= ruleLink )
                    	    {
                    	    // InternalWorkload.g:1511:6: (lv_links_5_0= ruleLink )
                    	    // InternalWorkload.g:1512:7: lv_links_5_0= ruleLink
                    	    {

                    	    							newCompositeNode(grammarAccess.getStatementAccess().getLinksLinkEnumRuleCall_3_2_0_0());
                    	    						
                    	    pushFollow(FOLLOW_5);
                    	    lv_links_5_0=ruleLink();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStatementRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"links",
                    	    								lv_links_5_0,
                    	    								"io.shardml.Workload.Link");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }

                    	    // InternalWorkload.g:1529:5: ( (lv_rest_6_0= ruleCondition ) )
                    	    // InternalWorkload.g:1530:6: (lv_rest_6_0= ruleCondition )
                    	    {
                    	    // InternalWorkload.g:1530:6: (lv_rest_6_0= ruleCondition )
                    	    // InternalWorkload.g:1531:7: lv_rest_6_0= ruleCondition
                    	    {

                    	    							newCompositeNode(grammarAccess.getStatementAccess().getRestConditionParserRuleCall_3_2_1_0());
                    	    						
                    	    pushFollow(FOLLOW_37);
                    	    lv_rest_6_0=ruleCondition();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getStatementRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"rest",
                    	    								lv_rest_6_0,
                    	    								"io.shardml.Workload.Condition");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);


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
    // $ANTLR end "ruleStatement"


    // $ANTLR start "entryRuleJoin"
    // InternalWorkload.g:1554:1: entryRuleJoin returns [EObject current=null] : iv_ruleJoin= ruleJoin EOF ;
    public final EObject entryRuleJoin() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJoin = null;


        try {
            // InternalWorkload.g:1554:45: (iv_ruleJoin= ruleJoin EOF )
            // InternalWorkload.g:1555:2: iv_ruleJoin= ruleJoin EOF
            {
             newCompositeNode(grammarAccess.getJoinRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJoin=ruleJoin();

            state._fsp--;

             current =iv_ruleJoin; 
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
    // $ANTLR end "entryRuleJoin"


    // $ANTLR start "ruleJoin"
    // InternalWorkload.g:1561:1: ruleJoin returns [EObject current=null] : (otherlv_0= 'join' ( ( ruleValidID ) ) otherlv_2= 'on' ( (lv_on_3_0= ruleJoinOn ) ) (otherlv_4= 'AND' ( (lv_on_5_0= ruleJoinOn ) ) )* ) ;
    public final EObject ruleJoin() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_on_3_0 = null;

        EObject lv_on_5_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:1567:2: ( (otherlv_0= 'join' ( ( ruleValidID ) ) otherlv_2= 'on' ( (lv_on_3_0= ruleJoinOn ) ) (otherlv_4= 'AND' ( (lv_on_5_0= ruleJoinOn ) ) )* ) )
            // InternalWorkload.g:1568:2: (otherlv_0= 'join' ( ( ruleValidID ) ) otherlv_2= 'on' ( (lv_on_3_0= ruleJoinOn ) ) (otherlv_4= 'AND' ( (lv_on_5_0= ruleJoinOn ) ) )* )
            {
            // InternalWorkload.g:1568:2: (otherlv_0= 'join' ( ( ruleValidID ) ) otherlv_2= 'on' ( (lv_on_3_0= ruleJoinOn ) ) (otherlv_4= 'AND' ( (lv_on_5_0= ruleJoinOn ) ) )* )
            // InternalWorkload.g:1569:3: otherlv_0= 'join' ( ( ruleValidID ) ) otherlv_2= 'on' ( (lv_on_3_0= ruleJoinOn ) ) (otherlv_4= 'AND' ( (lv_on_5_0= ruleJoinOn ) ) )*
            {
            otherlv_0=(Token)match(input,41,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getJoinAccess().getJoinKeyword_0());
            		
            // InternalWorkload.g:1573:3: ( ( ruleValidID ) )
            // InternalWorkload.g:1574:4: ( ruleValidID )
            {
            // InternalWorkload.g:1574:4: ( ruleValidID )
            // InternalWorkload.g:1575:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getJoinRule());
            					}
            				

            					newCompositeNode(grammarAccess.getJoinAccess().getTableTableCrossReference_1_0());
            				
            pushFollow(FOLLOW_38);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,42,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getJoinAccess().getOnKeyword_2());
            		
            // InternalWorkload.g:1593:3: ( (lv_on_3_0= ruleJoinOn ) )
            // InternalWorkload.g:1594:4: (lv_on_3_0= ruleJoinOn )
            {
            // InternalWorkload.g:1594:4: (lv_on_3_0= ruleJoinOn )
            // InternalWorkload.g:1595:5: lv_on_3_0= ruleJoinOn
            {

            					newCompositeNode(grammarAccess.getJoinAccess().getOnJoinOnParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_39);
            lv_on_3_0=ruleJoinOn();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getJoinRule());
            					}
            					add(
            						current,
            						"on",
            						lv_on_3_0,
            						"io.shardml.Workload.JoinOn");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:1612:3: (otherlv_4= 'AND' ( (lv_on_5_0= ruleJoinOn ) ) )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==43) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // InternalWorkload.g:1613:4: otherlv_4= 'AND' ( (lv_on_5_0= ruleJoinOn ) )
            	    {
            	    otherlv_4=(Token)match(input,43,FOLLOW_5); 

            	    				newLeafNode(otherlv_4, grammarAccess.getJoinAccess().getANDKeyword_4_0());
            	    			
            	    // InternalWorkload.g:1617:4: ( (lv_on_5_0= ruleJoinOn ) )
            	    // InternalWorkload.g:1618:5: (lv_on_5_0= ruleJoinOn )
            	    {
            	    // InternalWorkload.g:1618:5: (lv_on_5_0= ruleJoinOn )
            	    // InternalWorkload.g:1619:6: lv_on_5_0= ruleJoinOn
            	    {

            	    						newCompositeNode(grammarAccess.getJoinAccess().getOnJoinOnParserRuleCall_4_1_0());
            	    					
            	    pushFollow(FOLLOW_39);
            	    lv_on_5_0=ruleJoinOn();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getJoinRule());
            	    						}
            	    						add(
            	    							current,
            	    							"on",
            	    							lv_on_5_0,
            	    							"io.shardml.Workload.JoinOn");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop22;
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
    // $ANTLR end "ruleJoin"


    // $ANTLR start "entryRuleJoinOn"
    // InternalWorkload.g:1641:1: entryRuleJoinOn returns [EObject current=null] : iv_ruleJoinOn= ruleJoinOn EOF ;
    public final EObject entryRuleJoinOn() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleJoinOn = null;


        try {
            // InternalWorkload.g:1641:47: (iv_ruleJoinOn= ruleJoinOn EOF )
            // InternalWorkload.g:1642:2: iv_ruleJoinOn= ruleJoinOn EOF
            {
             newCompositeNode(grammarAccess.getJoinOnRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleJoinOn=ruleJoinOn();

            state._fsp--;

             current =iv_ruleJoinOn; 
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
    // $ANTLR end "entryRuleJoinOn"


    // $ANTLR start "ruleJoinOn"
    // InternalWorkload.g:1648:1: ruleJoinOn returns [EObject current=null] : ( ( (lv_left_0_0= ruleColumnRef ) ) otherlv_1= '=' ( (lv_right_2_0= ruleColumnRef ) ) ) ;
    public final EObject ruleJoinOn() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_left_0_0 = null;

        EObject lv_right_2_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:1654:2: ( ( ( (lv_left_0_0= ruleColumnRef ) ) otherlv_1= '=' ( (lv_right_2_0= ruleColumnRef ) ) ) )
            // InternalWorkload.g:1655:2: ( ( (lv_left_0_0= ruleColumnRef ) ) otherlv_1= '=' ( (lv_right_2_0= ruleColumnRef ) ) )
            {
            // InternalWorkload.g:1655:2: ( ( (lv_left_0_0= ruleColumnRef ) ) otherlv_1= '=' ( (lv_right_2_0= ruleColumnRef ) ) )
            // InternalWorkload.g:1656:3: ( (lv_left_0_0= ruleColumnRef ) ) otherlv_1= '=' ( (lv_right_2_0= ruleColumnRef ) )
            {
            // InternalWorkload.g:1656:3: ( (lv_left_0_0= ruleColumnRef ) )
            // InternalWorkload.g:1657:4: (lv_left_0_0= ruleColumnRef )
            {
            // InternalWorkload.g:1657:4: (lv_left_0_0= ruleColumnRef )
            // InternalWorkload.g:1658:5: lv_left_0_0= ruleColumnRef
            {

            					newCompositeNode(grammarAccess.getJoinOnAccess().getLeftColumnRefParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_11);
            lv_left_0_0=ruleColumnRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getJoinOnRule());
            					}
            					set(
            						current,
            						"left",
            						lv_left_0_0,
            						"io.shardml.Workload.ColumnRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,16,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getJoinOnAccess().getEqualsSignKeyword_1());
            		
            // InternalWorkload.g:1679:3: ( (lv_right_2_0= ruleColumnRef ) )
            // InternalWorkload.g:1680:4: (lv_right_2_0= ruleColumnRef )
            {
            // InternalWorkload.g:1680:4: (lv_right_2_0= ruleColumnRef )
            // InternalWorkload.g:1681:5: lv_right_2_0= ruleColumnRef
            {

            					newCompositeNode(grammarAccess.getJoinOnAccess().getRightColumnRefParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_2);
            lv_right_2_0=ruleColumnRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getJoinOnRule());
            					}
            					set(
            						current,
            						"right",
            						lv_right_2_0,
            						"io.shardml.Workload.ColumnRef");
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
    // $ANTLR end "ruleJoinOn"


    // $ANTLR start "entryRuleCondition"
    // InternalWorkload.g:1702:1: entryRuleCondition returns [EObject current=null] : iv_ruleCondition= ruleCondition EOF ;
    public final EObject entryRuleCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCondition = null;


        try {
            // InternalWorkload.g:1702:50: (iv_ruleCondition= ruleCondition EOF )
            // InternalWorkload.g:1703:2: iv_ruleCondition= ruleCondition EOF
            {
             newCompositeNode(grammarAccess.getConditionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCondition=ruleCondition();

            state._fsp--;

             current =iv_ruleCondition; 
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
    // $ANTLR end "entryRuleCondition"


    // $ANTLR start "ruleCondition"
    // InternalWorkload.g:1709:1: ruleCondition returns [EObject current=null] : ( ( (lv_column_0_0= ruleColumnRef ) ) ( (lv_op_1_0= ruleOperator ) ) ( ( (lv_unbound_2_0= '?' ) ) | ( (lv_value_3_0= ruleVarRef ) ) ) ) ;
    public final EObject ruleCondition() throws RecognitionException {
        EObject current = null;

        Token lv_unbound_2_0=null;
        EObject lv_column_0_0 = null;

        Enumerator lv_op_1_0 = null;

        EObject lv_value_3_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:1715:2: ( ( ( (lv_column_0_0= ruleColumnRef ) ) ( (lv_op_1_0= ruleOperator ) ) ( ( (lv_unbound_2_0= '?' ) ) | ( (lv_value_3_0= ruleVarRef ) ) ) ) )
            // InternalWorkload.g:1716:2: ( ( (lv_column_0_0= ruleColumnRef ) ) ( (lv_op_1_0= ruleOperator ) ) ( ( (lv_unbound_2_0= '?' ) ) | ( (lv_value_3_0= ruleVarRef ) ) ) )
            {
            // InternalWorkload.g:1716:2: ( ( (lv_column_0_0= ruleColumnRef ) ) ( (lv_op_1_0= ruleOperator ) ) ( ( (lv_unbound_2_0= '?' ) ) | ( (lv_value_3_0= ruleVarRef ) ) ) )
            // InternalWorkload.g:1717:3: ( (lv_column_0_0= ruleColumnRef ) ) ( (lv_op_1_0= ruleOperator ) ) ( ( (lv_unbound_2_0= '?' ) ) | ( (lv_value_3_0= ruleVarRef ) ) )
            {
            // InternalWorkload.g:1717:3: ( (lv_column_0_0= ruleColumnRef ) )
            // InternalWorkload.g:1718:4: (lv_column_0_0= ruleColumnRef )
            {
            // InternalWorkload.g:1718:4: (lv_column_0_0= ruleColumnRef )
            // InternalWorkload.g:1719:5: lv_column_0_0= ruleColumnRef
            {

            					newCompositeNode(grammarAccess.getConditionAccess().getColumnColumnRefParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_40);
            lv_column_0_0=ruleColumnRef();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionRule());
            					}
            					set(
            						current,
            						"column",
            						lv_column_0_0,
            						"io.shardml.Workload.ColumnRef");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:1736:3: ( (lv_op_1_0= ruleOperator ) )
            // InternalWorkload.g:1737:4: (lv_op_1_0= ruleOperator )
            {
            // InternalWorkload.g:1737:4: (lv_op_1_0= ruleOperator )
            // InternalWorkload.g:1738:5: lv_op_1_0= ruleOperator
            {

            					newCompositeNode(grammarAccess.getConditionAccess().getOpOperatorEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_41);
            lv_op_1_0=ruleOperator();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getConditionRule());
            					}
            					set(
            						current,
            						"op",
            						lv_op_1_0,
            						"io.shardml.Workload.Operator");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:1755:3: ( ( (lv_unbound_2_0= '?' ) ) | ( (lv_value_3_0= ruleVarRef ) ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==44) ) {
                alt23=1;
            }
            else if ( (LA23_0==45) ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // InternalWorkload.g:1756:4: ( (lv_unbound_2_0= '?' ) )
                    {
                    // InternalWorkload.g:1756:4: ( (lv_unbound_2_0= '?' ) )
                    // InternalWorkload.g:1757:5: (lv_unbound_2_0= '?' )
                    {
                    // InternalWorkload.g:1757:5: (lv_unbound_2_0= '?' )
                    // InternalWorkload.g:1758:6: lv_unbound_2_0= '?'
                    {
                    lv_unbound_2_0=(Token)match(input,44,FOLLOW_2); 

                    						newLeafNode(lv_unbound_2_0, grammarAccess.getConditionAccess().getUnboundQuestionMarkKeyword_2_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getConditionRule());
                    						}
                    						setWithLastConsumed(current, "unbound", lv_unbound_2_0 != null, "?");
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalWorkload.g:1771:4: ( (lv_value_3_0= ruleVarRef ) )
                    {
                    // InternalWorkload.g:1771:4: ( (lv_value_3_0= ruleVarRef ) )
                    // InternalWorkload.g:1772:5: (lv_value_3_0= ruleVarRef )
                    {
                    // InternalWorkload.g:1772:5: (lv_value_3_0= ruleVarRef )
                    // InternalWorkload.g:1773:6: lv_value_3_0= ruleVarRef
                    {

                    						newCompositeNode(grammarAccess.getConditionAccess().getValueVarRefParserRuleCall_2_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_value_3_0=ruleVarRef();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getConditionRule());
                    						}
                    						set(
                    							current,
                    							"value",
                    							lv_value_3_0,
                    							"io.shardml.Workload.VarRef");
                    						afterParserOrEnumRuleCall();
                    					

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
    // $ANTLR end "ruleCondition"


    // $ANTLR start "entryRuleColumnRef"
    // InternalWorkload.g:1795:1: entryRuleColumnRef returns [EObject current=null] : iv_ruleColumnRef= ruleColumnRef EOF ;
    public final EObject entryRuleColumnRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleColumnRef = null;


        try {
            // InternalWorkload.g:1795:50: (iv_ruleColumnRef= ruleColumnRef EOF )
            // InternalWorkload.g:1796:2: iv_ruleColumnRef= ruleColumnRef EOF
            {
             newCompositeNode(grammarAccess.getColumnRefRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleColumnRef=ruleColumnRef();

            state._fsp--;

             current =iv_ruleColumnRef; 
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
    // $ANTLR end "entryRuleColumnRef"


    // $ANTLR start "ruleColumnRef"
    // InternalWorkload.g:1802:1: ruleColumnRef returns [EObject current=null] : ( ( ( ( ruleValidID ) ) otherlv_1= '.' )? ( ( ruleValidID ) ) ) ;
    public final EObject ruleColumnRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalWorkload.g:1808:2: ( ( ( ( ( ruleValidID ) ) otherlv_1= '.' )? ( ( ruleValidID ) ) ) )
            // InternalWorkload.g:1809:2: ( ( ( ( ruleValidID ) ) otherlv_1= '.' )? ( ( ruleValidID ) ) )
            {
            // InternalWorkload.g:1809:2: ( ( ( ( ruleValidID ) ) otherlv_1= '.' )? ( ( ruleValidID ) ) )
            // InternalWorkload.g:1810:3: ( ( ( ruleValidID ) ) otherlv_1= '.' )? ( ( ruleValidID ) )
            {
            // InternalWorkload.g:1810:3: ( ( ( ruleValidID ) ) otherlv_1= '.' )?
            int alt24=2;
            alt24 = dfa24.predict(input);
            switch (alt24) {
                case 1 :
                    // InternalWorkload.g:1811:4: ( ( ruleValidID ) ) otherlv_1= '.'
                    {
                    // InternalWorkload.g:1811:4: ( ( ruleValidID ) )
                    // InternalWorkload.g:1812:5: ( ruleValidID )
                    {
                    // InternalWorkload.g:1812:5: ( ruleValidID )
                    // InternalWorkload.g:1813:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getColumnRefRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getColumnRefAccess().getTableTableCrossReference_0_0_0());
                    					
                    pushFollow(FOLLOW_42);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,27,FOLLOW_5); 

                    				newLeafNode(otherlv_1, grammarAccess.getColumnRefAccess().getFullStopKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalWorkload.g:1832:3: ( ( ruleValidID ) )
            // InternalWorkload.g:1833:4: ( ruleValidID )
            {
            // InternalWorkload.g:1833:4: ( ruleValidID )
            // InternalWorkload.g:1834:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getColumnRefRule());
            					}
            				

            					newCompositeNode(grammarAccess.getColumnRefAccess().getColumnColumnCrossReference_1_0());
            				
            pushFollow(FOLLOW_2);
            ruleValidID();

            state._fsp--;


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
    // $ANTLR end "ruleColumnRef"


    // $ANTLR start "entryRuleVarRef"
    // InternalWorkload.g:1852:1: entryRuleVarRef returns [EObject current=null] : iv_ruleVarRef= ruleVarRef EOF ;
    public final EObject entryRuleVarRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarRef = null;


        try {
            // InternalWorkload.g:1852:47: (iv_ruleVarRef= ruleVarRef EOF )
            // InternalWorkload.g:1853:2: iv_ruleVarRef= ruleVarRef EOF
            {
             newCompositeNode(grammarAccess.getVarRefRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVarRef=ruleVarRef();

            state._fsp--;

             current =iv_ruleVarRef; 
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
    // $ANTLR end "entryRuleVarRef"


    // $ANTLR start "ruleVarRef"
    // InternalWorkload.g:1859:1: ruleVarRef returns [EObject current=null] : (otherlv_0= '$' ( ( ruleValidID ) ) (otherlv_2= '.' ( ( ruleValidID ) ) )* ) ;
    public final EObject ruleVarRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalWorkload.g:1865:2: ( (otherlv_0= '$' ( ( ruleValidID ) ) (otherlv_2= '.' ( ( ruleValidID ) ) )* ) )
            // InternalWorkload.g:1866:2: (otherlv_0= '$' ( ( ruleValidID ) ) (otherlv_2= '.' ( ( ruleValidID ) ) )* )
            {
            // InternalWorkload.g:1866:2: (otherlv_0= '$' ( ( ruleValidID ) ) (otherlv_2= '.' ( ( ruleValidID ) ) )* )
            // InternalWorkload.g:1867:3: otherlv_0= '$' ( ( ruleValidID ) ) (otherlv_2= '.' ( ( ruleValidID ) ) )*
            {
            otherlv_0=(Token)match(input,45,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getVarRefAccess().getDollarSignKeyword_0());
            		
            // InternalWorkload.g:1871:3: ( ( ruleValidID ) )
            // InternalWorkload.g:1872:4: ( ruleValidID )
            {
            // InternalWorkload.g:1872:4: ( ruleValidID )
            // InternalWorkload.g:1873:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVarRefRule());
            					}
            				

            					newCompositeNode(grammarAccess.getVarRefAccess().getVariableLetCrossReference_1_0());
            				
            pushFollow(FOLLOW_24);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalWorkload.g:1887:3: (otherlv_2= '.' ( ( ruleValidID ) ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==27) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // InternalWorkload.g:1888:4: otherlv_2= '.' ( ( ruleValidID ) )
            	    {
            	    otherlv_2=(Token)match(input,27,FOLLOW_5); 

            	    				newLeafNode(otherlv_2, grammarAccess.getVarRefAccess().getFullStopKeyword_2_0());
            	    			
            	    // InternalWorkload.g:1892:4: ( ( ruleValidID ) )
            	    // InternalWorkload.g:1893:5: ( ruleValidID )
            	    {
            	    // InternalWorkload.g:1893:5: ( ruleValidID )
            	    // InternalWorkload.g:1894:6: ruleValidID
            	    {

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getVarRefRule());
            	    						}
            	    					

            	    						newCompositeNode(grammarAccess.getVarRefAccess().getParentsDomainCrossReference_2_1_0());
            	    					
            	    pushFollow(FOLLOW_24);
            	    ruleValidID();

            	    state._fsp--;


            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop25;
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
    // $ANTLR end "ruleVarRef"


    // $ANTLR start "entryRuleNum"
    // InternalWorkload.g:1913:1: entryRuleNum returns [EObject current=null] : iv_ruleNum= ruleNum EOF ;
    public final EObject entryRuleNum() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNum = null;


        try {
            // InternalWorkload.g:1913:44: (iv_ruleNum= ruleNum EOF )
            // InternalWorkload.g:1914:2: iv_ruleNum= ruleNum EOF
            {
             newCompositeNode(grammarAccess.getNumRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNum=ruleNum();

            state._fsp--;

             current =iv_ruleNum; 
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
    // $ANTLR end "entryRuleNum"


    // $ANTLR start "ruleNum"
    // InternalWorkload.g:1920:1: ruleNum returns [EObject current=null] : ( ( (lv_value_0_0= ruleDecimal ) ) | (otherlv_1= '$' ( ( ruleValidID ) ) ) ) ;
    public final EObject ruleNum() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        AntlrDatatypeRuleToken lv_value_0_0 = null;



        	enterRule();

        try {
            // InternalWorkload.g:1926:2: ( ( ( (lv_value_0_0= ruleDecimal ) ) | (otherlv_1= '$' ( ( ruleValidID ) ) ) ) )
            // InternalWorkload.g:1927:2: ( ( (lv_value_0_0= ruleDecimal ) ) | (otherlv_1= '$' ( ( ruleValidID ) ) ) )
            {
            // InternalWorkload.g:1927:2: ( ( (lv_value_0_0= ruleDecimal ) ) | (otherlv_1= '$' ( ( ruleValidID ) ) ) )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==RULE_INT) ) {
                alt26=1;
            }
            else if ( (LA26_0==45) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // InternalWorkload.g:1928:3: ( (lv_value_0_0= ruleDecimal ) )
                    {
                    // InternalWorkload.g:1928:3: ( (lv_value_0_0= ruleDecimal ) )
                    // InternalWorkload.g:1929:4: (lv_value_0_0= ruleDecimal )
                    {
                    // InternalWorkload.g:1929:4: (lv_value_0_0= ruleDecimal )
                    // InternalWorkload.g:1930:5: lv_value_0_0= ruleDecimal
                    {

                    					newCompositeNode(grammarAccess.getNumAccess().getValueDecimalParserRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_2);
                    lv_value_0_0=ruleDecimal();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getNumRule());
                    					}
                    					set(
                    						current,
                    						"value",
                    						lv_value_0_0,
                    						"io.shardml.Workload.Decimal");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalWorkload.g:1948:3: (otherlv_1= '$' ( ( ruleValidID ) ) )
                    {
                    // InternalWorkload.g:1948:3: (otherlv_1= '$' ( ( ruleValidID ) ) )
                    // InternalWorkload.g:1949:4: otherlv_1= '$' ( ( ruleValidID ) )
                    {
                    otherlv_1=(Token)match(input,45,FOLLOW_5); 

                    				newLeafNode(otherlv_1, grammarAccess.getNumAccess().getDollarSignKeyword_1_0());
                    			
                    // InternalWorkload.g:1953:4: ( ( ruleValidID ) )
                    // InternalWorkload.g:1954:5: ( ruleValidID )
                    {
                    // InternalWorkload.g:1954:5: ( ruleValidID )
                    // InternalWorkload.g:1955:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getNumRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getNumAccess().getParamParamCrossReference_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


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
    // $ANTLR end "ruleNum"


    // $ANTLR start "entryRuleDecimal"
    // InternalWorkload.g:1974:1: entryRuleDecimal returns [String current=null] : iv_ruleDecimal= ruleDecimal EOF ;
    public final String entryRuleDecimal() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDecimal = null;


        try {
            // InternalWorkload.g:1974:47: (iv_ruleDecimal= ruleDecimal EOF )
            // InternalWorkload.g:1975:2: iv_ruleDecimal= ruleDecimal EOF
            {
             newCompositeNode(grammarAccess.getDecimalRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDecimal=ruleDecimal();

            state._fsp--;

             current =iv_ruleDecimal.getText(); 
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
    // $ANTLR end "entryRuleDecimal"


    // $ANTLR start "ruleDecimal"
    // InternalWorkload.g:1981:1: ruleDecimal returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleDecimal() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;
        Token kw=null;
        Token this_INT_2=null;


        	enterRule();

        try {
            // InternalWorkload.g:1987:2: ( (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT )? ) )
            // InternalWorkload.g:1988:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT )? )
            {
            // InternalWorkload.g:1988:2: (this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT )? )
            // InternalWorkload.g:1989:3: this_INT_0= RULE_INT (kw= '.' this_INT_2= RULE_INT )?
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_24); 

            			current.merge(this_INT_0);
            		

            			newLeafNode(this_INT_0, grammarAccess.getDecimalAccess().getINTTerminalRuleCall_0());
            		
            // InternalWorkload.g:1996:3: (kw= '.' this_INT_2= RULE_INT )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==27) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalWorkload.g:1997:4: kw= '.' this_INT_2= RULE_INT
                    {
                    kw=(Token)match(input,27,FOLLOW_12); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getDecimalAccess().getFullStopKeyword_1_0());
                    			
                    this_INT_2=(Token)match(input,RULE_INT,FOLLOW_2); 

                    				current.merge(this_INT_2);
                    			

                    				newLeafNode(this_INT_2, grammarAccess.getDecimalAccess().getINTTerminalRuleCall_1_1());
                    			

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
    // $ANTLR end "ruleDecimal"


    // $ANTLR start "entryRuleCount"
    // InternalWorkload.g:2014:1: entryRuleCount returns [String current=null] : iv_ruleCount= ruleCount EOF ;
    public final String entryRuleCount() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleCount = null;


        try {
            // InternalWorkload.g:2014:45: (iv_ruleCount= ruleCount EOF )
            // InternalWorkload.g:2015:2: iv_ruleCount= ruleCount EOF
            {
             newCompositeNode(grammarAccess.getCountRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCount=ruleCount();

            state._fsp--;

             current =iv_ruleCount.getText(); 
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
    // $ANTLR end "entryRuleCount"


    // $ANTLR start "ruleCount"
    // InternalWorkload.g:2021:1: ruleCount returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_INT_0= RULE_INT ;
    public final AntlrDatatypeRuleToken ruleCount() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_INT_0=null;


        	enterRule();

        try {
            // InternalWorkload.g:2027:2: (this_INT_0= RULE_INT )
            // InternalWorkload.g:2028:2: this_INT_0= RULE_INT
            {
            this_INT_0=(Token)match(input,RULE_INT,FOLLOW_2); 

            		current.merge(this_INT_0);
            	

            		newLeafNode(this_INT_0, grammarAccess.getCountAccess().getINTTerminalRuleCall());
            	

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
    // $ANTLR end "ruleCount"


    // $ANTLR start "entryRuleValidID"
    // InternalWorkload.g:2038:1: entryRuleValidID returns [String current=null] : iv_ruleValidID= ruleValidID EOF ;
    public final String entryRuleValidID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleValidID = null;


        try {
            // InternalWorkload.g:2038:47: (iv_ruleValidID= ruleValidID EOF )
            // InternalWorkload.g:2039:2: iv_ruleValidID= ruleValidID EOF
            {
             newCompositeNode(grammarAccess.getValidIDRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleValidID=ruleValidID();

            state._fsp--;

             current =iv_ruleValidID.getText(); 
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
    // $ANTLR end "entryRuleValidID"


    // $ANTLR start "ruleValidID"
    // InternalWorkload.g:2045:1: ruleValidID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= 'import' | kw= 'workload' | kw= 'param' | kw= 'domain' | kw= 'size' | kw= 'within' | kw= 'table' | kw= 'rows' | kw= 'per' | kw= 'over' | kw= 'unique' | kw= 'uniform' | kw= 'zipf' | kw= 'transaction' | kw= 'weight' | kw= 'let' | kw= 'else' | kw= 'with' | kw= 'distinct' | kw= 'from' | kw= 'repeat' | kw= 'join' | kw= 'on' | kw= 'where' | kw= 'read' | kw= 'write' | kw= 'delete' ) ;
    public final AntlrDatatypeRuleToken ruleValidID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalWorkload.g:2051:2: ( (this_ID_0= RULE_ID | kw= 'import' | kw= 'workload' | kw= 'param' | kw= 'domain' | kw= 'size' | kw= 'within' | kw= 'table' | kw= 'rows' | kw= 'per' | kw= 'over' | kw= 'unique' | kw= 'uniform' | kw= 'zipf' | kw= 'transaction' | kw= 'weight' | kw= 'let' | kw= 'else' | kw= 'with' | kw= 'distinct' | kw= 'from' | kw= 'repeat' | kw= 'join' | kw= 'on' | kw= 'where' | kw= 'read' | kw= 'write' | kw= 'delete' ) )
            // InternalWorkload.g:2052:2: (this_ID_0= RULE_ID | kw= 'import' | kw= 'workload' | kw= 'param' | kw= 'domain' | kw= 'size' | kw= 'within' | kw= 'table' | kw= 'rows' | kw= 'per' | kw= 'over' | kw= 'unique' | kw= 'uniform' | kw= 'zipf' | kw= 'transaction' | kw= 'weight' | kw= 'let' | kw= 'else' | kw= 'with' | kw= 'distinct' | kw= 'from' | kw= 'repeat' | kw= 'join' | kw= 'on' | kw= 'where' | kw= 'read' | kw= 'write' | kw= 'delete' )
            {
            // InternalWorkload.g:2052:2: (this_ID_0= RULE_ID | kw= 'import' | kw= 'workload' | kw= 'param' | kw= 'domain' | kw= 'size' | kw= 'within' | kw= 'table' | kw= 'rows' | kw= 'per' | kw= 'over' | kw= 'unique' | kw= 'uniform' | kw= 'zipf' | kw= 'transaction' | kw= 'weight' | kw= 'let' | kw= 'else' | kw= 'with' | kw= 'distinct' | kw= 'from' | kw= 'repeat' | kw= 'join' | kw= 'on' | kw= 'where' | kw= 'read' | kw= 'write' | kw= 'delete' )
            int alt28=28;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt28=1;
                }
                break;
            case 11:
                {
                alt28=2;
                }
                break;
            case 12:
                {
                alt28=3;
                }
                break;
            case 15:
                {
                alt28=4;
                }
                break;
            case 17:
                {
                alt28=5;
                }
                break;
            case 18:
                {
                alt28=6;
                }
                break;
            case 20:
                {
                alt28=7;
                }
                break;
            case 21:
                {
                alt28=8;
                }
                break;
            case 22:
                {
                alt28=9;
                }
                break;
            case 23:
                {
                alt28=10;
                }
                break;
            case 25:
                {
                alt28=11;
                }
                break;
            case 26:
                {
                alt28=12;
                }
                break;
            case 28:
                {
                alt28=13;
                }
                break;
            case 29:
                {
                alt28=14;
                }
                break;
            case 32:
                {
                alt28=15;
                }
                break;
            case 33:
                {
                alt28=16;
                }
                break;
            case 34:
                {
                alt28=17;
                }
                break;
            case 35:
                {
                alt28=18;
                }
                break;
            case 36:
                {
                alt28=19;
                }
                break;
            case 37:
                {
                alt28=20;
                }
                break;
            case 38:
                {
                alt28=21;
                }
                break;
            case 39:
                {
                alt28=22;
                }
                break;
            case 41:
                {
                alt28=23;
                }
                break;
            case 42:
                {
                alt28=24;
                }
                break;
            case 40:
                {
                alt28=25;
                }
                break;
            case 46:
                {
                alt28=26;
                }
                break;
            case 47:
                {
                alt28=27;
                }
                break;
            case 48:
                {
                alt28=28;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // InternalWorkload.g:2053:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getValidIDAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalWorkload.g:2061:3: kw= 'import'
                    {
                    kw=(Token)match(input,11,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getImportKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalWorkload.g:2067:3: kw= 'workload'
                    {
                    kw=(Token)match(input,12,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWorkloadKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalWorkload.g:2073:3: kw= 'param'
                    {
                    kw=(Token)match(input,15,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getParamKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalWorkload.g:2079:3: kw= 'domain'
                    {
                    kw=(Token)match(input,17,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDomainKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalWorkload.g:2085:3: kw= 'size'
                    {
                    kw=(Token)match(input,18,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSizeKeyword_5());
                    		

                    }
                    break;
                case 7 :
                    // InternalWorkload.g:2091:3: kw= 'within'
                    {
                    kw=(Token)match(input,20,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWithinKeyword_6());
                    		

                    }
                    break;
                case 8 :
                    // InternalWorkload.g:2097:3: kw= 'table'
                    {
                    kw=(Token)match(input,21,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getTableKeyword_7());
                    		

                    }
                    break;
                case 9 :
                    // InternalWorkload.g:2103:3: kw= 'rows'
                    {
                    kw=(Token)match(input,22,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRowsKeyword_8());
                    		

                    }
                    break;
                case 10 :
                    // InternalWorkload.g:2109:3: kw= 'per'
                    {
                    kw=(Token)match(input,23,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPerKeyword_9());
                    		

                    }
                    break;
                case 11 :
                    // InternalWorkload.g:2115:3: kw= 'over'
                    {
                    kw=(Token)match(input,25,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getOverKeyword_10());
                    		

                    }
                    break;
                case 12 :
                    // InternalWorkload.g:2121:3: kw= 'unique'
                    {
                    kw=(Token)match(input,26,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getUniqueKeyword_11());
                    		

                    }
                    break;
                case 13 :
                    // InternalWorkload.g:2127:3: kw= 'uniform'
                    {
                    kw=(Token)match(input,28,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getUniformKeyword_12());
                    		

                    }
                    break;
                case 14 :
                    // InternalWorkload.g:2133:3: kw= 'zipf'
                    {
                    kw=(Token)match(input,29,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getZipfKeyword_13());
                    		

                    }
                    break;
                case 15 :
                    // InternalWorkload.g:2139:3: kw= 'transaction'
                    {
                    kw=(Token)match(input,32,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getTransactionKeyword_14());
                    		

                    }
                    break;
                case 16 :
                    // InternalWorkload.g:2145:3: kw= 'weight'
                    {
                    kw=(Token)match(input,33,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWeightKeyword_15());
                    		

                    }
                    break;
                case 17 :
                    // InternalWorkload.g:2151:3: kw= 'let'
                    {
                    kw=(Token)match(input,34,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getLetKeyword_16());
                    		

                    }
                    break;
                case 18 :
                    // InternalWorkload.g:2157:3: kw= 'else'
                    {
                    kw=(Token)match(input,35,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getElseKeyword_17());
                    		

                    }
                    break;
                case 19 :
                    // InternalWorkload.g:2163:3: kw= 'with'
                    {
                    kw=(Token)match(input,36,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWithKeyword_18());
                    		

                    }
                    break;
                case 20 :
                    // InternalWorkload.g:2169:3: kw= 'distinct'
                    {
                    kw=(Token)match(input,37,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDistinctKeyword_19());
                    		

                    }
                    break;
                case 21 :
                    // InternalWorkload.g:2175:3: kw= 'from'
                    {
                    kw=(Token)match(input,38,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getFromKeyword_20());
                    		

                    }
                    break;
                case 22 :
                    // InternalWorkload.g:2181:3: kw= 'repeat'
                    {
                    kw=(Token)match(input,39,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getRepeatKeyword_21());
                    		

                    }
                    break;
                case 23 :
                    // InternalWorkload.g:2187:3: kw= 'join'
                    {
                    kw=(Token)match(input,41,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getJoinKeyword_22());
                    		

                    }
                    break;
                case 24 :
                    // InternalWorkload.g:2193:3: kw= 'on'
                    {
                    kw=(Token)match(input,42,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getOnKeyword_23());
                    		

                    }
                    break;
                case 25 :
                    // InternalWorkload.g:2199:3: kw= 'where'
                    {
                    kw=(Token)match(input,40,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWhereKeyword_24());
                    		

                    }
                    break;
                case 26 :
                    // InternalWorkload.g:2205:3: kw= 'read'
                    {
                    kw=(Token)match(input,46,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getReadKeyword_25());
                    		

                    }
                    break;
                case 27 :
                    // InternalWorkload.g:2211:3: kw= 'write'
                    {
                    kw=(Token)match(input,47,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getWriteKeyword_26());
                    		

                    }
                    break;
                case 28 :
                    // InternalWorkload.g:2217:3: kw= 'delete'
                    {
                    kw=(Token)match(input,48,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getDeleteKeyword_27());
                    		

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
    // $ANTLR end "ruleValidID"


    // $ANTLR start "ruleQueryType"
    // InternalWorkload.g:2226:1: ruleQueryType returns [Enumerator current=null] : ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) ) ;
    public final Enumerator ruleQueryType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalWorkload.g:2232:2: ( ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) ) )
            // InternalWorkload.g:2233:2: ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) )
            {
            // InternalWorkload.g:2233:2: ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) )
            int alt29=3;
            switch ( input.LA(1) ) {
            case 46:
                {
                alt29=1;
                }
                break;
            case 47:
                {
                alt29=2;
                }
                break;
            case 48:
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
                    // InternalWorkload.g:2234:3: (enumLiteral_0= 'read' )
                    {
                    // InternalWorkload.g:2234:3: (enumLiteral_0= 'read' )
                    // InternalWorkload.g:2235:4: enumLiteral_0= 'read'
                    {
                    enumLiteral_0=(Token)match(input,46,FOLLOW_2); 

                    				current = grammarAccess.getQueryTypeAccess().getREADEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getQueryTypeAccess().getREADEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalWorkload.g:2242:3: (enumLiteral_1= 'write' )
                    {
                    // InternalWorkload.g:2242:3: (enumLiteral_1= 'write' )
                    // InternalWorkload.g:2243:4: enumLiteral_1= 'write'
                    {
                    enumLiteral_1=(Token)match(input,47,FOLLOW_2); 

                    				current = grammarAccess.getQueryTypeAccess().getWRITEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getQueryTypeAccess().getWRITEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalWorkload.g:2250:3: (enumLiteral_2= 'delete' )
                    {
                    // InternalWorkload.g:2250:3: (enumLiteral_2= 'delete' )
                    // InternalWorkload.g:2251:4: enumLiteral_2= 'delete'
                    {
                    enumLiteral_2=(Token)match(input,48,FOLLOW_2); 

                    				current = grammarAccess.getQueryTypeAccess().getDELETEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getQueryTypeAccess().getDELETEEnumLiteralDeclaration_2());
                    			

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
    // $ANTLR end "ruleQueryType"


    // $ANTLR start "ruleOperator"
    // InternalWorkload.g:2261:1: ruleOperator returns [Enumerator current=null] : ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) ) ;
    public final Enumerator ruleOperator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;


        	enterRule();

        try {
            // InternalWorkload.g:2267:2: ( ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) ) )
            // InternalWorkload.g:2268:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) )
            {
            // InternalWorkload.g:2268:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) )
            int alt30=6;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt30=1;
                }
                break;
            case 49:
                {
                alt30=2;
                }
                break;
            case 50:
                {
                alt30=3;
                }
                break;
            case 51:
                {
                alt30=4;
                }
                break;
            case 52:
                {
                alt30=5;
                }
                break;
            case 53:
                {
                alt30=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }

            switch (alt30) {
                case 1 :
                    // InternalWorkload.g:2269:3: (enumLiteral_0= '=' )
                    {
                    // InternalWorkload.g:2269:3: (enumLiteral_0= '=' )
                    // InternalWorkload.g:2270:4: enumLiteral_0= '='
                    {
                    enumLiteral_0=(Token)match(input,16,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getEQEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getOperatorAccess().getEQEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalWorkload.g:2277:3: (enumLiteral_1= '>' )
                    {
                    // InternalWorkload.g:2277:3: (enumLiteral_1= '>' )
                    // InternalWorkload.g:2278:4: enumLiteral_1= '>'
                    {
                    enumLiteral_1=(Token)match(input,49,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getGEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getOperatorAccess().getGEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalWorkload.g:2285:3: (enumLiteral_2= '<' )
                    {
                    // InternalWorkload.g:2285:3: (enumLiteral_2= '<' )
                    // InternalWorkload.g:2286:4: enumLiteral_2= '<'
                    {
                    enumLiteral_2=(Token)match(input,50,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getLEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getOperatorAccess().getLEEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalWorkload.g:2293:3: (enumLiteral_3= '<=' )
                    {
                    // InternalWorkload.g:2293:3: (enumLiteral_3= '<=' )
                    // InternalWorkload.g:2294:4: enumLiteral_3= '<='
                    {
                    enumLiteral_3=(Token)match(input,51,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getLEQEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getOperatorAccess().getLEQEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalWorkload.g:2301:3: (enumLiteral_4= '>=' )
                    {
                    // InternalWorkload.g:2301:3: (enumLiteral_4= '>=' )
                    // InternalWorkload.g:2302:4: enumLiteral_4= '>='
                    {
                    enumLiteral_4=(Token)match(input,52,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getGEQEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getOperatorAccess().getGEQEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalWorkload.g:2309:3: (enumLiteral_5= '!=' )
                    {
                    // InternalWorkload.g:2309:3: (enumLiteral_5= '!=' )
                    // InternalWorkload.g:2310:4: enumLiteral_5= '!='
                    {
                    enumLiteral_5=(Token)match(input,53,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getNEQEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getOperatorAccess().getNEQEnumLiteralDeclaration_5());
                    			

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
    // $ANTLR end "ruleOperator"


    // $ANTLR start "ruleLink"
    // InternalWorkload.g:2320:1: ruleLink returns [Enumerator current=null] : ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) ) ;
    public final Enumerator ruleLink() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalWorkload.g:2326:2: ( ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) ) )
            // InternalWorkload.g:2327:2: ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) )
            {
            // InternalWorkload.g:2327:2: ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==43) ) {
                alt31=1;
            }
            else if ( (LA31_0==54) ) {
                alt31=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // InternalWorkload.g:2328:3: (enumLiteral_0= 'AND' )
                    {
                    // InternalWorkload.g:2328:3: (enumLiteral_0= 'AND' )
                    // InternalWorkload.g:2329:4: enumLiteral_0= 'AND'
                    {
                    enumLiteral_0=(Token)match(input,43,FOLLOW_2); 

                    				current = grammarAccess.getLinkAccess().getANDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getLinkAccess().getANDEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalWorkload.g:2336:3: (enumLiteral_1= 'OR' )
                    {
                    // InternalWorkload.g:2336:3: (enumLiteral_1= 'OR' )
                    // InternalWorkload.g:2337:4: enumLiteral_1= 'OR'
                    {
                    enumLiteral_1=(Token)match(input,54,FOLLOW_2); 

                    				current = grammarAccess.getLinkAccess().getOREnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getLinkAccess().getOREnumLiteralDeclaration_1());
                    			

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
    // $ANTLR end "ruleLink"

    // Delegated rules


    protected DFA24 dfa24 = new DFA24(this);
    static final String dfa_1s = "\37\uffff";
    static final String dfa_2s = "\1\uffff\34\35\2\uffff";
    static final String dfa_3s = "\1\6\34\16\2\uffff";
    static final String dfa_4s = "\1\60\34\65\2\uffff";
    static final String dfa_5s = "\35\uffff\1\2\1\1";
    static final String dfa_6s = "\37\uffff}>";
    static final String[] dfa_7s = {
            "\1\1\4\uffff\1\2\1\3\2\uffff\1\4\1\uffff\1\5\1\6\1\uffff\1\7\1\10\1\11\1\12\1\uffff\1\13\1\14\1\uffff\1\15\1\16\2\uffff\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\31\1\27\1\30\3\uffff\1\32\1\33\1\34",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "\1\35\1\uffff\1\35\12\uffff\1\36\6\uffff\1\35\4\uffff\3\35\1\uffff\1\35\2\uffff\10\35",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
    static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

    class DFA24 extends DFA {

        public DFA24(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 24;
            this.eot = dfa_1;
            this.eof = dfa_2;
            this.min = dfa_3;
            this.max = dfa_4;
            this.accept = dfa_5;
            this.special = dfa_6;
            this.transition = dfa_7;
        }
        public String getDescription() {
            return "1810:3: ( ( ( ruleValidID ) ) otherlv_1= '.' )?";
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0001C7FF36F69840L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000000010022C000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000100224000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000100204000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000100004000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000104000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000030000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000800020L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0001C7FF36F6D840L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000200000000020L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0001C08400004000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0001E7FF36F69840L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000030000000002L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0040080000000002L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x003E000000010000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0001F7FF36F69840L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000008000000L});

}