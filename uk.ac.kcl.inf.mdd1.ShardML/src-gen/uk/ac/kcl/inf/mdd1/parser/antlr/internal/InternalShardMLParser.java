package uk.ac.kcl.inf.mdd1.parser.antlr.internal;

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
import uk.ac.kcl.inf.mdd1.services.ShardMLGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalShardMLParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "'database'", "'{'", "'type:'", "'}'", "'has_many'", "'has_one'", "'belongs_to'", "'shard'", "'strategy:'", "'key:'", "'buckets:'", "'colocate_with:'", "'route'", "'policy:'", "'max_conditions:'", "'query'", "'where:'", "'sort:'", "'group_by:'", "'distinct:'", "'true'", "'?'", "'postgres'", "'mysql'", "'hash'", "'consistent_hash'", "'replicated'", "'allow'", "'warn'", "'deny'", "'read'", "'write'", "'delete'", "'='", "'>'", "'<'", "'<='", "'>='", "'!='", "'AND'", "'OR'", "'ASC'", "'DESC'"
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
    public static final int RULE_ID=5;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=6;
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


        public InternalShardMLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalShardMLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalShardMLParser.tokenNames; }
    public String getGrammarFileName() { return "InternalShardML.g"; }



     	private ShardMLGrammarAccess grammarAccess;

        public InternalShardMLParser(TokenStream input, ShardMLGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Model";
       	}

       	@Override
       	protected ShardMLGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleModel"
    // InternalShardML.g:65:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalShardML.g:65:46: (iv_ruleModel= ruleModel EOF )
            // InternalShardML.g:66:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
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
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalShardML.g:72:1: ruleModel returns [EObject current=null] : ( ( (lv_imports_0_0= ruleImport ) )* ( (lv_db_1_0= ruleDatabase ) ) ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject lv_imports_0_0 = null;

        EObject lv_db_1_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:78:2: ( ( ( (lv_imports_0_0= ruleImport ) )* ( (lv_db_1_0= ruleDatabase ) ) ) )
            // InternalShardML.g:79:2: ( ( (lv_imports_0_0= ruleImport ) )* ( (lv_db_1_0= ruleDatabase ) ) )
            {
            // InternalShardML.g:79:2: ( ( (lv_imports_0_0= ruleImport ) )* ( (lv_db_1_0= ruleDatabase ) ) )
            // InternalShardML.g:80:3: ( (lv_imports_0_0= ruleImport ) )* ( (lv_db_1_0= ruleDatabase ) )
            {
            // InternalShardML.g:80:3: ( (lv_imports_0_0= ruleImport ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==11) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalShardML.g:81:4: (lv_imports_0_0= ruleImport )
            	    {
            	    // InternalShardML.g:81:4: (lv_imports_0_0= ruleImport )
            	    // InternalShardML.g:82:5: lv_imports_0_0= ruleImport
            	    {

            	    					newCompositeNode(grammarAccess.getModelAccess().getImportsImportParserRuleCall_0_0());
            	    				
            	    pushFollow(FOLLOW_3);
            	    lv_imports_0_0=ruleImport();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getModelRule());
            	    					}
            	    					add(
            	    						current,
            	    						"imports",
            	    						lv_imports_0_0,
            	    						"uk.ac.kcl.inf.mdd1.ShardML.Import");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // InternalShardML.g:99:3: ( (lv_db_1_0= ruleDatabase ) )
            // InternalShardML.g:100:4: (lv_db_1_0= ruleDatabase )
            {
            // InternalShardML.g:100:4: (lv_db_1_0= ruleDatabase )
            // InternalShardML.g:101:5: lv_db_1_0= ruleDatabase
            {

            					newCompositeNode(grammarAccess.getModelAccess().getDbDatabaseParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_db_1_0=ruleDatabase();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getModelRule());
            					}
            					set(
            						current,
            						"db",
            						lv_db_1_0,
            						"uk.ac.kcl.inf.mdd1.ShardML.Database");
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
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleImport"
    // InternalShardML.g:122:1: entryRuleImport returns [EObject current=null] : iv_ruleImport= ruleImport EOF ;
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImport = null;


        try {
            // InternalShardML.g:122:47: (iv_ruleImport= ruleImport EOF )
            // InternalShardML.g:123:2: iv_ruleImport= ruleImport EOF
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
    // InternalShardML.g:129:1: ruleImport returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) ;
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_importURI_1_0=null;


        	enterRule();

        try {
            // InternalShardML.g:135:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) ) )
            // InternalShardML.g:136:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            {
            // InternalShardML.g:136:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) )
            // InternalShardML.g:137:3: otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) )
            {
            otherlv_0=(Token)match(input,11,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
            		
            // InternalShardML.g:141:3: ( (lv_importURI_1_0= RULE_STRING ) )
            // InternalShardML.g:142:4: (lv_importURI_1_0= RULE_STRING )
            {
            // InternalShardML.g:142:4: (lv_importURI_1_0= RULE_STRING )
            // InternalShardML.g:143:5: lv_importURI_1_0= RULE_STRING
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


    // $ANTLR start "entryRuleDatabase"
    // InternalShardML.g:163:1: entryRuleDatabase returns [EObject current=null] : iv_ruleDatabase= ruleDatabase EOF ;
    public final EObject entryRuleDatabase() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDatabase = null;


        try {
            // InternalShardML.g:163:49: (iv_ruleDatabase= ruleDatabase EOF )
            // InternalShardML.g:164:2: iv_ruleDatabase= ruleDatabase EOF
            {
             newCompositeNode(grammarAccess.getDatabaseRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDatabase=ruleDatabase();

            state._fsp--;

             current =iv_ruleDatabase; 
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
    // $ANTLR end "entryRuleDatabase"


    // $ANTLR start "ruleDatabase"
    // InternalShardML.g:170:1: ruleDatabase returns [EObject current=null] : (otherlv_0= 'database' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleDatabaseType ) ) ( (lv_shard_decl_5_0= ruleShardDecl ) )* ( (lv_route_rules_6_0= ruleRouteRule ) )* ( (lv_relationships_7_0= ruleRelationship ) )* otherlv_8= '}' ) ;
    public final EObject ruleDatabase() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_8=null;
        Enumerator lv_type_4_0 = null;

        EObject lv_shard_decl_5_0 = null;

        EObject lv_route_rules_6_0 = null;

        EObject lv_relationships_7_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:176:2: ( (otherlv_0= 'database' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleDatabaseType ) ) ( (lv_shard_decl_5_0= ruleShardDecl ) )* ( (lv_route_rules_6_0= ruleRouteRule ) )* ( (lv_relationships_7_0= ruleRelationship ) )* otherlv_8= '}' ) )
            // InternalShardML.g:177:2: (otherlv_0= 'database' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleDatabaseType ) ) ( (lv_shard_decl_5_0= ruleShardDecl ) )* ( (lv_route_rules_6_0= ruleRouteRule ) )* ( (lv_relationships_7_0= ruleRelationship ) )* otherlv_8= '}' )
            {
            // InternalShardML.g:177:2: (otherlv_0= 'database' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleDatabaseType ) ) ( (lv_shard_decl_5_0= ruleShardDecl ) )* ( (lv_route_rules_6_0= ruleRouteRule ) )* ( (lv_relationships_7_0= ruleRelationship ) )* otherlv_8= '}' )
            // InternalShardML.g:178:3: otherlv_0= 'database' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleDatabaseType ) ) ( (lv_shard_decl_5_0= ruleShardDecl ) )* ( (lv_route_rules_6_0= ruleRouteRule ) )* ( (lv_relationships_7_0= ruleRelationship ) )* otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getDatabaseAccess().getDatabaseKeyword_0());
            		
            // InternalShardML.g:182:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalShardML.g:183:4: (lv_name_1_0= RULE_ID )
            {
            // InternalShardML.g:183:4: (lv_name_1_0= RULE_ID )
            // InternalShardML.g:184:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_6); 

            					newLeafNode(lv_name_1_0, grammarAccess.getDatabaseAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDatabaseRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_7); 

            			newLeafNode(otherlv_2, grammarAccess.getDatabaseAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,14,FOLLOW_8); 

            			newLeafNode(otherlv_3, grammarAccess.getDatabaseAccess().getTypeKeyword_3());
            		
            // InternalShardML.g:208:3: ( (lv_type_4_0= ruleDatabaseType ) )
            // InternalShardML.g:209:4: (lv_type_4_0= ruleDatabaseType )
            {
            // InternalShardML.g:209:4: (lv_type_4_0= ruleDatabaseType )
            // InternalShardML.g:210:5: lv_type_4_0= ruleDatabaseType
            {

            					newCompositeNode(grammarAccess.getDatabaseAccess().getTypeDatabaseTypeEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_9);
            lv_type_4_0=ruleDatabaseType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDatabaseRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_4_0,
            						"uk.ac.kcl.inf.mdd1.ShardML.DatabaseType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:227:3: ( (lv_shard_decl_5_0= ruleShardDecl ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==19) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalShardML.g:228:4: (lv_shard_decl_5_0= ruleShardDecl )
            	    {
            	    // InternalShardML.g:228:4: (lv_shard_decl_5_0= ruleShardDecl )
            	    // InternalShardML.g:229:5: lv_shard_decl_5_0= ruleShardDecl
            	    {

            	    					newCompositeNode(grammarAccess.getDatabaseAccess().getShard_declShardDeclParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_9);
            	    lv_shard_decl_5_0=ruleShardDecl();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDatabaseRule());
            	    					}
            	    					add(
            	    						current,
            	    						"shard_decl",
            	    						lv_shard_decl_5_0,
            	    						"uk.ac.kcl.inf.mdd1.ShardML.ShardDecl");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // InternalShardML.g:246:3: ( (lv_route_rules_6_0= ruleRouteRule ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==24) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalShardML.g:247:4: (lv_route_rules_6_0= ruleRouteRule )
            	    {
            	    // InternalShardML.g:247:4: (lv_route_rules_6_0= ruleRouteRule )
            	    // InternalShardML.g:248:5: lv_route_rules_6_0= ruleRouteRule
            	    {

            	    					newCompositeNode(grammarAccess.getDatabaseAccess().getRoute_rulesRouteRuleParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_10);
            	    lv_route_rules_6_0=ruleRouteRule();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDatabaseRule());
            	    					}
            	    					add(
            	    						current,
            	    						"route_rules",
            	    						lv_route_rules_6_0,
            	    						"uk.ac.kcl.inf.mdd1.ShardML.RouteRule");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // InternalShardML.g:265:3: ( (lv_relationships_7_0= ruleRelationship ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==RULE_ID) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalShardML.g:266:4: (lv_relationships_7_0= ruleRelationship )
            	    {
            	    // InternalShardML.g:266:4: (lv_relationships_7_0= ruleRelationship )
            	    // InternalShardML.g:267:5: lv_relationships_7_0= ruleRelationship
            	    {

            	    					newCompositeNode(grammarAccess.getDatabaseAccess().getRelationshipsRelationshipParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_11);
            	    lv_relationships_7_0=ruleRelationship();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDatabaseRule());
            	    					}
            	    					add(
            	    						current,
            	    						"relationships",
            	    						lv_relationships_7_0,
            	    						"uk.ac.kcl.inf.mdd1.ShardML.Relationship");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            otherlv_8=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getDatabaseAccess().getRightCurlyBracketKeyword_8());
            		

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
    // $ANTLR end "ruleDatabase"


    // $ANTLR start "entryRuleRelationship"
    // InternalShardML.g:292:1: entryRuleRelationship returns [EObject current=null] : iv_ruleRelationship= ruleRelationship EOF ;
    public final EObject entryRuleRelationship() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRelationship = null;


        try {
            // InternalShardML.g:292:53: (iv_ruleRelationship= ruleRelationship EOF )
            // InternalShardML.g:293:2: iv_ruleRelationship= ruleRelationship EOF
            {
             newCompositeNode(grammarAccess.getRelationshipRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRelationship=ruleRelationship();

            state._fsp--;

             current =iv_ruleRelationship; 
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
    // $ANTLR end "entryRuleRelationship"


    // $ANTLR start "ruleRelationship"
    // InternalShardML.g:299:1: ruleRelationship returns [EObject current=null] : (this_HasMany_0= ruleHasMany | this_HasOne_1= ruleHasOne | this_BelongsTo_2= ruleBelongsTo ) ;
    public final EObject ruleRelationship() throws RecognitionException {
        EObject current = null;

        EObject this_HasMany_0 = null;

        EObject this_HasOne_1 = null;

        EObject this_BelongsTo_2 = null;



        	enterRule();

        try {
            // InternalShardML.g:305:2: ( (this_HasMany_0= ruleHasMany | this_HasOne_1= ruleHasOne | this_BelongsTo_2= ruleBelongsTo ) )
            // InternalShardML.g:306:2: (this_HasMany_0= ruleHasMany | this_HasOne_1= ruleHasOne | this_BelongsTo_2= ruleBelongsTo )
            {
            // InternalShardML.g:306:2: (this_HasMany_0= ruleHasMany | this_HasOne_1= ruleHasOne | this_BelongsTo_2= ruleBelongsTo )
            int alt5=3;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==RULE_ID) ) {
                switch ( input.LA(2) ) {
                case 17:
                    {
                    alt5=2;
                    }
                    break;
                case 18:
                    {
                    alt5=3;
                    }
                    break;
                case 16:
                    {
                    alt5=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // InternalShardML.g:307:3: this_HasMany_0= ruleHasMany
                    {

                    			newCompositeNode(grammarAccess.getRelationshipAccess().getHasManyParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_HasMany_0=ruleHasMany();

                    state._fsp--;


                    			current = this_HasMany_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalShardML.g:316:3: this_HasOne_1= ruleHasOne
                    {

                    			newCompositeNode(grammarAccess.getRelationshipAccess().getHasOneParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_HasOne_1=ruleHasOne();

                    state._fsp--;


                    			current = this_HasOne_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalShardML.g:325:3: this_BelongsTo_2= ruleBelongsTo
                    {

                    			newCompositeNode(grammarAccess.getRelationshipAccess().getBelongsToParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_BelongsTo_2=ruleBelongsTo();

                    state._fsp--;


                    			current = this_BelongsTo_2;
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
    // $ANTLR end "ruleRelationship"


    // $ANTLR start "entryRuleHasMany"
    // InternalShardML.g:337:1: entryRuleHasMany returns [EObject current=null] : iv_ruleHasMany= ruleHasMany EOF ;
    public final EObject entryRuleHasMany() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHasMany = null;


        try {
            // InternalShardML.g:337:48: (iv_ruleHasMany= ruleHasMany EOF )
            // InternalShardML.g:338:2: iv_ruleHasMany= ruleHasMany EOF
            {
             newCompositeNode(grammarAccess.getHasManyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleHasMany=ruleHasMany();

            state._fsp--;

             current =iv_ruleHasMany; 
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
    // $ANTLR end "entryRuleHasMany"


    // $ANTLR start "ruleHasMany"
    // InternalShardML.g:344:1: ruleHasMany returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'has_many' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleHasMany() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalShardML.g:350:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'has_many' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalShardML.g:351:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'has_many' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalShardML.g:351:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'has_many' ( (otherlv_2= RULE_ID ) ) )
            // InternalShardML.g:352:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= 'has_many' ( (otherlv_2= RULE_ID ) )
            {
            // InternalShardML.g:352:3: ( (otherlv_0= RULE_ID ) )
            // InternalShardML.g:353:4: (otherlv_0= RULE_ID )
            {
            // InternalShardML.g:353:4: (otherlv_0= RULE_ID )
            // InternalShardML.g:354:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getHasManyRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_12); 

            					newLeafNode(otherlv_0, grammarAccess.getHasManyAccess().getSubjectTableCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,16,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getHasManyAccess().getHas_manyKeyword_1());
            		
            // InternalShardML.g:369:3: ( (otherlv_2= RULE_ID ) )
            // InternalShardML.g:370:4: (otherlv_2= RULE_ID )
            {
            // InternalShardML.g:370:4: (otherlv_2= RULE_ID )
            // InternalShardML.g:371:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getHasManyRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_2, grammarAccess.getHasManyAccess().getManyTableCrossReference_2_0());
            				

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
    // $ANTLR end "ruleHasMany"


    // $ANTLR start "entryRuleHasOne"
    // InternalShardML.g:386:1: entryRuleHasOne returns [EObject current=null] : iv_ruleHasOne= ruleHasOne EOF ;
    public final EObject entryRuleHasOne() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHasOne = null;


        try {
            // InternalShardML.g:386:47: (iv_ruleHasOne= ruleHasOne EOF )
            // InternalShardML.g:387:2: iv_ruleHasOne= ruleHasOne EOF
            {
             newCompositeNode(grammarAccess.getHasOneRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleHasOne=ruleHasOne();

            state._fsp--;

             current =iv_ruleHasOne; 
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
    // $ANTLR end "entryRuleHasOne"


    // $ANTLR start "ruleHasOne"
    // InternalShardML.g:393:1: ruleHasOne returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'has_one' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleHasOne() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalShardML.g:399:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'has_one' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalShardML.g:400:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'has_one' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalShardML.g:400:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'has_one' ( (otherlv_2= RULE_ID ) ) )
            // InternalShardML.g:401:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= 'has_one' ( (otherlv_2= RULE_ID ) )
            {
            // InternalShardML.g:401:3: ( (otherlv_0= RULE_ID ) )
            // InternalShardML.g:402:4: (otherlv_0= RULE_ID )
            {
            // InternalShardML.g:402:4: (otherlv_0= RULE_ID )
            // InternalShardML.g:403:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getHasOneRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_13); 

            					newLeafNode(otherlv_0, grammarAccess.getHasOneAccess().getSubjectTableCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,17,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getHasOneAccess().getHas_oneKeyword_1());
            		
            // InternalShardML.g:418:3: ( (otherlv_2= RULE_ID ) )
            // InternalShardML.g:419:4: (otherlv_2= RULE_ID )
            {
            // InternalShardML.g:419:4: (otherlv_2= RULE_ID )
            // InternalShardML.g:420:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getHasOneRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_2, grammarAccess.getHasOneAccess().getOneTableCrossReference_2_0());
            				

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
    // $ANTLR end "ruleHasOne"


    // $ANTLR start "entryRuleBelongsTo"
    // InternalShardML.g:435:1: entryRuleBelongsTo returns [EObject current=null] : iv_ruleBelongsTo= ruleBelongsTo EOF ;
    public final EObject entryRuleBelongsTo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBelongsTo = null;


        try {
            // InternalShardML.g:435:50: (iv_ruleBelongsTo= ruleBelongsTo EOF )
            // InternalShardML.g:436:2: iv_ruleBelongsTo= ruleBelongsTo EOF
            {
             newCompositeNode(grammarAccess.getBelongsToRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBelongsTo=ruleBelongsTo();

            state._fsp--;

             current =iv_ruleBelongsTo; 
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
    // $ANTLR end "entryRuleBelongsTo"


    // $ANTLR start "ruleBelongsTo"
    // InternalShardML.g:442:1: ruleBelongsTo returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'belongs_to' ( (otherlv_2= RULE_ID ) ) ) ;
    public final EObject ruleBelongsTo() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;


        	enterRule();

        try {
            // InternalShardML.g:448:2: ( ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'belongs_to' ( (otherlv_2= RULE_ID ) ) ) )
            // InternalShardML.g:449:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'belongs_to' ( (otherlv_2= RULE_ID ) ) )
            {
            // InternalShardML.g:449:2: ( ( (otherlv_0= RULE_ID ) ) otherlv_1= 'belongs_to' ( (otherlv_2= RULE_ID ) ) )
            // InternalShardML.g:450:3: ( (otherlv_0= RULE_ID ) ) otherlv_1= 'belongs_to' ( (otherlv_2= RULE_ID ) )
            {
            // InternalShardML.g:450:3: ( (otherlv_0= RULE_ID ) )
            // InternalShardML.g:451:4: (otherlv_0= RULE_ID )
            {
            // InternalShardML.g:451:4: (otherlv_0= RULE_ID )
            // InternalShardML.g:452:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBelongsToRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_14); 

            					newLeafNode(otherlv_0, grammarAccess.getBelongsToAccess().getSubjectTableCrossReference_0_0());
            				

            }


            }

            otherlv_1=(Token)match(input,18,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getBelongsToAccess().getBelongs_toKeyword_1());
            		
            // InternalShardML.g:467:3: ( (otherlv_2= RULE_ID ) )
            // InternalShardML.g:468:4: (otherlv_2= RULE_ID )
            {
            // InternalShardML.g:468:4: (otherlv_2= RULE_ID )
            // InternalShardML.g:469:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBelongsToRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_2); 

            					newLeafNode(otherlv_2, grammarAccess.getBelongsToAccess().getBelongsTableCrossReference_2_0());
            				

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
    // $ANTLR end "ruleBelongsTo"


    // $ANTLR start "entryRuleShardDecl"
    // InternalShardML.g:484:1: entryRuleShardDecl returns [EObject current=null] : iv_ruleShardDecl= ruleShardDecl EOF ;
    public final EObject entryRuleShardDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleShardDecl = null;


        try {
            // InternalShardML.g:484:50: (iv_ruleShardDecl= ruleShardDecl EOF )
            // InternalShardML.g:485:2: iv_ruleShardDecl= ruleShardDecl EOF
            {
             newCompositeNode(grammarAccess.getShardDeclRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleShardDecl=ruleShardDecl();

            state._fsp--;

             current =iv_ruleShardDecl; 
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
    // $ANTLR end "entryRuleShardDecl"


    // $ANTLR start "ruleShardDecl"
    // InternalShardML.g:491:1: ruleShardDecl returns [EObject current=null] : (otherlv_0= 'shard' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= ruleStrategy ) ) (otherlv_5= 'key:' ( (otherlv_6= RULE_ID ) ) )? (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )? (otherlv_9= 'colocate_with:' ( (otherlv_10= RULE_ID ) ) )? otherlv_11= '}' ) ;
    public final EObject ruleShardDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_buckets_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Enumerator lv_strategy_4_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:497:2: ( (otherlv_0= 'shard' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= ruleStrategy ) ) (otherlv_5= 'key:' ( (otherlv_6= RULE_ID ) ) )? (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )? (otherlv_9= 'colocate_with:' ( (otherlv_10= RULE_ID ) ) )? otherlv_11= '}' ) )
            // InternalShardML.g:498:2: (otherlv_0= 'shard' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= ruleStrategy ) ) (otherlv_5= 'key:' ( (otherlv_6= RULE_ID ) ) )? (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )? (otherlv_9= 'colocate_with:' ( (otherlv_10= RULE_ID ) ) )? otherlv_11= '}' )
            {
            // InternalShardML.g:498:2: (otherlv_0= 'shard' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= ruleStrategy ) ) (otherlv_5= 'key:' ( (otherlv_6= RULE_ID ) ) )? (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )? (otherlv_9= 'colocate_with:' ( (otherlv_10= RULE_ID ) ) )? otherlv_11= '}' )
            // InternalShardML.g:499:3: otherlv_0= 'shard' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= ruleStrategy ) ) (otherlv_5= 'key:' ( (otherlv_6= RULE_ID ) ) )? (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )? (otherlv_9= 'colocate_with:' ( (otherlv_10= RULE_ID ) ) )? otherlv_11= '}'
            {
            otherlv_0=(Token)match(input,19,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getShardDeclAccess().getShardKeyword_0());
            		
            // InternalShardML.g:503:3: ( (otherlv_1= RULE_ID ) )
            // InternalShardML.g:504:4: (otherlv_1= RULE_ID )
            {
            // InternalShardML.g:504:4: (otherlv_1= RULE_ID )
            // InternalShardML.g:505:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getShardDeclRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_6); 

            					newLeafNode(otherlv_1, grammarAccess.getShardDeclAccess().getEntityTableCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_15); 

            			newLeafNode(otherlv_2, grammarAccess.getShardDeclAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,20,FOLLOW_16); 

            			newLeafNode(otherlv_3, grammarAccess.getShardDeclAccess().getStrategyKeyword_3());
            		
            // InternalShardML.g:524:3: ( (lv_strategy_4_0= ruleStrategy ) )
            // InternalShardML.g:525:4: (lv_strategy_4_0= ruleStrategy )
            {
            // InternalShardML.g:525:4: (lv_strategy_4_0= ruleStrategy )
            // InternalShardML.g:526:5: lv_strategy_4_0= ruleStrategy
            {

            					newCompositeNode(grammarAccess.getShardDeclAccess().getStrategyStrategyEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_17);
            lv_strategy_4_0=ruleStrategy();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getShardDeclRule());
            					}
            					set(
            						current,
            						"strategy",
            						lv_strategy_4_0,
            						"uk.ac.kcl.inf.mdd1.ShardML.Strategy");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:543:3: (otherlv_5= 'key:' ( (otherlv_6= RULE_ID ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==21) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalShardML.g:544:4: otherlv_5= 'key:' ( (otherlv_6= RULE_ID ) )
                    {
                    otherlv_5=(Token)match(input,21,FOLLOW_5); 

                    				newLeafNode(otherlv_5, grammarAccess.getShardDeclAccess().getKeyKeyword_5_0());
                    			
                    // InternalShardML.g:548:4: ( (otherlv_6= RULE_ID ) )
                    // InternalShardML.g:549:5: (otherlv_6= RULE_ID )
                    {
                    // InternalShardML.g:549:5: (otherlv_6= RULE_ID )
                    // InternalShardML.g:550:6: otherlv_6= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getShardDeclRule());
                    						}
                    					
                    otherlv_6=(Token)match(input,RULE_ID,FOLLOW_18); 

                    						newLeafNode(otherlv_6, grammarAccess.getShardDeclAccess().getIdColumnCrossReference_5_1_0());
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalShardML.g:562:3: (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==22) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalShardML.g:563:4: otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,22,FOLLOW_19); 

                    				newLeafNode(otherlv_7, grammarAccess.getShardDeclAccess().getBucketsKeyword_6_0());
                    			
                    // InternalShardML.g:567:4: ( (lv_buckets_8_0= RULE_INT ) )
                    // InternalShardML.g:568:5: (lv_buckets_8_0= RULE_INT )
                    {
                    // InternalShardML.g:568:5: (lv_buckets_8_0= RULE_INT )
                    // InternalShardML.g:569:6: lv_buckets_8_0= RULE_INT
                    {
                    lv_buckets_8_0=(Token)match(input,RULE_INT,FOLLOW_20); 

                    						newLeafNode(lv_buckets_8_0, grammarAccess.getShardDeclAccess().getBucketsINTTerminalRuleCall_6_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getShardDeclRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"buckets",
                    							lv_buckets_8_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalShardML.g:586:3: (otherlv_9= 'colocate_with:' ( (otherlv_10= RULE_ID ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==23) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalShardML.g:587:4: otherlv_9= 'colocate_with:' ( (otherlv_10= RULE_ID ) )
                    {
                    otherlv_9=(Token)match(input,23,FOLLOW_5); 

                    				newLeafNode(otherlv_9, grammarAccess.getShardDeclAccess().getColocate_withKeyword_7_0());
                    			
                    // InternalShardML.g:591:4: ( (otherlv_10= RULE_ID ) )
                    // InternalShardML.g:592:5: (otherlv_10= RULE_ID )
                    {
                    // InternalShardML.g:592:5: (otherlv_10= RULE_ID )
                    // InternalShardML.g:593:6: otherlv_10= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getShardDeclRule());
                    						}
                    					
                    otherlv_10=(Token)match(input,RULE_ID,FOLLOW_21); 

                    						newLeafNode(otherlv_10, grammarAccess.getShardDeclAccess().getColocateTableCrossReference_7_1_0());
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_11=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_11, grammarAccess.getShardDeclAccess().getRightCurlyBracketKeyword_8());
            		

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
    // $ANTLR end "ruleShardDecl"


    // $ANTLR start "entryRuleRouteRule"
    // InternalShardML.g:613:1: entryRuleRouteRule returns [EObject current=null] : iv_ruleRouteRule= ruleRouteRule EOF ;
    public final EObject entryRuleRouteRule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRouteRule = null;


        try {
            // InternalShardML.g:613:50: (iv_ruleRouteRule= ruleRouteRule EOF )
            // InternalShardML.g:614:2: iv_ruleRouteRule= ruleRouteRule EOF
            {
             newCompositeNode(grammarAccess.getRouteRuleRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRouteRule=ruleRouteRule();

            state._fsp--;

             current =iv_ruleRouteRule; 
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
    // $ANTLR end "entryRuleRouteRule"


    // $ANTLR start "ruleRouteRule"
    // InternalShardML.g:620:1: ruleRouteRule returns [EObject current=null] : (otherlv_0= 'route' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'policy:' ( (lv_policy_4_0= rulePolicy ) ) (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )? ( (lv_queries_7_0= ruleQueryPattern ) )* otherlv_8= '}' ) ;
    public final EObject ruleRouteRule() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_condtions_6_0=null;
        Token otherlv_8=null;
        Enumerator lv_policy_4_0 = null;

        EObject lv_queries_7_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:626:2: ( (otherlv_0= 'route' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'policy:' ( (lv_policy_4_0= rulePolicy ) ) (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )? ( (lv_queries_7_0= ruleQueryPattern ) )* otherlv_8= '}' ) )
            // InternalShardML.g:627:2: (otherlv_0= 'route' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'policy:' ( (lv_policy_4_0= rulePolicy ) ) (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )? ( (lv_queries_7_0= ruleQueryPattern ) )* otherlv_8= '}' )
            {
            // InternalShardML.g:627:2: (otherlv_0= 'route' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'policy:' ( (lv_policy_4_0= rulePolicy ) ) (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )? ( (lv_queries_7_0= ruleQueryPattern ) )* otherlv_8= '}' )
            // InternalShardML.g:628:3: otherlv_0= 'route' ( (otherlv_1= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'policy:' ( (lv_policy_4_0= rulePolicy ) ) (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )? ( (lv_queries_7_0= ruleQueryPattern ) )* otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,24,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getRouteRuleAccess().getRouteKeyword_0());
            		
            // InternalShardML.g:632:3: ( (otherlv_1= RULE_ID ) )
            // InternalShardML.g:633:4: (otherlv_1= RULE_ID )
            {
            // InternalShardML.g:633:4: (otherlv_1= RULE_ID )
            // InternalShardML.g:634:5: otherlv_1= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRouteRuleRule());
            					}
            				
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_6); 

            					newLeafNode(otherlv_1, grammarAccess.getRouteRuleAccess().getEntityTableCrossReference_1_0());
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_22); 

            			newLeafNode(otherlv_2, grammarAccess.getRouteRuleAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,25,FOLLOW_23); 

            			newLeafNode(otherlv_3, grammarAccess.getRouteRuleAccess().getPolicyKeyword_3());
            		
            // InternalShardML.g:653:3: ( (lv_policy_4_0= rulePolicy ) )
            // InternalShardML.g:654:4: (lv_policy_4_0= rulePolicy )
            {
            // InternalShardML.g:654:4: (lv_policy_4_0= rulePolicy )
            // InternalShardML.g:655:5: lv_policy_4_0= rulePolicy
            {

            					newCompositeNode(grammarAccess.getRouteRuleAccess().getPolicyPolicyEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_24);
            lv_policy_4_0=rulePolicy();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRouteRuleRule());
            					}
            					set(
            						current,
            						"policy",
            						lv_policy_4_0,
            						"uk.ac.kcl.inf.mdd1.ShardML.Policy");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:672:3: (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==26) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalShardML.g:673:4: otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,26,FOLLOW_19); 

                    				newLeafNode(otherlv_5, grammarAccess.getRouteRuleAccess().getMax_conditionsKeyword_5_0());
                    			
                    // InternalShardML.g:677:4: ( (lv_condtions_6_0= RULE_INT ) )
                    // InternalShardML.g:678:5: (lv_condtions_6_0= RULE_INT )
                    {
                    // InternalShardML.g:678:5: (lv_condtions_6_0= RULE_INT )
                    // InternalShardML.g:679:6: lv_condtions_6_0= RULE_INT
                    {
                    lv_condtions_6_0=(Token)match(input,RULE_INT,FOLLOW_25); 

                    						newLeafNode(lv_condtions_6_0, grammarAccess.getRouteRuleAccess().getCondtionsINTTerminalRuleCall_5_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRouteRuleRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"condtions",
                    							lv_condtions_6_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalShardML.g:696:3: ( (lv_queries_7_0= ruleQueryPattern ) )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==27) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalShardML.g:697:4: (lv_queries_7_0= ruleQueryPattern )
            	    {
            	    // InternalShardML.g:697:4: (lv_queries_7_0= ruleQueryPattern )
            	    // InternalShardML.g:698:5: lv_queries_7_0= ruleQueryPattern
            	    {

            	    					newCompositeNode(grammarAccess.getRouteRuleAccess().getQueriesQueryPatternParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_25);
            	    lv_queries_7_0=ruleQueryPattern();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getRouteRuleRule());
            	    					}
            	    					add(
            	    						current,
            	    						"queries",
            	    						lv_queries_7_0,
            	    						"uk.ac.kcl.inf.mdd1.ShardML.QueryPattern");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            otherlv_8=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getRouteRuleAccess().getRightCurlyBracketKeyword_7());
            		

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
    // $ANTLR end "ruleRouteRule"


    // $ANTLR start "entryRuleQueryPattern"
    // InternalShardML.g:723:1: entryRuleQueryPattern returns [EObject current=null] : iv_ruleQueryPattern= ruleQueryPattern EOF ;
    public final EObject entryRuleQueryPattern() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQueryPattern = null;


        try {
            // InternalShardML.g:723:53: (iv_ruleQueryPattern= ruleQueryPattern EOF )
            // InternalShardML.g:724:2: iv_ruleQueryPattern= ruleQueryPattern EOF
            {
             newCompositeNode(grammarAccess.getQueryPatternRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleQueryPattern=ruleQueryPattern();

            state._fsp--;

             current =iv_ruleQueryPattern; 
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
    // $ANTLR end "entryRuleQueryPattern"


    // $ANTLR start "ruleQueryPattern"
    // InternalShardML.g:730:1: ruleQueryPattern returns [EObject current=null] : (otherlv_0= 'query' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_queryType_4_0= ruleQueryType ) ) (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )? (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )? (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )? (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )? otherlv_13= '}' ) ;
    public final EObject ruleQueryPattern() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token lv_distinct_12_0=null;
        Token otherlv_13=null;
        Enumerator lv_queryType_4_0 = null;

        EObject lv_where_6_0 = null;

        EObject lv_sort_8_0 = null;

        EObject lv_group_10_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:736:2: ( (otherlv_0= 'query' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_queryType_4_0= ruleQueryType ) ) (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )? (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )? (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )? (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )? otherlv_13= '}' ) )
            // InternalShardML.g:737:2: (otherlv_0= 'query' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_queryType_4_0= ruleQueryType ) ) (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )? (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )? (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )? (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )? otherlv_13= '}' )
            {
            // InternalShardML.g:737:2: (otherlv_0= 'query' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_queryType_4_0= ruleQueryType ) ) (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )? (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )? (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )? (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )? otherlv_13= '}' )
            // InternalShardML.g:738:3: otherlv_0= 'query' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_queryType_4_0= ruleQueryType ) ) (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )? (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )? (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )? (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )? otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,27,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getQueryPatternAccess().getQueryKeyword_0());
            		
            // InternalShardML.g:742:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalShardML.g:743:4: (lv_name_1_0= RULE_ID )
            {
            // InternalShardML.g:743:4: (lv_name_1_0= RULE_ID )
            // InternalShardML.g:744:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_6); 

            					newLeafNode(lv_name_1_0, grammarAccess.getQueryPatternAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getQueryPatternRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_7); 

            			newLeafNode(otherlv_2, grammarAccess.getQueryPatternAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,14,FOLLOW_26); 

            			newLeafNode(otherlv_3, grammarAccess.getQueryPatternAccess().getTypeKeyword_3());
            		
            // InternalShardML.g:768:3: ( (lv_queryType_4_0= ruleQueryType ) )
            // InternalShardML.g:769:4: (lv_queryType_4_0= ruleQueryType )
            {
            // InternalShardML.g:769:4: (lv_queryType_4_0= ruleQueryType )
            // InternalShardML.g:770:5: lv_queryType_4_0= ruleQueryType
            {

            					newCompositeNode(grammarAccess.getQueryPatternAccess().getQueryTypeQueryTypeEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_27);
            lv_queryType_4_0=ruleQueryType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getQueryPatternRule());
            					}
            					set(
            						current,
            						"queryType",
            						lv_queryType_4_0,
            						"uk.ac.kcl.inf.mdd1.ShardML.QueryType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:787:3: (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==28) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalShardML.g:788:4: otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) )
                    {
                    otherlv_5=(Token)match(input,28,FOLLOW_5); 

                    				newLeafNode(otherlv_5, grammarAccess.getQueryPatternAccess().getWhereKeyword_5_0());
                    			
                    // InternalShardML.g:792:4: ( (lv_where_6_0= ruleWhereClause ) )
                    // InternalShardML.g:793:5: (lv_where_6_0= ruleWhereClause )
                    {
                    // InternalShardML.g:793:5: (lv_where_6_0= ruleWhereClause )
                    // InternalShardML.g:794:6: lv_where_6_0= ruleWhereClause
                    {

                    						newCompositeNode(grammarAccess.getQueryPatternAccess().getWhereWhereClauseParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_28);
                    lv_where_6_0=ruleWhereClause();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQueryPatternRule());
                    						}
                    						set(
                    							current,
                    							"where",
                    							lv_where_6_0,
                    							"uk.ac.kcl.inf.mdd1.ShardML.WhereClause");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalShardML.g:812:3: (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==29) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalShardML.g:813:4: otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) )
                    {
                    otherlv_7=(Token)match(input,29,FOLLOW_5); 

                    				newLeafNode(otherlv_7, grammarAccess.getQueryPatternAccess().getSortKeyword_6_0());
                    			
                    // InternalShardML.g:817:4: ( (lv_sort_8_0= ruleSortClause ) )
                    // InternalShardML.g:818:5: (lv_sort_8_0= ruleSortClause )
                    {
                    // InternalShardML.g:818:5: (lv_sort_8_0= ruleSortClause )
                    // InternalShardML.g:819:6: lv_sort_8_0= ruleSortClause
                    {

                    						newCompositeNode(grammarAccess.getQueryPatternAccess().getSortSortClauseParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_29);
                    lv_sort_8_0=ruleSortClause();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQueryPatternRule());
                    						}
                    						set(
                    							current,
                    							"sort",
                    							lv_sort_8_0,
                    							"uk.ac.kcl.inf.mdd1.ShardML.SortClause");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalShardML.g:837:3: (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==30) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalShardML.g:838:4: otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) )
                    {
                    otherlv_9=(Token)match(input,30,FOLLOW_5); 

                    				newLeafNode(otherlv_9, grammarAccess.getQueryPatternAccess().getGroup_byKeyword_7_0());
                    			
                    // InternalShardML.g:842:4: ( (lv_group_10_0= ruleGroupBy ) )
                    // InternalShardML.g:843:5: (lv_group_10_0= ruleGroupBy )
                    {
                    // InternalShardML.g:843:5: (lv_group_10_0= ruleGroupBy )
                    // InternalShardML.g:844:6: lv_group_10_0= ruleGroupBy
                    {

                    						newCompositeNode(grammarAccess.getQueryPatternAccess().getGroupGroupByParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_30);
                    lv_group_10_0=ruleGroupBy();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQueryPatternRule());
                    						}
                    						set(
                    							current,
                    							"group",
                    							lv_group_10_0,
                    							"uk.ac.kcl.inf.mdd1.ShardML.GroupBy");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalShardML.g:862:3: (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==31) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalShardML.g:863:4: otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) )
                    {
                    otherlv_11=(Token)match(input,31,FOLLOW_31); 

                    				newLeafNode(otherlv_11, grammarAccess.getQueryPatternAccess().getDistinctKeyword_8_0());
                    			
                    // InternalShardML.g:867:4: ( (lv_distinct_12_0= 'true' ) )
                    // InternalShardML.g:868:5: (lv_distinct_12_0= 'true' )
                    {
                    // InternalShardML.g:868:5: (lv_distinct_12_0= 'true' )
                    // InternalShardML.g:869:6: lv_distinct_12_0= 'true'
                    {
                    lv_distinct_12_0=(Token)match(input,32,FOLLOW_21); 

                    						newLeafNode(lv_distinct_12_0, grammarAccess.getQueryPatternAccess().getDistinctTrueKeyword_8_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getQueryPatternRule());
                    						}
                    						setWithLastConsumed(current, "distinct", lv_distinct_12_0 != null, "true");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_13=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_13, grammarAccess.getQueryPatternAccess().getRightCurlyBracketKeyword_9());
            		

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
    // $ANTLR end "ruleQueryPattern"


    // $ANTLR start "entryRuleWhereClause"
    // InternalShardML.g:890:1: entryRuleWhereClause returns [EObject current=null] : iv_ruleWhereClause= ruleWhereClause EOF ;
    public final EObject entryRuleWhereClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhereClause = null;


        try {
            // InternalShardML.g:890:52: (iv_ruleWhereClause= ruleWhereClause EOF )
            // InternalShardML.g:891:2: iv_ruleWhereClause= ruleWhereClause EOF
            {
             newCompositeNode(grammarAccess.getWhereClauseRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleWhereClause=ruleWhereClause();

            state._fsp--;

             current =iv_ruleWhereClause; 
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
    // $ANTLR end "entryRuleWhereClause"


    // $ANTLR start "ruleWhereClause"
    // InternalShardML.g:897:1: ruleWhereClause returns [EObject current=null] : ( ( (lv_first_0_0= ruleWhereCondition ) ) ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )* ) ;
    public final EObject ruleWhereClause() throws RecognitionException {
        EObject current = null;

        EObject lv_first_0_0 = null;

        Enumerator lv_link_1_0 = null;

        EObject lv_rest_2_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:903:2: ( ( ( (lv_first_0_0= ruleWhereCondition ) ) ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )* ) )
            // InternalShardML.g:904:2: ( ( (lv_first_0_0= ruleWhereCondition ) ) ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )* )
            {
            // InternalShardML.g:904:2: ( ( (lv_first_0_0= ruleWhereCondition ) ) ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )* )
            // InternalShardML.g:905:3: ( (lv_first_0_0= ruleWhereCondition ) ) ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )*
            {
            // InternalShardML.g:905:3: ( (lv_first_0_0= ruleWhereCondition ) )
            // InternalShardML.g:906:4: (lv_first_0_0= ruleWhereCondition )
            {
            // InternalShardML.g:906:4: (lv_first_0_0= ruleWhereCondition )
            // InternalShardML.g:907:5: lv_first_0_0= ruleWhereCondition
            {

            					newCompositeNode(grammarAccess.getWhereClauseAccess().getFirstWhereConditionParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_32);
            lv_first_0_0=ruleWhereCondition();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWhereClauseRule());
            					}
            					set(
            						current,
            						"first",
            						lv_first_0_0,
            						"uk.ac.kcl.inf.mdd1.ShardML.WhereCondition");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:924:3: ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>=51 && LA15_0<=52)) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalShardML.g:925:4: ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) )
            	    {
            	    // InternalShardML.g:925:4: ( (lv_link_1_0= ruleLink ) )
            	    // InternalShardML.g:926:5: (lv_link_1_0= ruleLink )
            	    {
            	    // InternalShardML.g:926:5: (lv_link_1_0= ruleLink )
            	    // InternalShardML.g:927:6: lv_link_1_0= ruleLink
            	    {

            	    						newCompositeNode(grammarAccess.getWhereClauseAccess().getLinkLinkEnumRuleCall_1_0_0());
            	    					
            	    pushFollow(FOLLOW_5);
            	    lv_link_1_0=ruleLink();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getWhereClauseRule());
            	    						}
            	    						add(
            	    							current,
            	    							"link",
            	    							lv_link_1_0,
            	    							"uk.ac.kcl.inf.mdd1.ShardML.Link");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalShardML.g:944:4: ( (lv_rest_2_0= ruleWhereCondition ) )
            	    // InternalShardML.g:945:5: (lv_rest_2_0= ruleWhereCondition )
            	    {
            	    // InternalShardML.g:945:5: (lv_rest_2_0= ruleWhereCondition )
            	    // InternalShardML.g:946:6: lv_rest_2_0= ruleWhereCondition
            	    {

            	    						newCompositeNode(grammarAccess.getWhereClauseAccess().getRestWhereConditionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_32);
            	    lv_rest_2_0=ruleWhereCondition();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getWhereClauseRule());
            	    						}
            	    						add(
            	    							current,
            	    							"rest",
            	    							lv_rest_2_0,
            	    							"uk.ac.kcl.inf.mdd1.ShardML.WhereCondition");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop15;
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
    // $ANTLR end "ruleWhereClause"


    // $ANTLR start "entryRuleWhereCondition"
    // InternalShardML.g:968:1: entryRuleWhereCondition returns [EObject current=null] : iv_ruleWhereCondition= ruleWhereCondition EOF ;
    public final EObject entryRuleWhereCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhereCondition = null;


        try {
            // InternalShardML.g:968:55: (iv_ruleWhereCondition= ruleWhereCondition EOF )
            // InternalShardML.g:969:2: iv_ruleWhereCondition= ruleWhereCondition EOF
            {
             newCompositeNode(grammarAccess.getWhereConditionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleWhereCondition=ruleWhereCondition();

            state._fsp--;

             current =iv_ruleWhereCondition; 
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
    // $ANTLR end "entryRuleWhereCondition"


    // $ANTLR start "ruleWhereCondition"
    // InternalShardML.g:975:1: ruleWhereCondition returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) ( (lv_op_1_0= ruleOperator ) ) otherlv_2= '?' ) ;
    public final EObject ruleWhereCondition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Enumerator lv_op_1_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:981:2: ( ( ( (otherlv_0= RULE_ID ) ) ( (lv_op_1_0= ruleOperator ) ) otherlv_2= '?' ) )
            // InternalShardML.g:982:2: ( ( (otherlv_0= RULE_ID ) ) ( (lv_op_1_0= ruleOperator ) ) otherlv_2= '?' )
            {
            // InternalShardML.g:982:2: ( ( (otherlv_0= RULE_ID ) ) ( (lv_op_1_0= ruleOperator ) ) otherlv_2= '?' )
            // InternalShardML.g:983:3: ( (otherlv_0= RULE_ID ) ) ( (lv_op_1_0= ruleOperator ) ) otherlv_2= '?'
            {
            // InternalShardML.g:983:3: ( (otherlv_0= RULE_ID ) )
            // InternalShardML.g:984:4: (otherlv_0= RULE_ID )
            {
            // InternalShardML.g:984:4: (otherlv_0= RULE_ID )
            // InternalShardML.g:985:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getWhereConditionRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_33); 

            					newLeafNode(otherlv_0, grammarAccess.getWhereConditionAccess().getFieldColumnCrossReference_0_0());
            				

            }


            }

            // InternalShardML.g:996:3: ( (lv_op_1_0= ruleOperator ) )
            // InternalShardML.g:997:4: (lv_op_1_0= ruleOperator )
            {
            // InternalShardML.g:997:4: (lv_op_1_0= ruleOperator )
            // InternalShardML.g:998:5: lv_op_1_0= ruleOperator
            {

            					newCompositeNode(grammarAccess.getWhereConditionAccess().getOpOperatorEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_34);
            lv_op_1_0=ruleOperator();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWhereConditionRule());
            					}
            					set(
            						current,
            						"op",
            						lv_op_1_0,
            						"uk.ac.kcl.inf.mdd1.ShardML.Operator");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,33,FOLLOW_2); 

            			newLeafNode(otherlv_2, grammarAccess.getWhereConditionAccess().getQuestionMarkKeyword_2());
            		

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
    // $ANTLR end "ruleWhereCondition"


    // $ANTLR start "entryRuleSortClause"
    // InternalShardML.g:1023:1: entryRuleSortClause returns [EObject current=null] : iv_ruleSortClause= ruleSortClause EOF ;
    public final EObject entryRuleSortClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSortClause = null;


        try {
            // InternalShardML.g:1023:51: (iv_ruleSortClause= ruleSortClause EOF )
            // InternalShardML.g:1024:2: iv_ruleSortClause= ruleSortClause EOF
            {
             newCompositeNode(grammarAccess.getSortClauseRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSortClause=ruleSortClause();

            state._fsp--;

             current =iv_ruleSortClause; 
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
    // $ANTLR end "entryRuleSortClause"


    // $ANTLR start "ruleSortClause"
    // InternalShardML.g:1030:1: ruleSortClause returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) ( (lv_order_1_0= ruleSortOrder ) ) ) ;
    public final EObject ruleSortClause() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Enumerator lv_order_1_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:1036:2: ( ( ( (otherlv_0= RULE_ID ) ) ( (lv_order_1_0= ruleSortOrder ) ) ) )
            // InternalShardML.g:1037:2: ( ( (otherlv_0= RULE_ID ) ) ( (lv_order_1_0= ruleSortOrder ) ) )
            {
            // InternalShardML.g:1037:2: ( ( (otherlv_0= RULE_ID ) ) ( (lv_order_1_0= ruleSortOrder ) ) )
            // InternalShardML.g:1038:3: ( (otherlv_0= RULE_ID ) ) ( (lv_order_1_0= ruleSortOrder ) )
            {
            // InternalShardML.g:1038:3: ( (otherlv_0= RULE_ID ) )
            // InternalShardML.g:1039:4: (otherlv_0= RULE_ID )
            {
            // InternalShardML.g:1039:4: (otherlv_0= RULE_ID )
            // InternalShardML.g:1040:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSortClauseRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_35); 

            					newLeafNode(otherlv_0, grammarAccess.getSortClauseAccess().getFieldColumnCrossReference_0_0());
            				

            }


            }

            // InternalShardML.g:1051:3: ( (lv_order_1_0= ruleSortOrder ) )
            // InternalShardML.g:1052:4: (lv_order_1_0= ruleSortOrder )
            {
            // InternalShardML.g:1052:4: (lv_order_1_0= ruleSortOrder )
            // InternalShardML.g:1053:5: lv_order_1_0= ruleSortOrder
            {

            					newCompositeNode(grammarAccess.getSortClauseAccess().getOrderSortOrderEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_2);
            lv_order_1_0=ruleSortOrder();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSortClauseRule());
            					}
            					set(
            						current,
            						"order",
            						lv_order_1_0,
            						"uk.ac.kcl.inf.mdd1.ShardML.SortOrder");
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
    // $ANTLR end "ruleSortClause"


    // $ANTLR start "entryRuleGroupBy"
    // InternalShardML.g:1074:1: entryRuleGroupBy returns [EObject current=null] : iv_ruleGroupBy= ruleGroupBy EOF ;
    public final EObject entryRuleGroupBy() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGroupBy = null;


        try {
            // InternalShardML.g:1074:48: (iv_ruleGroupBy= ruleGroupBy EOF )
            // InternalShardML.g:1075:2: iv_ruleGroupBy= ruleGroupBy EOF
            {
             newCompositeNode(grammarAccess.getGroupByRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGroupBy=ruleGroupBy();

            state._fsp--;

             current =iv_ruleGroupBy; 
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
    // $ANTLR end "entryRuleGroupBy"


    // $ANTLR start "ruleGroupBy"
    // InternalShardML.g:1081:1: ruleGroupBy returns [EObject current=null] : ( (otherlv_0= RULE_ID ) ) ;
    public final EObject ruleGroupBy() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;


        	enterRule();

        try {
            // InternalShardML.g:1087:2: ( ( (otherlv_0= RULE_ID ) ) )
            // InternalShardML.g:1088:2: ( (otherlv_0= RULE_ID ) )
            {
            // InternalShardML.g:1088:2: ( (otherlv_0= RULE_ID ) )
            // InternalShardML.g:1089:3: (otherlv_0= RULE_ID )
            {
            // InternalShardML.g:1089:3: (otherlv_0= RULE_ID )
            // InternalShardML.g:1090:4: otherlv_0= RULE_ID
            {

            				if (current==null) {
            					current = createModelElement(grammarAccess.getGroupByRule());
            				}
            			
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            				newLeafNode(otherlv_0, grammarAccess.getGroupByAccess().getFieldColumnCrossReference_0());
            			

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
    // $ANTLR end "ruleGroupBy"


    // $ANTLR start "ruleDatabaseType"
    // InternalShardML.g:1104:1: ruleDatabaseType returns [Enumerator current=null] : ( (enumLiteral_0= 'postgres' ) | (enumLiteral_1= 'mysql' ) ) ;
    public final Enumerator ruleDatabaseType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalShardML.g:1110:2: ( ( (enumLiteral_0= 'postgres' ) | (enumLiteral_1= 'mysql' ) ) )
            // InternalShardML.g:1111:2: ( (enumLiteral_0= 'postgres' ) | (enumLiteral_1= 'mysql' ) )
            {
            // InternalShardML.g:1111:2: ( (enumLiteral_0= 'postgres' ) | (enumLiteral_1= 'mysql' ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==34) ) {
                alt16=1;
            }
            else if ( (LA16_0==35) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalShardML.g:1112:3: (enumLiteral_0= 'postgres' )
                    {
                    // InternalShardML.g:1112:3: (enumLiteral_0= 'postgres' )
                    // InternalShardML.g:1113:4: enumLiteral_0= 'postgres'
                    {
                    enumLiteral_0=(Token)match(input,34,FOLLOW_2); 

                    				current = grammarAccess.getDatabaseTypeAccess().getPOSTGRESEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDatabaseTypeAccess().getPOSTGRESEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1120:3: (enumLiteral_1= 'mysql' )
                    {
                    // InternalShardML.g:1120:3: (enumLiteral_1= 'mysql' )
                    // InternalShardML.g:1121:4: enumLiteral_1= 'mysql'
                    {
                    enumLiteral_1=(Token)match(input,35,FOLLOW_2); 

                    				current = grammarAccess.getDatabaseTypeAccess().getMYSQLEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDatabaseTypeAccess().getMYSQLEnumLiteralDeclaration_1());
                    			

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
    // $ANTLR end "ruleDatabaseType"


    // $ANTLR start "ruleStrategy"
    // InternalShardML.g:1131:1: ruleStrategy returns [Enumerator current=null] : ( (enumLiteral_0= 'hash' ) | (enumLiteral_1= 'consistent_hash' ) | (enumLiteral_2= 'replicated' ) ) ;
    public final Enumerator ruleStrategy() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalShardML.g:1137:2: ( ( (enumLiteral_0= 'hash' ) | (enumLiteral_1= 'consistent_hash' ) | (enumLiteral_2= 'replicated' ) ) )
            // InternalShardML.g:1138:2: ( (enumLiteral_0= 'hash' ) | (enumLiteral_1= 'consistent_hash' ) | (enumLiteral_2= 'replicated' ) )
            {
            // InternalShardML.g:1138:2: ( (enumLiteral_0= 'hash' ) | (enumLiteral_1= 'consistent_hash' ) | (enumLiteral_2= 'replicated' ) )
            int alt17=3;
            switch ( input.LA(1) ) {
            case 36:
                {
                alt17=1;
                }
                break;
            case 37:
                {
                alt17=2;
                }
                break;
            case 38:
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
                    // InternalShardML.g:1139:3: (enumLiteral_0= 'hash' )
                    {
                    // InternalShardML.g:1139:3: (enumLiteral_0= 'hash' )
                    // InternalShardML.g:1140:4: enumLiteral_0= 'hash'
                    {
                    enumLiteral_0=(Token)match(input,36,FOLLOW_2); 

                    				current = grammarAccess.getStrategyAccess().getHASHEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getStrategyAccess().getHASHEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1147:3: (enumLiteral_1= 'consistent_hash' )
                    {
                    // InternalShardML.g:1147:3: (enumLiteral_1= 'consistent_hash' )
                    // InternalShardML.g:1148:4: enumLiteral_1= 'consistent_hash'
                    {
                    enumLiteral_1=(Token)match(input,37,FOLLOW_2); 

                    				current = grammarAccess.getStrategyAccess().getCONS_HASHEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getStrategyAccess().getCONS_HASHEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:1155:3: (enumLiteral_2= 'replicated' )
                    {
                    // InternalShardML.g:1155:3: (enumLiteral_2= 'replicated' )
                    // InternalShardML.g:1156:4: enumLiteral_2= 'replicated'
                    {
                    enumLiteral_2=(Token)match(input,38,FOLLOW_2); 

                    				current = grammarAccess.getStrategyAccess().getREPLICATEDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getStrategyAccess().getREPLICATEDEnumLiteralDeclaration_2());
                    			

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
    // $ANTLR end "ruleStrategy"


    // $ANTLR start "rulePolicy"
    // InternalShardML.g:1166:1: rulePolicy returns [Enumerator current=null] : ( (enumLiteral_0= 'allow' ) | (enumLiteral_1= 'warn' ) | (enumLiteral_2= 'deny' ) ) ;
    public final Enumerator rulePolicy() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalShardML.g:1172:2: ( ( (enumLiteral_0= 'allow' ) | (enumLiteral_1= 'warn' ) | (enumLiteral_2= 'deny' ) ) )
            // InternalShardML.g:1173:2: ( (enumLiteral_0= 'allow' ) | (enumLiteral_1= 'warn' ) | (enumLiteral_2= 'deny' ) )
            {
            // InternalShardML.g:1173:2: ( (enumLiteral_0= 'allow' ) | (enumLiteral_1= 'warn' ) | (enumLiteral_2= 'deny' ) )
            int alt18=3;
            switch ( input.LA(1) ) {
            case 39:
                {
                alt18=1;
                }
                break;
            case 40:
                {
                alt18=2;
                }
                break;
            case 41:
                {
                alt18=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalShardML.g:1174:3: (enumLiteral_0= 'allow' )
                    {
                    // InternalShardML.g:1174:3: (enumLiteral_0= 'allow' )
                    // InternalShardML.g:1175:4: enumLiteral_0= 'allow'
                    {
                    enumLiteral_0=(Token)match(input,39,FOLLOW_2); 

                    				current = grammarAccess.getPolicyAccess().getALLOWEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getPolicyAccess().getALLOWEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1182:3: (enumLiteral_1= 'warn' )
                    {
                    // InternalShardML.g:1182:3: (enumLiteral_1= 'warn' )
                    // InternalShardML.g:1183:4: enumLiteral_1= 'warn'
                    {
                    enumLiteral_1=(Token)match(input,40,FOLLOW_2); 

                    				current = grammarAccess.getPolicyAccess().getWARNEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getPolicyAccess().getWARNEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:1190:3: (enumLiteral_2= 'deny' )
                    {
                    // InternalShardML.g:1190:3: (enumLiteral_2= 'deny' )
                    // InternalShardML.g:1191:4: enumLiteral_2= 'deny'
                    {
                    enumLiteral_2=(Token)match(input,41,FOLLOW_2); 

                    				current = grammarAccess.getPolicyAccess().getDENYEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getPolicyAccess().getDENYEnumLiteralDeclaration_2());
                    			

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
    // $ANTLR end "rulePolicy"


    // $ANTLR start "ruleQueryType"
    // InternalShardML.g:1201:1: ruleQueryType returns [Enumerator current=null] : ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) ) ;
    public final Enumerator ruleQueryType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalShardML.g:1207:2: ( ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) ) )
            // InternalShardML.g:1208:2: ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) )
            {
            // InternalShardML.g:1208:2: ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) )
            int alt19=3;
            switch ( input.LA(1) ) {
            case 42:
                {
                alt19=1;
                }
                break;
            case 43:
                {
                alt19=2;
                }
                break;
            case 44:
                {
                alt19=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // InternalShardML.g:1209:3: (enumLiteral_0= 'read' )
                    {
                    // InternalShardML.g:1209:3: (enumLiteral_0= 'read' )
                    // InternalShardML.g:1210:4: enumLiteral_0= 'read'
                    {
                    enumLiteral_0=(Token)match(input,42,FOLLOW_2); 

                    				current = grammarAccess.getQueryTypeAccess().getREADEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getQueryTypeAccess().getREADEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1217:3: (enumLiteral_1= 'write' )
                    {
                    // InternalShardML.g:1217:3: (enumLiteral_1= 'write' )
                    // InternalShardML.g:1218:4: enumLiteral_1= 'write'
                    {
                    enumLiteral_1=(Token)match(input,43,FOLLOW_2); 

                    				current = grammarAccess.getQueryTypeAccess().getWRITEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getQueryTypeAccess().getWRITEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:1225:3: (enumLiteral_2= 'delete' )
                    {
                    // InternalShardML.g:1225:3: (enumLiteral_2= 'delete' )
                    // InternalShardML.g:1226:4: enumLiteral_2= 'delete'
                    {
                    enumLiteral_2=(Token)match(input,44,FOLLOW_2); 

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
    // InternalShardML.g:1236:1: ruleOperator returns [Enumerator current=null] : ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) ) ;
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
            // InternalShardML.g:1242:2: ( ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) ) )
            // InternalShardML.g:1243:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) )
            {
            // InternalShardML.g:1243:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) )
            int alt20=6;
            switch ( input.LA(1) ) {
            case 45:
                {
                alt20=1;
                }
                break;
            case 46:
                {
                alt20=2;
                }
                break;
            case 47:
                {
                alt20=3;
                }
                break;
            case 48:
                {
                alt20=4;
                }
                break;
            case 49:
                {
                alt20=5;
                }
                break;
            case 50:
                {
                alt20=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // InternalShardML.g:1244:3: (enumLiteral_0= '=' )
                    {
                    // InternalShardML.g:1244:3: (enumLiteral_0= '=' )
                    // InternalShardML.g:1245:4: enumLiteral_0= '='
                    {
                    enumLiteral_0=(Token)match(input,45,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getEQEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getOperatorAccess().getEQEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1252:3: (enumLiteral_1= '>' )
                    {
                    // InternalShardML.g:1252:3: (enumLiteral_1= '>' )
                    // InternalShardML.g:1253:4: enumLiteral_1= '>'
                    {
                    enumLiteral_1=(Token)match(input,46,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getGEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getOperatorAccess().getGEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:1260:3: (enumLiteral_2= '<' )
                    {
                    // InternalShardML.g:1260:3: (enumLiteral_2= '<' )
                    // InternalShardML.g:1261:4: enumLiteral_2= '<'
                    {
                    enumLiteral_2=(Token)match(input,47,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getLEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getOperatorAccess().getLEEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalShardML.g:1268:3: (enumLiteral_3= '<=' )
                    {
                    // InternalShardML.g:1268:3: (enumLiteral_3= '<=' )
                    // InternalShardML.g:1269:4: enumLiteral_3= '<='
                    {
                    enumLiteral_3=(Token)match(input,48,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getLEQEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getOperatorAccess().getLEQEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalShardML.g:1276:3: (enumLiteral_4= '>=' )
                    {
                    // InternalShardML.g:1276:3: (enumLiteral_4= '>=' )
                    // InternalShardML.g:1277:4: enumLiteral_4= '>='
                    {
                    enumLiteral_4=(Token)match(input,49,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getGEQEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getOperatorAccess().getGEQEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalShardML.g:1284:3: (enumLiteral_5= '!=' )
                    {
                    // InternalShardML.g:1284:3: (enumLiteral_5= '!=' )
                    // InternalShardML.g:1285:4: enumLiteral_5= '!='
                    {
                    enumLiteral_5=(Token)match(input,50,FOLLOW_2); 

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
    // InternalShardML.g:1295:1: ruleLink returns [Enumerator current=null] : ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) ) ;
    public final Enumerator ruleLink() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalShardML.g:1301:2: ( ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) ) )
            // InternalShardML.g:1302:2: ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) )
            {
            // InternalShardML.g:1302:2: ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==51) ) {
                alt21=1;
            }
            else if ( (LA21_0==52) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // InternalShardML.g:1303:3: (enumLiteral_0= 'AND' )
                    {
                    // InternalShardML.g:1303:3: (enumLiteral_0= 'AND' )
                    // InternalShardML.g:1304:4: enumLiteral_0= 'AND'
                    {
                    enumLiteral_0=(Token)match(input,51,FOLLOW_2); 

                    				current = grammarAccess.getLinkAccess().getANDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getLinkAccess().getANDEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1311:3: (enumLiteral_1= 'OR' )
                    {
                    // InternalShardML.g:1311:3: (enumLiteral_1= 'OR' )
                    // InternalShardML.g:1312:4: enumLiteral_1= 'OR'
                    {
                    enumLiteral_1=(Token)match(input,52,FOLLOW_2); 

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


    // $ANTLR start "ruleSortOrder"
    // InternalShardML.g:1322:1: ruleSortOrder returns [Enumerator current=null] : ( (enumLiteral_0= 'ASC' ) | (enumLiteral_1= 'DESC' ) ) ;
    public final Enumerator ruleSortOrder() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalShardML.g:1328:2: ( ( (enumLiteral_0= 'ASC' ) | (enumLiteral_1= 'DESC' ) ) )
            // InternalShardML.g:1329:2: ( (enumLiteral_0= 'ASC' ) | (enumLiteral_1= 'DESC' ) )
            {
            // InternalShardML.g:1329:2: ( (enumLiteral_0= 'ASC' ) | (enumLiteral_1= 'DESC' ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==53) ) {
                alt22=1;
            }
            else if ( (LA22_0==54) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalShardML.g:1330:3: (enumLiteral_0= 'ASC' )
                    {
                    // InternalShardML.g:1330:3: (enumLiteral_0= 'ASC' )
                    // InternalShardML.g:1331:4: enumLiteral_0= 'ASC'
                    {
                    enumLiteral_0=(Token)match(input,53,FOLLOW_2); 

                    				current = grammarAccess.getSortOrderAccess().getASCEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getSortOrderAccess().getASCEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1338:3: (enumLiteral_1= 'DESC' )
                    {
                    // InternalShardML.g:1338:3: (enumLiteral_1= 'DESC' )
                    // InternalShardML.g:1339:4: enumLiteral_1= 'DESC'
                    {
                    enumLiteral_1=(Token)match(input,54,FOLLOW_2); 

                    				current = grammarAccess.getSortOrderAccess().getDESCEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getSortOrderAccess().getDESCEnumLiteralDeclaration_1());
                    			

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
    // $ANTLR end "ruleSortOrder"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000C00000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000001088020L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000001008020L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000008020L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000007000000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000E08000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000C08000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000808000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000038000000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x000000000C008000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000008008000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00001C0000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x00000000F0008000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x00000000E0008000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x00000000C0008000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000080008000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0018000000000002L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0007E00000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0060000000000000L});

}