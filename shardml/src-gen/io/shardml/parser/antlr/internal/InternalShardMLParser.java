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
import io.shardml.services.ShardMLGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalShardMLParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_ID", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'import'", "'database'", "'{'", "'type:'", "'}'", "'node'", "'place'", "'spread'", "'pin'", "'host:'", "'port:'", "'strategy:'", "'nodes:'", "','", "'has_many'", "'has_one'", "'belongs_to'", "'via'", "'shard'", "'key:'", "'buckets:'", "'colocate_with:'", "'route'", "'policy:'", "'max_conditions:'", "'query'", "'where:'", "'sort:'", "'group_by:'", "'distinct:'", "'true'", "'?'", "'postgres'", "'mysql'", "'hash'", "'consistent_hash'", "'replicated'", "'allow'", "'warn'", "'deny'", "'read'", "'write'", "'delete'", "'='", "'>'", "'<'", "'<='", "'>='", "'!='", "'AND'", "'OR'", "'ASC'", "'DESC'"
    };
    public static final int T__50=50;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__59=59;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__55=55;
    public static final int T__12=12;
    public static final int T__56=56;
    public static final int T__13=13;
    public static final int T__57=57;
    public static final int T__14=14;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
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
    public static final int T__62=62;
    public static final int T__63=63;
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
            	    						"io.shardml.ShardML.Import");
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
            						"io.shardml.ShardML.Database");
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
    // InternalShardML.g:170:1: ruleDatabase returns [EObject current=null] : (otherlv_0= 'database' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleDatabaseType ) ) ( (lv_nodes_5_0= ruleNode ) )* ( (lv_shard_decl_6_0= ruleShardDecl ) )* ( (lv_placements_7_0= rulePlaceDecl ) )* ( (lv_route_rules_8_0= ruleRouteRule ) )* ( (lv_relationships_9_0= ruleRelationship ) )* otherlv_10= '}' ) ;
    public final EObject ruleDatabase() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_10=null;
        Enumerator lv_type_4_0 = null;

        EObject lv_nodes_5_0 = null;

        EObject lv_shard_decl_6_0 = null;

        EObject lv_placements_7_0 = null;

        EObject lv_route_rules_8_0 = null;

        EObject lv_relationships_9_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:176:2: ( (otherlv_0= 'database' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleDatabaseType ) ) ( (lv_nodes_5_0= ruleNode ) )* ( (lv_shard_decl_6_0= ruleShardDecl ) )* ( (lv_placements_7_0= rulePlaceDecl ) )* ( (lv_route_rules_8_0= ruleRouteRule ) )* ( (lv_relationships_9_0= ruleRelationship ) )* otherlv_10= '}' ) )
            // InternalShardML.g:177:2: (otherlv_0= 'database' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleDatabaseType ) ) ( (lv_nodes_5_0= ruleNode ) )* ( (lv_shard_decl_6_0= ruleShardDecl ) )* ( (lv_placements_7_0= rulePlaceDecl ) )* ( (lv_route_rules_8_0= ruleRouteRule ) )* ( (lv_relationships_9_0= ruleRelationship ) )* otherlv_10= '}' )
            {
            // InternalShardML.g:177:2: (otherlv_0= 'database' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleDatabaseType ) ) ( (lv_nodes_5_0= ruleNode ) )* ( (lv_shard_decl_6_0= ruleShardDecl ) )* ( (lv_placements_7_0= rulePlaceDecl ) )* ( (lv_route_rules_8_0= ruleRouteRule ) )* ( (lv_relationships_9_0= ruleRelationship ) )* otherlv_10= '}' )
            // InternalShardML.g:178:3: otherlv_0= 'database' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_type_4_0= ruleDatabaseType ) ) ( (lv_nodes_5_0= ruleNode ) )* ( (lv_shard_decl_6_0= ruleShardDecl ) )* ( (lv_placements_7_0= rulePlaceDecl ) )* ( (lv_route_rules_8_0= ruleRouteRule ) )* ( (lv_relationships_9_0= ruleRelationship ) )* otherlv_10= '}'
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
            						"io.shardml.ShardML.DatabaseType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:227:3: ( (lv_nodes_5_0= ruleNode ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==16) ) {
                    int LA2_2 = input.LA(2);

                    if ( (LA2_2==RULE_ID||(LA2_2>=16 && LA2_2<=19)) ) {
                        alt2=1;
                    }


                }


                switch (alt2) {
            	case 1 :
            	    // InternalShardML.g:228:4: (lv_nodes_5_0= ruleNode )
            	    {
            	    // InternalShardML.g:228:4: (lv_nodes_5_0= ruleNode )
            	    // InternalShardML.g:229:5: lv_nodes_5_0= ruleNode
            	    {

            	    					newCompositeNode(grammarAccess.getDatabaseAccess().getNodesNodeParserRuleCall_5_0());
            	    				
            	    pushFollow(FOLLOW_9);
            	    lv_nodes_5_0=ruleNode();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDatabaseRule());
            	    					}
            	    					add(
            	    						current,
            	    						"nodes",
            	    						lv_nodes_5_0,
            	    						"io.shardml.ShardML.Node");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // InternalShardML.g:246:3: ( (lv_shard_decl_6_0= ruleShardDecl ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==29) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalShardML.g:247:4: (lv_shard_decl_6_0= ruleShardDecl )
            	    {
            	    // InternalShardML.g:247:4: (lv_shard_decl_6_0= ruleShardDecl )
            	    // InternalShardML.g:248:5: lv_shard_decl_6_0= ruleShardDecl
            	    {

            	    					newCompositeNode(grammarAccess.getDatabaseAccess().getShard_declShardDeclParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_9);
            	    lv_shard_decl_6_0=ruleShardDecl();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDatabaseRule());
            	    					}
            	    					add(
            	    						current,
            	    						"shard_decl",
            	    						lv_shard_decl_6_0,
            	    						"io.shardml.ShardML.ShardDecl");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // InternalShardML.g:265:3: ( (lv_placements_7_0= rulePlaceDecl ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==17) ) {
                    int LA4_2 = input.LA(2);

                    if ( (LA4_2==RULE_ID||(LA4_2>=16 && LA4_2<=19)) ) {
                        alt4=1;
                    }


                }


                switch (alt4) {
            	case 1 :
            	    // InternalShardML.g:266:4: (lv_placements_7_0= rulePlaceDecl )
            	    {
            	    // InternalShardML.g:266:4: (lv_placements_7_0= rulePlaceDecl )
            	    // InternalShardML.g:267:5: lv_placements_7_0= rulePlaceDecl
            	    {

            	    					newCompositeNode(grammarAccess.getDatabaseAccess().getPlacementsPlaceDeclParserRuleCall_7_0());
            	    				
            	    pushFollow(FOLLOW_10);
            	    lv_placements_7_0=rulePlaceDecl();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDatabaseRule());
            	    					}
            	    					add(
            	    						current,
            	    						"placements",
            	    						lv_placements_7_0,
            	    						"io.shardml.ShardML.PlaceDecl");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            // InternalShardML.g:284:3: ( (lv_route_rules_8_0= ruleRouteRule ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==33) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalShardML.g:285:4: (lv_route_rules_8_0= ruleRouteRule )
            	    {
            	    // InternalShardML.g:285:4: (lv_route_rules_8_0= ruleRouteRule )
            	    // InternalShardML.g:286:5: lv_route_rules_8_0= ruleRouteRule
            	    {

            	    					newCompositeNode(grammarAccess.getDatabaseAccess().getRoute_rulesRouteRuleParserRuleCall_8_0());
            	    				
            	    pushFollow(FOLLOW_10);
            	    lv_route_rules_8_0=ruleRouteRule();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDatabaseRule());
            	    					}
            	    					add(
            	    						current,
            	    						"route_rules",
            	    						lv_route_rules_8_0,
            	    						"io.shardml.ShardML.RouteRule");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // InternalShardML.g:303:3: ( (lv_relationships_9_0= ruleRelationship ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==RULE_ID||(LA6_0>=16 && LA6_0<=19)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalShardML.g:304:4: (lv_relationships_9_0= ruleRelationship )
            	    {
            	    // InternalShardML.g:304:4: (lv_relationships_9_0= ruleRelationship )
            	    // InternalShardML.g:305:5: lv_relationships_9_0= ruleRelationship
            	    {

            	    					newCompositeNode(grammarAccess.getDatabaseAccess().getRelationshipsRelationshipParserRuleCall_9_0());
            	    				
            	    pushFollow(FOLLOW_11);
            	    lv_relationships_9_0=ruleRelationship();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getDatabaseRule());
            	    					}
            	    					add(
            	    						current,
            	    						"relationships",
            	    						lv_relationships_9_0,
            	    						"io.shardml.ShardML.Relationship");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            otherlv_10=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_10, grammarAccess.getDatabaseAccess().getRightCurlyBracketKeyword_10());
            		

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


    // $ANTLR start "entryRuleValidID"
    // InternalShardML.g:330:1: entryRuleValidID returns [String current=null] : iv_ruleValidID= ruleValidID EOF ;
    public final String entryRuleValidID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleValidID = null;


        try {
            // InternalShardML.g:330:47: (iv_ruleValidID= ruleValidID EOF )
            // InternalShardML.g:331:2: iv_ruleValidID= ruleValidID EOF
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
    // InternalShardML.g:337:1: ruleValidID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID | kw= 'node' | kw= 'place' | kw= 'spread' | kw= 'pin' ) ;
    public final AntlrDatatypeRuleToken ruleValidID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalShardML.g:343:2: ( (this_ID_0= RULE_ID | kw= 'node' | kw= 'place' | kw= 'spread' | kw= 'pin' ) )
            // InternalShardML.g:344:2: (this_ID_0= RULE_ID | kw= 'node' | kw= 'place' | kw= 'spread' | kw= 'pin' )
            {
            // InternalShardML.g:344:2: (this_ID_0= RULE_ID | kw= 'node' | kw= 'place' | kw= 'spread' | kw= 'pin' )
            int alt7=5;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt7=1;
                }
                break;
            case 16:
                {
                alt7=2;
                }
                break;
            case 17:
                {
                alt7=3;
                }
                break;
            case 18:
                {
                alt7=4;
                }
                break;
            case 19:
                {
                alt7=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // InternalShardML.g:345:3: this_ID_0= RULE_ID
                    {
                    this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    			current.merge(this_ID_0);
                    		

                    			newLeafNode(this_ID_0, grammarAccess.getValidIDAccess().getIDTerminalRuleCall_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalShardML.g:353:3: kw= 'node'
                    {
                    kw=(Token)match(input,16,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getNodeKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalShardML.g:359:3: kw= 'place'
                    {
                    kw=(Token)match(input,17,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPlaceKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalShardML.g:365:3: kw= 'spread'
                    {
                    kw=(Token)match(input,18,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getSpreadKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalShardML.g:371:3: kw= 'pin'
                    {
                    kw=(Token)match(input,19,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getValidIDAccess().getPinKeyword_4());
                    		

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


    // $ANTLR start "entryRuleNode"
    // InternalShardML.g:380:1: entryRuleNode returns [EObject current=null] : iv_ruleNode= ruleNode EOF ;
    public final EObject entryRuleNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNode = null;


        try {
            // InternalShardML.g:380:45: (iv_ruleNode= ruleNode EOF )
            // InternalShardML.g:381:2: iv_ruleNode= ruleNode EOF
            {
             newCompositeNode(grammarAccess.getNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNode=ruleNode();

            state._fsp--;

             current =iv_ruleNode; 
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
    // $ANTLR end "entryRuleNode"


    // $ANTLR start "ruleNode"
    // InternalShardML.g:387:1: ruleNode returns [EObject current=null] : (otherlv_0= 'node' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'host:' ( (lv_host_4_0= RULE_STRING ) ) otherlv_5= 'port:' ( (lv_port_6_0= RULE_INT ) ) otherlv_7= '}' ) ;
    public final EObject ruleNode() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_host_4_0=null;
        Token otherlv_5=null;
        Token lv_port_6_0=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:393:2: ( (otherlv_0= 'node' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'host:' ( (lv_host_4_0= RULE_STRING ) ) otherlv_5= 'port:' ( (lv_port_6_0= RULE_INT ) ) otherlv_7= '}' ) )
            // InternalShardML.g:394:2: (otherlv_0= 'node' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'host:' ( (lv_host_4_0= RULE_STRING ) ) otherlv_5= 'port:' ( (lv_port_6_0= RULE_INT ) ) otherlv_7= '}' )
            {
            // InternalShardML.g:394:2: (otherlv_0= 'node' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'host:' ( (lv_host_4_0= RULE_STRING ) ) otherlv_5= 'port:' ( (lv_port_6_0= RULE_INT ) ) otherlv_7= '}' )
            // InternalShardML.g:395:3: otherlv_0= 'node' ( (lv_name_1_0= ruleValidID ) ) otherlv_2= '{' otherlv_3= 'host:' ( (lv_host_4_0= RULE_STRING ) ) otherlv_5= 'port:' ( (lv_port_6_0= RULE_INT ) ) otherlv_7= '}'
            {
            otherlv_0=(Token)match(input,16,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getNodeAccess().getNodeKeyword_0());
            		
            // InternalShardML.g:399:3: ( (lv_name_1_0= ruleValidID ) )
            // InternalShardML.g:400:4: (lv_name_1_0= ruleValidID )
            {
            // InternalShardML.g:400:4: (lv_name_1_0= ruleValidID )
            // InternalShardML.g:401:5: lv_name_1_0= ruleValidID
            {

            					newCompositeNode(grammarAccess.getNodeAccess().getNameValidIDParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_6);
            lv_name_1_0=ruleValidID();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getNodeRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"io.shardml.ShardML.ValidID");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_13); 

            			newLeafNode(otherlv_2, grammarAccess.getNodeAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,20,FOLLOW_4); 

            			newLeafNode(otherlv_3, grammarAccess.getNodeAccess().getHostKeyword_3());
            		
            // InternalShardML.g:426:3: ( (lv_host_4_0= RULE_STRING ) )
            // InternalShardML.g:427:4: (lv_host_4_0= RULE_STRING )
            {
            // InternalShardML.g:427:4: (lv_host_4_0= RULE_STRING )
            // InternalShardML.g:428:5: lv_host_4_0= RULE_STRING
            {
            lv_host_4_0=(Token)match(input,RULE_STRING,FOLLOW_14); 

            					newLeafNode(lv_host_4_0, grammarAccess.getNodeAccess().getHostSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodeRule());
            					}
            					setWithLastConsumed(
            						current,
            						"host",
            						lv_host_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,21,FOLLOW_15); 

            			newLeafNode(otherlv_5, grammarAccess.getNodeAccess().getPortKeyword_5());
            		
            // InternalShardML.g:448:3: ( (lv_port_6_0= RULE_INT ) )
            // InternalShardML.g:449:4: (lv_port_6_0= RULE_INT )
            {
            // InternalShardML.g:449:4: (lv_port_6_0= RULE_INT )
            // InternalShardML.g:450:5: lv_port_6_0= RULE_INT
            {
            lv_port_6_0=(Token)match(input,RULE_INT,FOLLOW_16); 

            					newLeafNode(lv_port_6_0, grammarAccess.getNodeAccess().getPortINTTerminalRuleCall_6_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodeRule());
            					}
            					setWithLastConsumed(
            						current,
            						"port",
            						lv_port_6_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_7=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_7, grammarAccess.getNodeAccess().getRightCurlyBracketKeyword_7());
            		

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
    // $ANTLR end "ruleNode"


    // $ANTLR start "entryRulePlaceDecl"
    // InternalShardML.g:474:1: entryRulePlaceDecl returns [EObject current=null] : iv_rulePlaceDecl= rulePlaceDecl EOF ;
    public final EObject entryRulePlaceDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePlaceDecl = null;


        try {
            // InternalShardML.g:474:50: (iv_rulePlaceDecl= rulePlaceDecl EOF )
            // InternalShardML.g:475:2: iv_rulePlaceDecl= rulePlaceDecl EOF
            {
             newCompositeNode(grammarAccess.getPlaceDeclRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePlaceDecl=rulePlaceDecl();

            state._fsp--;

             current =iv_rulePlaceDecl; 
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
    // $ANTLR end "entryRulePlaceDecl"


    // $ANTLR start "rulePlaceDecl"
    // InternalShardML.g:481:1: rulePlaceDecl returns [EObject current=null] : (otherlv_0= 'place' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= rulePlacementStrategy ) ) (otherlv_5= 'nodes:' ( ( ruleValidID ) ) (otherlv_7= ',' ( ( ruleValidID ) ) )* )? otherlv_9= '}' ) ;
    public final EObject rulePlaceDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Enumerator lv_strategy_4_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:487:2: ( (otherlv_0= 'place' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= rulePlacementStrategy ) ) (otherlv_5= 'nodes:' ( ( ruleValidID ) ) (otherlv_7= ',' ( ( ruleValidID ) ) )* )? otherlv_9= '}' ) )
            // InternalShardML.g:488:2: (otherlv_0= 'place' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= rulePlacementStrategy ) ) (otherlv_5= 'nodes:' ( ( ruleValidID ) ) (otherlv_7= ',' ( ( ruleValidID ) ) )* )? otherlv_9= '}' )
            {
            // InternalShardML.g:488:2: (otherlv_0= 'place' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= rulePlacementStrategy ) ) (otherlv_5= 'nodes:' ( ( ruleValidID ) ) (otherlv_7= ',' ( ( ruleValidID ) ) )* )? otherlv_9= '}' )
            // InternalShardML.g:489:3: otherlv_0= 'place' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= rulePlacementStrategy ) ) (otherlv_5= 'nodes:' ( ( ruleValidID ) ) (otherlv_7= ',' ( ( ruleValidID ) ) )* )? otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getPlaceDeclAccess().getPlaceKeyword_0());
            		
            // InternalShardML.g:493:3: ( ( ruleValidID ) )
            // InternalShardML.g:494:4: ( ruleValidID )
            {
            // InternalShardML.g:494:4: ( ruleValidID )
            // InternalShardML.g:495:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPlaceDeclRule());
            					}
            				

            					newCompositeNode(grammarAccess.getPlaceDeclAccess().getEntityTableCrossReference_1_0());
            				
            pushFollow(FOLLOW_6);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_17); 

            			newLeafNode(otherlv_2, grammarAccess.getPlaceDeclAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,22,FOLLOW_18); 

            			newLeafNode(otherlv_3, grammarAccess.getPlaceDeclAccess().getStrategyKeyword_3());
            		
            // InternalShardML.g:517:3: ( (lv_strategy_4_0= rulePlacementStrategy ) )
            // InternalShardML.g:518:4: (lv_strategy_4_0= rulePlacementStrategy )
            {
            // InternalShardML.g:518:4: (lv_strategy_4_0= rulePlacementStrategy )
            // InternalShardML.g:519:5: lv_strategy_4_0= rulePlacementStrategy
            {

            					newCompositeNode(grammarAccess.getPlaceDeclAccess().getStrategyPlacementStrategyEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_19);
            lv_strategy_4_0=rulePlacementStrategy();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPlaceDeclRule());
            					}
            					set(
            						current,
            						"strategy",
            						lv_strategy_4_0,
            						"io.shardml.ShardML.PlacementStrategy");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:536:3: (otherlv_5= 'nodes:' ( ( ruleValidID ) ) (otherlv_7= ',' ( ( ruleValidID ) ) )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==23) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalShardML.g:537:4: otherlv_5= 'nodes:' ( ( ruleValidID ) ) (otherlv_7= ',' ( ( ruleValidID ) ) )*
                    {
                    otherlv_5=(Token)match(input,23,FOLLOW_12); 

                    				newLeafNode(otherlv_5, grammarAccess.getPlaceDeclAccess().getNodesKeyword_5_0());
                    			
                    // InternalShardML.g:541:4: ( ( ruleValidID ) )
                    // InternalShardML.g:542:5: ( ruleValidID )
                    {
                    // InternalShardML.g:542:5: ( ruleValidID )
                    // InternalShardML.g:543:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getPlaceDeclRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getPlaceDeclAccess().getNodesNodeCrossReference_5_1_0());
                    					
                    pushFollow(FOLLOW_20);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalShardML.g:557:4: (otherlv_7= ',' ( ( ruleValidID ) ) )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==24) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // InternalShardML.g:558:5: otherlv_7= ',' ( ( ruleValidID ) )
                    	    {
                    	    otherlv_7=(Token)match(input,24,FOLLOW_12); 

                    	    					newLeafNode(otherlv_7, grammarAccess.getPlaceDeclAccess().getCommaKeyword_5_2_0());
                    	    				
                    	    // InternalShardML.g:562:5: ( ( ruleValidID ) )
                    	    // InternalShardML.g:563:6: ( ruleValidID )
                    	    {
                    	    // InternalShardML.g:563:6: ( ruleValidID )
                    	    // InternalShardML.g:564:7: ruleValidID
                    	    {

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getPlaceDeclRule());
                    	    							}
                    	    						

                    	    							newCompositeNode(grammarAccess.getPlaceDeclAccess().getNodesNodeCrossReference_5_2_1_0());
                    	    						
                    	    pushFollow(FOLLOW_20);
                    	    ruleValidID();

                    	    state._fsp--;


                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;

            }

            otherlv_9=(Token)match(input,15,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getPlaceDeclAccess().getRightCurlyBracketKeyword_6());
            		

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
    // $ANTLR end "rulePlaceDecl"


    // $ANTLR start "entryRuleRelationship"
    // InternalShardML.g:588:1: entryRuleRelationship returns [EObject current=null] : iv_ruleRelationship= ruleRelationship EOF ;
    public final EObject entryRuleRelationship() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRelationship = null;


        try {
            // InternalShardML.g:588:53: (iv_ruleRelationship= ruleRelationship EOF )
            // InternalShardML.g:589:2: iv_ruleRelationship= ruleRelationship EOF
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
    // InternalShardML.g:595:1: ruleRelationship returns [EObject current=null] : (this_HasMany_0= ruleHasMany | this_HasOne_1= ruleHasOne | this_BelongsTo_2= ruleBelongsTo ) ;
    public final EObject ruleRelationship() throws RecognitionException {
        EObject current = null;

        EObject this_HasMany_0 = null;

        EObject this_HasOne_1 = null;

        EObject this_BelongsTo_2 = null;



        	enterRule();

        try {
            // InternalShardML.g:601:2: ( (this_HasMany_0= ruleHasMany | this_HasOne_1= ruleHasOne | this_BelongsTo_2= ruleBelongsTo ) )
            // InternalShardML.g:602:2: (this_HasMany_0= ruleHasMany | this_HasOne_1= ruleHasOne | this_BelongsTo_2= ruleBelongsTo )
            {
            // InternalShardML.g:602:2: (this_HasMany_0= ruleHasMany | this_HasOne_1= ruleHasOne | this_BelongsTo_2= ruleBelongsTo )
            int alt10=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                switch ( input.LA(2) ) {
                case 25:
                    {
                    alt10=1;
                    }
                    break;
                case 27:
                    {
                    alt10=3;
                    }
                    break;
                case 26:
                    {
                    alt10=2;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }

                }
                break;
            case 16:
                {
                switch ( input.LA(2) ) {
                case 27:
                    {
                    alt10=3;
                    }
                    break;
                case 26:
                    {
                    alt10=2;
                    }
                    break;
                case 25:
                    {
                    alt10=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 2, input);

                    throw nvae;
                }

                }
                break;
            case 17:
                {
                switch ( input.LA(2) ) {
                case 27:
                    {
                    alt10=3;
                    }
                    break;
                case 26:
                    {
                    alt10=2;
                    }
                    break;
                case 25:
                    {
                    alt10=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 3, input);

                    throw nvae;
                }

                }
                break;
            case 18:
                {
                switch ( input.LA(2) ) {
                case 27:
                    {
                    alt10=3;
                    }
                    break;
                case 26:
                    {
                    alt10=2;
                    }
                    break;
                case 25:
                    {
                    alt10=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 4, input);

                    throw nvae;
                }

                }
                break;
            case 19:
                {
                switch ( input.LA(2) ) {
                case 26:
                    {
                    alt10=2;
                    }
                    break;
                case 25:
                    {
                    alt10=1;
                    }
                    break;
                case 27:
                    {
                    alt10=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 5, input);

                    throw nvae;
                }

                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalShardML.g:603:3: this_HasMany_0= ruleHasMany
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
                    // InternalShardML.g:612:3: this_HasOne_1= ruleHasOne
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
                    // InternalShardML.g:621:3: this_BelongsTo_2= ruleBelongsTo
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
    // InternalShardML.g:633:1: entryRuleHasMany returns [EObject current=null] : iv_ruleHasMany= ruleHasMany EOF ;
    public final EObject entryRuleHasMany() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHasMany = null;


        try {
            // InternalShardML.g:633:48: (iv_ruleHasMany= ruleHasMany EOF )
            // InternalShardML.g:634:2: iv_ruleHasMany= ruleHasMany EOF
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
    // InternalShardML.g:640:1: ruleHasMany returns [EObject current=null] : ( ( ( ruleValidID ) ) otherlv_1= 'has_many' ( ( ruleValidID ) ) ) ;
    public final EObject ruleHasMany() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalShardML.g:646:2: ( ( ( ( ruleValidID ) ) otherlv_1= 'has_many' ( ( ruleValidID ) ) ) )
            // InternalShardML.g:647:2: ( ( ( ruleValidID ) ) otherlv_1= 'has_many' ( ( ruleValidID ) ) )
            {
            // InternalShardML.g:647:2: ( ( ( ruleValidID ) ) otherlv_1= 'has_many' ( ( ruleValidID ) ) )
            // InternalShardML.g:648:3: ( ( ruleValidID ) ) otherlv_1= 'has_many' ( ( ruleValidID ) )
            {
            // InternalShardML.g:648:3: ( ( ruleValidID ) )
            // InternalShardML.g:649:4: ( ruleValidID )
            {
            // InternalShardML.g:649:4: ( ruleValidID )
            // InternalShardML.g:650:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getHasManyRule());
            					}
            				

            					newCompositeNode(grammarAccess.getHasManyAccess().getSubjectTableCrossReference_0_0());
            				
            pushFollow(FOLLOW_21);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,25,FOLLOW_12); 

            			newLeafNode(otherlv_1, grammarAccess.getHasManyAccess().getHas_manyKeyword_1());
            		
            // InternalShardML.g:668:3: ( ( ruleValidID ) )
            // InternalShardML.g:669:4: ( ruleValidID )
            {
            // InternalShardML.g:669:4: ( ruleValidID )
            // InternalShardML.g:670:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getHasManyRule());
            					}
            				

            					newCompositeNode(grammarAccess.getHasManyAccess().getManyTableCrossReference_2_0());
            				
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
    // $ANTLR end "ruleHasMany"


    // $ANTLR start "entryRuleHasOne"
    // InternalShardML.g:688:1: entryRuleHasOne returns [EObject current=null] : iv_ruleHasOne= ruleHasOne EOF ;
    public final EObject entryRuleHasOne() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHasOne = null;


        try {
            // InternalShardML.g:688:47: (iv_ruleHasOne= ruleHasOne EOF )
            // InternalShardML.g:689:2: iv_ruleHasOne= ruleHasOne EOF
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
    // InternalShardML.g:695:1: ruleHasOne returns [EObject current=null] : ( ( ( ruleValidID ) ) otherlv_1= 'has_one' ( ( ruleValidID ) ) ) ;
    public final EObject ruleHasOne() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalShardML.g:701:2: ( ( ( ( ruleValidID ) ) otherlv_1= 'has_one' ( ( ruleValidID ) ) ) )
            // InternalShardML.g:702:2: ( ( ( ruleValidID ) ) otherlv_1= 'has_one' ( ( ruleValidID ) ) )
            {
            // InternalShardML.g:702:2: ( ( ( ruleValidID ) ) otherlv_1= 'has_one' ( ( ruleValidID ) ) )
            // InternalShardML.g:703:3: ( ( ruleValidID ) ) otherlv_1= 'has_one' ( ( ruleValidID ) )
            {
            // InternalShardML.g:703:3: ( ( ruleValidID ) )
            // InternalShardML.g:704:4: ( ruleValidID )
            {
            // InternalShardML.g:704:4: ( ruleValidID )
            // InternalShardML.g:705:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getHasOneRule());
            					}
            				

            					newCompositeNode(grammarAccess.getHasOneAccess().getSubjectTableCrossReference_0_0());
            				
            pushFollow(FOLLOW_22);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,26,FOLLOW_12); 

            			newLeafNode(otherlv_1, grammarAccess.getHasOneAccess().getHas_oneKeyword_1());
            		
            // InternalShardML.g:723:3: ( ( ruleValidID ) )
            // InternalShardML.g:724:4: ( ruleValidID )
            {
            // InternalShardML.g:724:4: ( ruleValidID )
            // InternalShardML.g:725:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getHasOneRule());
            					}
            				

            					newCompositeNode(grammarAccess.getHasOneAccess().getOneTableCrossReference_2_0());
            				
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
    // $ANTLR end "ruleHasOne"


    // $ANTLR start "entryRuleBelongsTo"
    // InternalShardML.g:743:1: entryRuleBelongsTo returns [EObject current=null] : iv_ruleBelongsTo= ruleBelongsTo EOF ;
    public final EObject entryRuleBelongsTo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBelongsTo = null;


        try {
            // InternalShardML.g:743:50: (iv_ruleBelongsTo= ruleBelongsTo EOF )
            // InternalShardML.g:744:2: iv_ruleBelongsTo= ruleBelongsTo EOF
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
    // InternalShardML.g:750:1: ruleBelongsTo returns [EObject current=null] : ( ( ( ruleValidID ) ) otherlv_1= 'belongs_to' ( ( ruleValidID ) ) otherlv_3= 'via' ( ( ruleValidID ) ) ) ;
    public final EObject ruleBelongsTo() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;


        	enterRule();

        try {
            // InternalShardML.g:756:2: ( ( ( ( ruleValidID ) ) otherlv_1= 'belongs_to' ( ( ruleValidID ) ) otherlv_3= 'via' ( ( ruleValidID ) ) ) )
            // InternalShardML.g:757:2: ( ( ( ruleValidID ) ) otherlv_1= 'belongs_to' ( ( ruleValidID ) ) otherlv_3= 'via' ( ( ruleValidID ) ) )
            {
            // InternalShardML.g:757:2: ( ( ( ruleValidID ) ) otherlv_1= 'belongs_to' ( ( ruleValidID ) ) otherlv_3= 'via' ( ( ruleValidID ) ) )
            // InternalShardML.g:758:3: ( ( ruleValidID ) ) otherlv_1= 'belongs_to' ( ( ruleValidID ) ) otherlv_3= 'via' ( ( ruleValidID ) )
            {
            // InternalShardML.g:758:3: ( ( ruleValidID ) )
            // InternalShardML.g:759:4: ( ruleValidID )
            {
            // InternalShardML.g:759:4: ( ruleValidID )
            // InternalShardML.g:760:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBelongsToRule());
            					}
            				

            					newCompositeNode(grammarAccess.getBelongsToAccess().getSubjectTableCrossReference_0_0());
            				
            pushFollow(FOLLOW_23);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,27,FOLLOW_12); 

            			newLeafNode(otherlv_1, grammarAccess.getBelongsToAccess().getBelongs_toKeyword_1());
            		
            // InternalShardML.g:778:3: ( ( ruleValidID ) )
            // InternalShardML.g:779:4: ( ruleValidID )
            {
            // InternalShardML.g:779:4: ( ruleValidID )
            // InternalShardML.g:780:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBelongsToRule());
            					}
            				

            					newCompositeNode(grammarAccess.getBelongsToAccess().getBelongsTableCrossReference_2_0());
            				
            pushFollow(FOLLOW_24);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_3=(Token)match(input,28,FOLLOW_12); 

            			newLeafNode(otherlv_3, grammarAccess.getBelongsToAccess().getViaKeyword_3());
            		
            // InternalShardML.g:798:3: ( ( ruleValidID ) )
            // InternalShardML.g:799:4: ( ruleValidID )
            {
            // InternalShardML.g:799:4: ( ruleValidID )
            // InternalShardML.g:800:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getBelongsToRule());
            					}
            				

            					newCompositeNode(grammarAccess.getBelongsToAccess().getViaColumnCrossReference_4_0());
            				
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
    // $ANTLR end "ruleBelongsTo"


    // $ANTLR start "entryRuleShardDecl"
    // InternalShardML.g:818:1: entryRuleShardDecl returns [EObject current=null] : iv_ruleShardDecl= ruleShardDecl EOF ;
    public final EObject entryRuleShardDecl() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleShardDecl = null;


        try {
            // InternalShardML.g:818:50: (iv_ruleShardDecl= ruleShardDecl EOF )
            // InternalShardML.g:819:2: iv_ruleShardDecl= ruleShardDecl EOF
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
    // InternalShardML.g:825:1: ruleShardDecl returns [EObject current=null] : (otherlv_0= 'shard' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= ruleStrategy ) ) (otherlv_5= 'key:' ( ( ruleValidID ) ) )? (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )? (otherlv_9= 'colocate_with:' ( ( ruleValidID ) ) )? otherlv_11= '}' ) ;
    public final EObject ruleShardDecl() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token lv_buckets_8_0=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Enumerator lv_strategy_4_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:831:2: ( (otherlv_0= 'shard' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= ruleStrategy ) ) (otherlv_5= 'key:' ( ( ruleValidID ) ) )? (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )? (otherlv_9= 'colocate_with:' ( ( ruleValidID ) ) )? otherlv_11= '}' ) )
            // InternalShardML.g:832:2: (otherlv_0= 'shard' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= ruleStrategy ) ) (otherlv_5= 'key:' ( ( ruleValidID ) ) )? (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )? (otherlv_9= 'colocate_with:' ( ( ruleValidID ) ) )? otherlv_11= '}' )
            {
            // InternalShardML.g:832:2: (otherlv_0= 'shard' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= ruleStrategy ) ) (otherlv_5= 'key:' ( ( ruleValidID ) ) )? (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )? (otherlv_9= 'colocate_with:' ( ( ruleValidID ) ) )? otherlv_11= '}' )
            // InternalShardML.g:833:3: otherlv_0= 'shard' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'strategy:' ( (lv_strategy_4_0= ruleStrategy ) ) (otherlv_5= 'key:' ( ( ruleValidID ) ) )? (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )? (otherlv_9= 'colocate_with:' ( ( ruleValidID ) ) )? otherlv_11= '}'
            {
            otherlv_0=(Token)match(input,29,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getShardDeclAccess().getShardKeyword_0());
            		
            // InternalShardML.g:837:3: ( ( ruleValidID ) )
            // InternalShardML.g:838:4: ( ruleValidID )
            {
            // InternalShardML.g:838:4: ( ruleValidID )
            // InternalShardML.g:839:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getShardDeclRule());
            					}
            				

            					newCompositeNode(grammarAccess.getShardDeclAccess().getEntityTableCrossReference_1_0());
            				
            pushFollow(FOLLOW_6);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_17); 

            			newLeafNode(otherlv_2, grammarAccess.getShardDeclAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,22,FOLLOW_25); 

            			newLeafNode(otherlv_3, grammarAccess.getShardDeclAccess().getStrategyKeyword_3());
            		
            // InternalShardML.g:861:3: ( (lv_strategy_4_0= ruleStrategy ) )
            // InternalShardML.g:862:4: (lv_strategy_4_0= ruleStrategy )
            {
            // InternalShardML.g:862:4: (lv_strategy_4_0= ruleStrategy )
            // InternalShardML.g:863:5: lv_strategy_4_0= ruleStrategy
            {

            					newCompositeNode(grammarAccess.getShardDeclAccess().getStrategyStrategyEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_26);
            lv_strategy_4_0=ruleStrategy();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getShardDeclRule());
            					}
            					set(
            						current,
            						"strategy",
            						lv_strategy_4_0,
            						"io.shardml.ShardML.Strategy");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:880:3: (otherlv_5= 'key:' ( ( ruleValidID ) ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==30) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalShardML.g:881:4: otherlv_5= 'key:' ( ( ruleValidID ) )
                    {
                    otherlv_5=(Token)match(input,30,FOLLOW_12); 

                    				newLeafNode(otherlv_5, grammarAccess.getShardDeclAccess().getKeyKeyword_5_0());
                    			
                    // InternalShardML.g:885:4: ( ( ruleValidID ) )
                    // InternalShardML.g:886:5: ( ruleValidID )
                    {
                    // InternalShardML.g:886:5: ( ruleValidID )
                    // InternalShardML.g:887:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getShardDeclRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getShardDeclAccess().getIdColumnCrossReference_5_1_0());
                    					
                    pushFollow(FOLLOW_27);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalShardML.g:902:3: (otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==31) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalShardML.g:903:4: otherlv_7= 'buckets:' ( (lv_buckets_8_0= RULE_INT ) )
                    {
                    otherlv_7=(Token)match(input,31,FOLLOW_15); 

                    				newLeafNode(otherlv_7, grammarAccess.getShardDeclAccess().getBucketsKeyword_6_0());
                    			
                    // InternalShardML.g:907:4: ( (lv_buckets_8_0= RULE_INT ) )
                    // InternalShardML.g:908:5: (lv_buckets_8_0= RULE_INT )
                    {
                    // InternalShardML.g:908:5: (lv_buckets_8_0= RULE_INT )
                    // InternalShardML.g:909:6: lv_buckets_8_0= RULE_INT
                    {
                    lv_buckets_8_0=(Token)match(input,RULE_INT,FOLLOW_28); 

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

            // InternalShardML.g:926:3: (otherlv_9= 'colocate_with:' ( ( ruleValidID ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==32) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalShardML.g:927:4: otherlv_9= 'colocate_with:' ( ( ruleValidID ) )
                    {
                    otherlv_9=(Token)match(input,32,FOLLOW_12); 

                    				newLeafNode(otherlv_9, grammarAccess.getShardDeclAccess().getColocate_withKeyword_7_0());
                    			
                    // InternalShardML.g:931:4: ( ( ruleValidID ) )
                    // InternalShardML.g:932:5: ( ruleValidID )
                    {
                    // InternalShardML.g:932:5: ( ruleValidID )
                    // InternalShardML.g:933:6: ruleValidID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getShardDeclRule());
                    						}
                    					

                    						newCompositeNode(grammarAccess.getShardDeclAccess().getColocateTableCrossReference_7_1_0());
                    					
                    pushFollow(FOLLOW_16);
                    ruleValidID();

                    state._fsp--;


                    						afterParserOrEnumRuleCall();
                    					

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
    // InternalShardML.g:956:1: entryRuleRouteRule returns [EObject current=null] : iv_ruleRouteRule= ruleRouteRule EOF ;
    public final EObject entryRuleRouteRule() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRouteRule = null;


        try {
            // InternalShardML.g:956:50: (iv_ruleRouteRule= ruleRouteRule EOF )
            // InternalShardML.g:957:2: iv_ruleRouteRule= ruleRouteRule EOF
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
    // InternalShardML.g:963:1: ruleRouteRule returns [EObject current=null] : (otherlv_0= 'route' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'policy:' ( (lv_policy_4_0= rulePolicy ) ) (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )? ( (lv_queries_7_0= ruleQueryPattern ) )* otherlv_8= '}' ) ;
    public final EObject ruleRouteRule() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_condtions_6_0=null;
        Token otherlv_8=null;
        Enumerator lv_policy_4_0 = null;

        EObject lv_queries_7_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:969:2: ( (otherlv_0= 'route' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'policy:' ( (lv_policy_4_0= rulePolicy ) ) (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )? ( (lv_queries_7_0= ruleQueryPattern ) )* otherlv_8= '}' ) )
            // InternalShardML.g:970:2: (otherlv_0= 'route' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'policy:' ( (lv_policy_4_0= rulePolicy ) ) (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )? ( (lv_queries_7_0= ruleQueryPattern ) )* otherlv_8= '}' )
            {
            // InternalShardML.g:970:2: (otherlv_0= 'route' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'policy:' ( (lv_policy_4_0= rulePolicy ) ) (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )? ( (lv_queries_7_0= ruleQueryPattern ) )* otherlv_8= '}' )
            // InternalShardML.g:971:3: otherlv_0= 'route' ( ( ruleValidID ) ) otherlv_2= '{' otherlv_3= 'policy:' ( (lv_policy_4_0= rulePolicy ) ) (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )? ( (lv_queries_7_0= ruleQueryPattern ) )* otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,33,FOLLOW_12); 

            			newLeafNode(otherlv_0, grammarAccess.getRouteRuleAccess().getRouteKeyword_0());
            		
            // InternalShardML.g:975:3: ( ( ruleValidID ) )
            // InternalShardML.g:976:4: ( ruleValidID )
            {
            // InternalShardML.g:976:4: ( ruleValidID )
            // InternalShardML.g:977:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRouteRuleRule());
            					}
            				

            					newCompositeNode(grammarAccess.getRouteRuleAccess().getEntityTableCrossReference_1_0());
            				
            pushFollow(FOLLOW_6);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_29); 

            			newLeafNode(otherlv_2, grammarAccess.getRouteRuleAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,34,FOLLOW_30); 

            			newLeafNode(otherlv_3, grammarAccess.getRouteRuleAccess().getPolicyKeyword_3());
            		
            // InternalShardML.g:999:3: ( (lv_policy_4_0= rulePolicy ) )
            // InternalShardML.g:1000:4: (lv_policy_4_0= rulePolicy )
            {
            // InternalShardML.g:1000:4: (lv_policy_4_0= rulePolicy )
            // InternalShardML.g:1001:5: lv_policy_4_0= rulePolicy
            {

            					newCompositeNode(grammarAccess.getRouteRuleAccess().getPolicyPolicyEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_31);
            lv_policy_4_0=rulePolicy();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRouteRuleRule());
            					}
            					set(
            						current,
            						"policy",
            						lv_policy_4_0,
            						"io.shardml.ShardML.Policy");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:1018:3: (otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==35) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalShardML.g:1019:4: otherlv_5= 'max_conditions:' ( (lv_condtions_6_0= RULE_INT ) )
                    {
                    otherlv_5=(Token)match(input,35,FOLLOW_15); 

                    				newLeafNode(otherlv_5, grammarAccess.getRouteRuleAccess().getMax_conditionsKeyword_5_0());
                    			
                    // InternalShardML.g:1023:4: ( (lv_condtions_6_0= RULE_INT ) )
                    // InternalShardML.g:1024:5: (lv_condtions_6_0= RULE_INT )
                    {
                    // InternalShardML.g:1024:5: (lv_condtions_6_0= RULE_INT )
                    // InternalShardML.g:1025:6: lv_condtions_6_0= RULE_INT
                    {
                    lv_condtions_6_0=(Token)match(input,RULE_INT,FOLLOW_32); 

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

            // InternalShardML.g:1042:3: ( (lv_queries_7_0= ruleQueryPattern ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==36) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // InternalShardML.g:1043:4: (lv_queries_7_0= ruleQueryPattern )
            	    {
            	    // InternalShardML.g:1043:4: (lv_queries_7_0= ruleQueryPattern )
            	    // InternalShardML.g:1044:5: lv_queries_7_0= ruleQueryPattern
            	    {

            	    					newCompositeNode(grammarAccess.getRouteRuleAccess().getQueriesQueryPatternParserRuleCall_6_0());
            	    				
            	    pushFollow(FOLLOW_32);
            	    lv_queries_7_0=ruleQueryPattern();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getRouteRuleRule());
            	    					}
            	    					add(
            	    						current,
            	    						"queries",
            	    						lv_queries_7_0,
            	    						"io.shardml.ShardML.QueryPattern");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop15;
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
    // InternalShardML.g:1069:1: entryRuleQueryPattern returns [EObject current=null] : iv_ruleQueryPattern= ruleQueryPattern EOF ;
    public final EObject entryRuleQueryPattern() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleQueryPattern = null;


        try {
            // InternalShardML.g:1069:53: (iv_ruleQueryPattern= ruleQueryPattern EOF )
            // InternalShardML.g:1070:2: iv_ruleQueryPattern= ruleQueryPattern EOF
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
    // InternalShardML.g:1076:1: ruleQueryPattern returns [EObject current=null] : (otherlv_0= 'query' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_queryType_4_0= ruleQueryType ) ) (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )? (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )? (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )? (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )? otherlv_13= '}' ) ;
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
            // InternalShardML.g:1082:2: ( (otherlv_0= 'query' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_queryType_4_0= ruleQueryType ) ) (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )? (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )? (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )? (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )? otherlv_13= '}' ) )
            // InternalShardML.g:1083:2: (otherlv_0= 'query' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_queryType_4_0= ruleQueryType ) ) (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )? (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )? (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )? (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )? otherlv_13= '}' )
            {
            // InternalShardML.g:1083:2: (otherlv_0= 'query' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_queryType_4_0= ruleQueryType ) ) (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )? (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )? (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )? (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )? otherlv_13= '}' )
            // InternalShardML.g:1084:3: otherlv_0= 'query' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'type:' ( (lv_queryType_4_0= ruleQueryType ) ) (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )? (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )? (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )? (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )? otherlv_13= '}'
            {
            otherlv_0=(Token)match(input,36,FOLLOW_5); 

            			newLeafNode(otherlv_0, grammarAccess.getQueryPatternAccess().getQueryKeyword_0());
            		
            // InternalShardML.g:1088:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalShardML.g:1089:4: (lv_name_1_0= RULE_ID )
            {
            // InternalShardML.g:1089:4: (lv_name_1_0= RULE_ID )
            // InternalShardML.g:1090:5: lv_name_1_0= RULE_ID
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
            		
            otherlv_3=(Token)match(input,14,FOLLOW_33); 

            			newLeafNode(otherlv_3, grammarAccess.getQueryPatternAccess().getTypeKeyword_3());
            		
            // InternalShardML.g:1114:3: ( (lv_queryType_4_0= ruleQueryType ) )
            // InternalShardML.g:1115:4: (lv_queryType_4_0= ruleQueryType )
            {
            // InternalShardML.g:1115:4: (lv_queryType_4_0= ruleQueryType )
            // InternalShardML.g:1116:5: lv_queryType_4_0= ruleQueryType
            {

            					newCompositeNode(grammarAccess.getQueryPatternAccess().getQueryTypeQueryTypeEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_34);
            lv_queryType_4_0=ruleQueryType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getQueryPatternRule());
            					}
            					set(
            						current,
            						"queryType",
            						lv_queryType_4_0,
            						"io.shardml.ShardML.QueryType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:1133:3: (otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==37) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalShardML.g:1134:4: otherlv_5= 'where:' ( (lv_where_6_0= ruleWhereClause ) )
                    {
                    otherlv_5=(Token)match(input,37,FOLLOW_12); 

                    				newLeafNode(otherlv_5, grammarAccess.getQueryPatternAccess().getWhereKeyword_5_0());
                    			
                    // InternalShardML.g:1138:4: ( (lv_where_6_0= ruleWhereClause ) )
                    // InternalShardML.g:1139:5: (lv_where_6_0= ruleWhereClause )
                    {
                    // InternalShardML.g:1139:5: (lv_where_6_0= ruleWhereClause )
                    // InternalShardML.g:1140:6: lv_where_6_0= ruleWhereClause
                    {

                    						newCompositeNode(grammarAccess.getQueryPatternAccess().getWhereWhereClauseParserRuleCall_5_1_0());
                    					
                    pushFollow(FOLLOW_35);
                    lv_where_6_0=ruleWhereClause();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQueryPatternRule());
                    						}
                    						set(
                    							current,
                    							"where",
                    							lv_where_6_0,
                    							"io.shardml.ShardML.WhereClause");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalShardML.g:1158:3: (otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==38) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalShardML.g:1159:4: otherlv_7= 'sort:' ( (lv_sort_8_0= ruleSortClause ) )
                    {
                    otherlv_7=(Token)match(input,38,FOLLOW_12); 

                    				newLeafNode(otherlv_7, grammarAccess.getQueryPatternAccess().getSortKeyword_6_0());
                    			
                    // InternalShardML.g:1163:4: ( (lv_sort_8_0= ruleSortClause ) )
                    // InternalShardML.g:1164:5: (lv_sort_8_0= ruleSortClause )
                    {
                    // InternalShardML.g:1164:5: (lv_sort_8_0= ruleSortClause )
                    // InternalShardML.g:1165:6: lv_sort_8_0= ruleSortClause
                    {

                    						newCompositeNode(grammarAccess.getQueryPatternAccess().getSortSortClauseParserRuleCall_6_1_0());
                    					
                    pushFollow(FOLLOW_36);
                    lv_sort_8_0=ruleSortClause();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQueryPatternRule());
                    						}
                    						set(
                    							current,
                    							"sort",
                    							lv_sort_8_0,
                    							"io.shardml.ShardML.SortClause");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalShardML.g:1183:3: (otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==39) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalShardML.g:1184:4: otherlv_9= 'group_by:' ( (lv_group_10_0= ruleGroupBy ) )
                    {
                    otherlv_9=(Token)match(input,39,FOLLOW_12); 

                    				newLeafNode(otherlv_9, grammarAccess.getQueryPatternAccess().getGroup_byKeyword_7_0());
                    			
                    // InternalShardML.g:1188:4: ( (lv_group_10_0= ruleGroupBy ) )
                    // InternalShardML.g:1189:5: (lv_group_10_0= ruleGroupBy )
                    {
                    // InternalShardML.g:1189:5: (lv_group_10_0= ruleGroupBy )
                    // InternalShardML.g:1190:6: lv_group_10_0= ruleGroupBy
                    {

                    						newCompositeNode(grammarAccess.getQueryPatternAccess().getGroupGroupByParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_37);
                    lv_group_10_0=ruleGroupBy();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getQueryPatternRule());
                    						}
                    						set(
                    							current,
                    							"group",
                    							lv_group_10_0,
                    							"io.shardml.ShardML.GroupBy");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalShardML.g:1208:3: (otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==40) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalShardML.g:1209:4: otherlv_11= 'distinct:' ( (lv_distinct_12_0= 'true' ) )
                    {
                    otherlv_11=(Token)match(input,40,FOLLOW_38); 

                    				newLeafNode(otherlv_11, grammarAccess.getQueryPatternAccess().getDistinctKeyword_8_0());
                    			
                    // InternalShardML.g:1213:4: ( (lv_distinct_12_0= 'true' ) )
                    // InternalShardML.g:1214:5: (lv_distinct_12_0= 'true' )
                    {
                    // InternalShardML.g:1214:5: (lv_distinct_12_0= 'true' )
                    // InternalShardML.g:1215:6: lv_distinct_12_0= 'true'
                    {
                    lv_distinct_12_0=(Token)match(input,41,FOLLOW_16); 

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
    // InternalShardML.g:1236:1: entryRuleWhereClause returns [EObject current=null] : iv_ruleWhereClause= ruleWhereClause EOF ;
    public final EObject entryRuleWhereClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhereClause = null;


        try {
            // InternalShardML.g:1236:52: (iv_ruleWhereClause= ruleWhereClause EOF )
            // InternalShardML.g:1237:2: iv_ruleWhereClause= ruleWhereClause EOF
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
    // InternalShardML.g:1243:1: ruleWhereClause returns [EObject current=null] : ( ( (lv_first_0_0= ruleWhereCondition ) ) ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )* ) ;
    public final EObject ruleWhereClause() throws RecognitionException {
        EObject current = null;

        EObject lv_first_0_0 = null;

        Enumerator lv_link_1_0 = null;

        EObject lv_rest_2_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:1249:2: ( ( ( (lv_first_0_0= ruleWhereCondition ) ) ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )* ) )
            // InternalShardML.g:1250:2: ( ( (lv_first_0_0= ruleWhereCondition ) ) ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )* )
            {
            // InternalShardML.g:1250:2: ( ( (lv_first_0_0= ruleWhereCondition ) ) ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )* )
            // InternalShardML.g:1251:3: ( (lv_first_0_0= ruleWhereCondition ) ) ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )*
            {
            // InternalShardML.g:1251:3: ( (lv_first_0_0= ruleWhereCondition ) )
            // InternalShardML.g:1252:4: (lv_first_0_0= ruleWhereCondition )
            {
            // InternalShardML.g:1252:4: (lv_first_0_0= ruleWhereCondition )
            // InternalShardML.g:1253:5: lv_first_0_0= ruleWhereCondition
            {

            					newCompositeNode(grammarAccess.getWhereClauseAccess().getFirstWhereConditionParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_39);
            lv_first_0_0=ruleWhereCondition();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWhereClauseRule());
            					}
            					set(
            						current,
            						"first",
            						lv_first_0_0,
            						"io.shardml.ShardML.WhereCondition");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:1270:3: ( ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=60 && LA20_0<=61)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalShardML.g:1271:4: ( (lv_link_1_0= ruleLink ) ) ( (lv_rest_2_0= ruleWhereCondition ) )
            	    {
            	    // InternalShardML.g:1271:4: ( (lv_link_1_0= ruleLink ) )
            	    // InternalShardML.g:1272:5: (lv_link_1_0= ruleLink )
            	    {
            	    // InternalShardML.g:1272:5: (lv_link_1_0= ruleLink )
            	    // InternalShardML.g:1273:6: lv_link_1_0= ruleLink
            	    {

            	    						newCompositeNode(grammarAccess.getWhereClauseAccess().getLinkLinkEnumRuleCall_1_0_0());
            	    					
            	    pushFollow(FOLLOW_12);
            	    lv_link_1_0=ruleLink();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getWhereClauseRule());
            	    						}
            	    						add(
            	    							current,
            	    							"link",
            	    							lv_link_1_0,
            	    							"io.shardml.ShardML.Link");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }

            	    // InternalShardML.g:1290:4: ( (lv_rest_2_0= ruleWhereCondition ) )
            	    // InternalShardML.g:1291:5: (lv_rest_2_0= ruleWhereCondition )
            	    {
            	    // InternalShardML.g:1291:5: (lv_rest_2_0= ruleWhereCondition )
            	    // InternalShardML.g:1292:6: lv_rest_2_0= ruleWhereCondition
            	    {

            	    						newCompositeNode(grammarAccess.getWhereClauseAccess().getRestWhereConditionParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_39);
            	    lv_rest_2_0=ruleWhereCondition();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getWhereClauseRule());
            	    						}
            	    						add(
            	    							current,
            	    							"rest",
            	    							lv_rest_2_0,
            	    							"io.shardml.ShardML.WhereCondition");
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
    // InternalShardML.g:1314:1: entryRuleWhereCondition returns [EObject current=null] : iv_ruleWhereCondition= ruleWhereCondition EOF ;
    public final EObject entryRuleWhereCondition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleWhereCondition = null;


        try {
            // InternalShardML.g:1314:55: (iv_ruleWhereCondition= ruleWhereCondition EOF )
            // InternalShardML.g:1315:2: iv_ruleWhereCondition= ruleWhereCondition EOF
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
    // InternalShardML.g:1321:1: ruleWhereCondition returns [EObject current=null] : ( ( ( ruleValidID ) ) ( (lv_op_1_0= ruleOperator ) ) otherlv_2= '?' ) ;
    public final EObject ruleWhereCondition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Enumerator lv_op_1_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:1327:2: ( ( ( ( ruleValidID ) ) ( (lv_op_1_0= ruleOperator ) ) otherlv_2= '?' ) )
            // InternalShardML.g:1328:2: ( ( ( ruleValidID ) ) ( (lv_op_1_0= ruleOperator ) ) otherlv_2= '?' )
            {
            // InternalShardML.g:1328:2: ( ( ( ruleValidID ) ) ( (lv_op_1_0= ruleOperator ) ) otherlv_2= '?' )
            // InternalShardML.g:1329:3: ( ( ruleValidID ) ) ( (lv_op_1_0= ruleOperator ) ) otherlv_2= '?'
            {
            // InternalShardML.g:1329:3: ( ( ruleValidID ) )
            // InternalShardML.g:1330:4: ( ruleValidID )
            {
            // InternalShardML.g:1330:4: ( ruleValidID )
            // InternalShardML.g:1331:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getWhereConditionRule());
            					}
            				

            					newCompositeNode(grammarAccess.getWhereConditionAccess().getFieldColumnCrossReference_0_0());
            				
            pushFollow(FOLLOW_40);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:1345:3: ( (lv_op_1_0= ruleOperator ) )
            // InternalShardML.g:1346:4: (lv_op_1_0= ruleOperator )
            {
            // InternalShardML.g:1346:4: (lv_op_1_0= ruleOperator )
            // InternalShardML.g:1347:5: lv_op_1_0= ruleOperator
            {

            					newCompositeNode(grammarAccess.getWhereConditionAccess().getOpOperatorEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_41);
            lv_op_1_0=ruleOperator();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getWhereConditionRule());
            					}
            					set(
            						current,
            						"op",
            						lv_op_1_0,
            						"io.shardml.ShardML.Operator");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,42,FOLLOW_2); 

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
    // InternalShardML.g:1372:1: entryRuleSortClause returns [EObject current=null] : iv_ruleSortClause= ruleSortClause EOF ;
    public final EObject entryRuleSortClause() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSortClause = null;


        try {
            // InternalShardML.g:1372:51: (iv_ruleSortClause= ruleSortClause EOF )
            // InternalShardML.g:1373:2: iv_ruleSortClause= ruleSortClause EOF
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
    // InternalShardML.g:1379:1: ruleSortClause returns [EObject current=null] : ( ( ( ruleValidID ) ) ( (lv_order_1_0= ruleSortOrder ) ) ) ;
    public final EObject ruleSortClause() throws RecognitionException {
        EObject current = null;

        Enumerator lv_order_1_0 = null;



        	enterRule();

        try {
            // InternalShardML.g:1385:2: ( ( ( ( ruleValidID ) ) ( (lv_order_1_0= ruleSortOrder ) ) ) )
            // InternalShardML.g:1386:2: ( ( ( ruleValidID ) ) ( (lv_order_1_0= ruleSortOrder ) ) )
            {
            // InternalShardML.g:1386:2: ( ( ( ruleValidID ) ) ( (lv_order_1_0= ruleSortOrder ) ) )
            // InternalShardML.g:1387:3: ( ( ruleValidID ) ) ( (lv_order_1_0= ruleSortOrder ) )
            {
            // InternalShardML.g:1387:3: ( ( ruleValidID ) )
            // InternalShardML.g:1388:4: ( ruleValidID )
            {
            // InternalShardML.g:1388:4: ( ruleValidID )
            // InternalShardML.g:1389:5: ruleValidID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSortClauseRule());
            					}
            				

            					newCompositeNode(grammarAccess.getSortClauseAccess().getFieldColumnCrossReference_0_0());
            				
            pushFollow(FOLLOW_42);
            ruleValidID();

            state._fsp--;


            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalShardML.g:1403:3: ( (lv_order_1_0= ruleSortOrder ) )
            // InternalShardML.g:1404:4: (lv_order_1_0= ruleSortOrder )
            {
            // InternalShardML.g:1404:4: (lv_order_1_0= ruleSortOrder )
            // InternalShardML.g:1405:5: lv_order_1_0= ruleSortOrder
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
            						"io.shardml.ShardML.SortOrder");
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
    // InternalShardML.g:1426:1: entryRuleGroupBy returns [EObject current=null] : iv_ruleGroupBy= ruleGroupBy EOF ;
    public final EObject entryRuleGroupBy() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGroupBy = null;


        try {
            // InternalShardML.g:1426:48: (iv_ruleGroupBy= ruleGroupBy EOF )
            // InternalShardML.g:1427:2: iv_ruleGroupBy= ruleGroupBy EOF
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
    // InternalShardML.g:1433:1: ruleGroupBy returns [EObject current=null] : ( ( ruleValidID ) ) ;
    public final EObject ruleGroupBy() throws RecognitionException {
        EObject current = null;


        	enterRule();

        try {
            // InternalShardML.g:1439:2: ( ( ( ruleValidID ) ) )
            // InternalShardML.g:1440:2: ( ( ruleValidID ) )
            {
            // InternalShardML.g:1440:2: ( ( ruleValidID ) )
            // InternalShardML.g:1441:3: ( ruleValidID )
            {
            // InternalShardML.g:1441:3: ( ruleValidID )
            // InternalShardML.g:1442:4: ruleValidID
            {

            				if (current==null) {
            					current = createModelElement(grammarAccess.getGroupByRule());
            				}
            			

            				newCompositeNode(grammarAccess.getGroupByAccess().getFieldColumnCrossReference_0());
            			
            pushFollow(FOLLOW_2);
            ruleValidID();

            state._fsp--;


            				afterParserOrEnumRuleCall();
            			

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


    // $ANTLR start "rulePlacementStrategy"
    // InternalShardML.g:1459:1: rulePlacementStrategy returns [Enumerator current=null] : ( (enumLiteral_0= 'spread' ) | (enumLiteral_1= 'pin' ) ) ;
    public final Enumerator rulePlacementStrategy() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalShardML.g:1465:2: ( ( (enumLiteral_0= 'spread' ) | (enumLiteral_1= 'pin' ) ) )
            // InternalShardML.g:1466:2: ( (enumLiteral_0= 'spread' ) | (enumLiteral_1= 'pin' ) )
            {
            // InternalShardML.g:1466:2: ( (enumLiteral_0= 'spread' ) | (enumLiteral_1= 'pin' ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==18) ) {
                alt21=1;
            }
            else if ( (LA21_0==19) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // InternalShardML.g:1467:3: (enumLiteral_0= 'spread' )
                    {
                    // InternalShardML.g:1467:3: (enumLiteral_0= 'spread' )
                    // InternalShardML.g:1468:4: enumLiteral_0= 'spread'
                    {
                    enumLiteral_0=(Token)match(input,18,FOLLOW_2); 

                    				current = grammarAccess.getPlacementStrategyAccess().getSPREADEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getPlacementStrategyAccess().getSPREADEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1475:3: (enumLiteral_1= 'pin' )
                    {
                    // InternalShardML.g:1475:3: (enumLiteral_1= 'pin' )
                    // InternalShardML.g:1476:4: enumLiteral_1= 'pin'
                    {
                    enumLiteral_1=(Token)match(input,19,FOLLOW_2); 

                    				current = grammarAccess.getPlacementStrategyAccess().getPINEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getPlacementStrategyAccess().getPINEnumLiteralDeclaration_1());
                    			

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
    // $ANTLR end "rulePlacementStrategy"


    // $ANTLR start "ruleDatabaseType"
    // InternalShardML.g:1486:1: ruleDatabaseType returns [Enumerator current=null] : ( (enumLiteral_0= 'postgres' ) | (enumLiteral_1= 'mysql' ) ) ;
    public final Enumerator ruleDatabaseType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalShardML.g:1492:2: ( ( (enumLiteral_0= 'postgres' ) | (enumLiteral_1= 'mysql' ) ) )
            // InternalShardML.g:1493:2: ( (enumLiteral_0= 'postgres' ) | (enumLiteral_1= 'mysql' ) )
            {
            // InternalShardML.g:1493:2: ( (enumLiteral_0= 'postgres' ) | (enumLiteral_1= 'mysql' ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==43) ) {
                alt22=1;
            }
            else if ( (LA22_0==44) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalShardML.g:1494:3: (enumLiteral_0= 'postgres' )
                    {
                    // InternalShardML.g:1494:3: (enumLiteral_0= 'postgres' )
                    // InternalShardML.g:1495:4: enumLiteral_0= 'postgres'
                    {
                    enumLiteral_0=(Token)match(input,43,FOLLOW_2); 

                    				current = grammarAccess.getDatabaseTypeAccess().getPOSTGRESEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDatabaseTypeAccess().getPOSTGRESEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1502:3: (enumLiteral_1= 'mysql' )
                    {
                    // InternalShardML.g:1502:3: (enumLiteral_1= 'mysql' )
                    // InternalShardML.g:1503:4: enumLiteral_1= 'mysql'
                    {
                    enumLiteral_1=(Token)match(input,44,FOLLOW_2); 

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
    // InternalShardML.g:1513:1: ruleStrategy returns [Enumerator current=null] : ( (enumLiteral_0= 'hash' ) | (enumLiteral_1= 'consistent_hash' ) | (enumLiteral_2= 'replicated' ) ) ;
    public final Enumerator ruleStrategy() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalShardML.g:1519:2: ( ( (enumLiteral_0= 'hash' ) | (enumLiteral_1= 'consistent_hash' ) | (enumLiteral_2= 'replicated' ) ) )
            // InternalShardML.g:1520:2: ( (enumLiteral_0= 'hash' ) | (enumLiteral_1= 'consistent_hash' ) | (enumLiteral_2= 'replicated' ) )
            {
            // InternalShardML.g:1520:2: ( (enumLiteral_0= 'hash' ) | (enumLiteral_1= 'consistent_hash' ) | (enumLiteral_2= 'replicated' ) )
            int alt23=3;
            switch ( input.LA(1) ) {
            case 45:
                {
                alt23=1;
                }
                break;
            case 46:
                {
                alt23=2;
                }
                break;
            case 47:
                {
                alt23=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // InternalShardML.g:1521:3: (enumLiteral_0= 'hash' )
                    {
                    // InternalShardML.g:1521:3: (enumLiteral_0= 'hash' )
                    // InternalShardML.g:1522:4: enumLiteral_0= 'hash'
                    {
                    enumLiteral_0=(Token)match(input,45,FOLLOW_2); 

                    				current = grammarAccess.getStrategyAccess().getHASHEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getStrategyAccess().getHASHEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1529:3: (enumLiteral_1= 'consistent_hash' )
                    {
                    // InternalShardML.g:1529:3: (enumLiteral_1= 'consistent_hash' )
                    // InternalShardML.g:1530:4: enumLiteral_1= 'consistent_hash'
                    {
                    enumLiteral_1=(Token)match(input,46,FOLLOW_2); 

                    				current = grammarAccess.getStrategyAccess().getCONS_HASHEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getStrategyAccess().getCONS_HASHEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:1537:3: (enumLiteral_2= 'replicated' )
                    {
                    // InternalShardML.g:1537:3: (enumLiteral_2= 'replicated' )
                    // InternalShardML.g:1538:4: enumLiteral_2= 'replicated'
                    {
                    enumLiteral_2=(Token)match(input,47,FOLLOW_2); 

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
    // InternalShardML.g:1548:1: rulePolicy returns [Enumerator current=null] : ( (enumLiteral_0= 'allow' ) | (enumLiteral_1= 'warn' ) | (enumLiteral_2= 'deny' ) ) ;
    public final Enumerator rulePolicy() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalShardML.g:1554:2: ( ( (enumLiteral_0= 'allow' ) | (enumLiteral_1= 'warn' ) | (enumLiteral_2= 'deny' ) ) )
            // InternalShardML.g:1555:2: ( (enumLiteral_0= 'allow' ) | (enumLiteral_1= 'warn' ) | (enumLiteral_2= 'deny' ) )
            {
            // InternalShardML.g:1555:2: ( (enumLiteral_0= 'allow' ) | (enumLiteral_1= 'warn' ) | (enumLiteral_2= 'deny' ) )
            int alt24=3;
            switch ( input.LA(1) ) {
            case 48:
                {
                alt24=1;
                }
                break;
            case 49:
                {
                alt24=2;
                }
                break;
            case 50:
                {
                alt24=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // InternalShardML.g:1556:3: (enumLiteral_0= 'allow' )
                    {
                    // InternalShardML.g:1556:3: (enumLiteral_0= 'allow' )
                    // InternalShardML.g:1557:4: enumLiteral_0= 'allow'
                    {
                    enumLiteral_0=(Token)match(input,48,FOLLOW_2); 

                    				current = grammarAccess.getPolicyAccess().getALLOWEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getPolicyAccess().getALLOWEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1564:3: (enumLiteral_1= 'warn' )
                    {
                    // InternalShardML.g:1564:3: (enumLiteral_1= 'warn' )
                    // InternalShardML.g:1565:4: enumLiteral_1= 'warn'
                    {
                    enumLiteral_1=(Token)match(input,49,FOLLOW_2); 

                    				current = grammarAccess.getPolicyAccess().getWARNEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getPolicyAccess().getWARNEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:1572:3: (enumLiteral_2= 'deny' )
                    {
                    // InternalShardML.g:1572:3: (enumLiteral_2= 'deny' )
                    // InternalShardML.g:1573:4: enumLiteral_2= 'deny'
                    {
                    enumLiteral_2=(Token)match(input,50,FOLLOW_2); 

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
    // InternalShardML.g:1583:1: ruleQueryType returns [Enumerator current=null] : ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) ) ;
    public final Enumerator ruleQueryType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalShardML.g:1589:2: ( ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) ) )
            // InternalShardML.g:1590:2: ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) )
            {
            // InternalShardML.g:1590:2: ( (enumLiteral_0= 'read' ) | (enumLiteral_1= 'write' ) | (enumLiteral_2= 'delete' ) )
            int alt25=3;
            switch ( input.LA(1) ) {
            case 51:
                {
                alt25=1;
                }
                break;
            case 52:
                {
                alt25=2;
                }
                break;
            case 53:
                {
                alt25=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // InternalShardML.g:1591:3: (enumLiteral_0= 'read' )
                    {
                    // InternalShardML.g:1591:3: (enumLiteral_0= 'read' )
                    // InternalShardML.g:1592:4: enumLiteral_0= 'read'
                    {
                    enumLiteral_0=(Token)match(input,51,FOLLOW_2); 

                    				current = grammarAccess.getQueryTypeAccess().getREADEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getQueryTypeAccess().getREADEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1599:3: (enumLiteral_1= 'write' )
                    {
                    // InternalShardML.g:1599:3: (enumLiteral_1= 'write' )
                    // InternalShardML.g:1600:4: enumLiteral_1= 'write'
                    {
                    enumLiteral_1=(Token)match(input,52,FOLLOW_2); 

                    				current = grammarAccess.getQueryTypeAccess().getWRITEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getQueryTypeAccess().getWRITEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:1607:3: (enumLiteral_2= 'delete' )
                    {
                    // InternalShardML.g:1607:3: (enumLiteral_2= 'delete' )
                    // InternalShardML.g:1608:4: enumLiteral_2= 'delete'
                    {
                    enumLiteral_2=(Token)match(input,53,FOLLOW_2); 

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
    // InternalShardML.g:1618:1: ruleOperator returns [Enumerator current=null] : ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) ) ;
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
            // InternalShardML.g:1624:2: ( ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) ) )
            // InternalShardML.g:1625:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) )
            {
            // InternalShardML.g:1625:2: ( (enumLiteral_0= '=' ) | (enumLiteral_1= '>' ) | (enumLiteral_2= '<' ) | (enumLiteral_3= '<=' ) | (enumLiteral_4= '>=' ) | (enumLiteral_5= '!=' ) )
            int alt26=6;
            switch ( input.LA(1) ) {
            case 54:
                {
                alt26=1;
                }
                break;
            case 55:
                {
                alt26=2;
                }
                break;
            case 56:
                {
                alt26=3;
                }
                break;
            case 57:
                {
                alt26=4;
                }
                break;
            case 58:
                {
                alt26=5;
                }
                break;
            case 59:
                {
                alt26=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // InternalShardML.g:1626:3: (enumLiteral_0= '=' )
                    {
                    // InternalShardML.g:1626:3: (enumLiteral_0= '=' )
                    // InternalShardML.g:1627:4: enumLiteral_0= '='
                    {
                    enumLiteral_0=(Token)match(input,54,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getEQEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getOperatorAccess().getEQEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1634:3: (enumLiteral_1= '>' )
                    {
                    // InternalShardML.g:1634:3: (enumLiteral_1= '>' )
                    // InternalShardML.g:1635:4: enumLiteral_1= '>'
                    {
                    enumLiteral_1=(Token)match(input,55,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getGEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getOperatorAccess().getGEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalShardML.g:1642:3: (enumLiteral_2= '<' )
                    {
                    // InternalShardML.g:1642:3: (enumLiteral_2= '<' )
                    // InternalShardML.g:1643:4: enumLiteral_2= '<'
                    {
                    enumLiteral_2=(Token)match(input,56,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getLEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getOperatorAccess().getLEEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalShardML.g:1650:3: (enumLiteral_3= '<=' )
                    {
                    // InternalShardML.g:1650:3: (enumLiteral_3= '<=' )
                    // InternalShardML.g:1651:4: enumLiteral_3= '<='
                    {
                    enumLiteral_3=(Token)match(input,57,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getLEQEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getOperatorAccess().getLEQEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalShardML.g:1658:3: (enumLiteral_4= '>=' )
                    {
                    // InternalShardML.g:1658:3: (enumLiteral_4= '>=' )
                    // InternalShardML.g:1659:4: enumLiteral_4= '>='
                    {
                    enumLiteral_4=(Token)match(input,58,FOLLOW_2); 

                    				current = grammarAccess.getOperatorAccess().getGEQEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getOperatorAccess().getGEQEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalShardML.g:1666:3: (enumLiteral_5= '!=' )
                    {
                    // InternalShardML.g:1666:3: (enumLiteral_5= '!=' )
                    // InternalShardML.g:1667:4: enumLiteral_5= '!='
                    {
                    enumLiteral_5=(Token)match(input,59,FOLLOW_2); 

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
    // InternalShardML.g:1677:1: ruleLink returns [Enumerator current=null] : ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) ) ;
    public final Enumerator ruleLink() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalShardML.g:1683:2: ( ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) ) )
            // InternalShardML.g:1684:2: ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) )
            {
            // InternalShardML.g:1684:2: ( (enumLiteral_0= 'AND' ) | (enumLiteral_1= 'OR' ) )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==60) ) {
                alt27=1;
            }
            else if ( (LA27_0==61) ) {
                alt27=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // InternalShardML.g:1685:3: (enumLiteral_0= 'AND' )
                    {
                    // InternalShardML.g:1685:3: (enumLiteral_0= 'AND' )
                    // InternalShardML.g:1686:4: enumLiteral_0= 'AND'
                    {
                    enumLiteral_0=(Token)match(input,60,FOLLOW_2); 

                    				current = grammarAccess.getLinkAccess().getANDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getLinkAccess().getANDEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1693:3: (enumLiteral_1= 'OR' )
                    {
                    // InternalShardML.g:1693:3: (enumLiteral_1= 'OR' )
                    // InternalShardML.g:1694:4: enumLiteral_1= 'OR'
                    {
                    enumLiteral_1=(Token)match(input,61,FOLLOW_2); 

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
    // InternalShardML.g:1704:1: ruleSortOrder returns [Enumerator current=null] : ( (enumLiteral_0= 'ASC' ) | (enumLiteral_1= 'DESC' ) ) ;
    public final Enumerator ruleSortOrder() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalShardML.g:1710:2: ( ( (enumLiteral_0= 'ASC' ) | (enumLiteral_1= 'DESC' ) ) )
            // InternalShardML.g:1711:2: ( (enumLiteral_0= 'ASC' ) | (enumLiteral_1= 'DESC' ) )
            {
            // InternalShardML.g:1711:2: ( (enumLiteral_0= 'ASC' ) | (enumLiteral_1= 'DESC' ) )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==62) ) {
                alt28=1;
            }
            else if ( (LA28_0==63) ) {
                alt28=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // InternalShardML.g:1712:3: (enumLiteral_0= 'ASC' )
                    {
                    // InternalShardML.g:1712:3: (enumLiteral_0= 'ASC' )
                    // InternalShardML.g:1713:4: enumLiteral_0= 'ASC'
                    {
                    enumLiteral_0=(Token)match(input,62,FOLLOW_2); 

                    				current = grammarAccess.getSortOrderAccess().getASCEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getSortOrderAccess().getASCEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalShardML.g:1720:3: (enumLiteral_1= 'DESC' )
                    {
                    // InternalShardML.g:1720:3: (enumLiteral_1= 'DESC' )
                    // InternalShardML.g:1721:4: enumLiteral_1= 'DESC'
                    {
                    enumLiteral_1=(Token)match(input,63,FOLLOW_2); 

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
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000180000000000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00000002200F8020L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00000002000F8020L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x00000000000F8020L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x00000000000F0020L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000808000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000001008000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000E00000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x00000001C0008000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000180008000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000100008000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0007000000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000001800008000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000001000008000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0038000000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x000001E000008000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x000001C000008000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000018000008000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000010000008000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x3000000000000002L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0FC0000000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0xC000000000000000L});

}