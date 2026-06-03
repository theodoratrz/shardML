package uk.ac.kcl.inf.mdd1.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import uk.ac.kcl.inf.mdd1.services.SQLGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSQLParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'INT'", "'BIGINT'", "'UUID'", "'DECIMAL'", "'TIMESTAMP'", "'DATE'", "'BOOLEAN'", "'TEXT'", "'CREATE'", "'TABLE'", "'('", "')'", "','", "'VARCHAR'", "'PRIMARY'", "'KEY'", "'UNIQUE'", "'NOT'", "'NULL'"
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

    	public void setGrammarAccess(SQLGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleSchema"
    // InternalSQL.g:53:1: entryRuleSchema : ruleSchema EOF ;
    public final void entryRuleSchema() throws RecognitionException {
        try {
            // InternalSQL.g:54:1: ( ruleSchema EOF )
            // InternalSQL.g:55:1: ruleSchema EOF
            {
             before(grammarAccess.getSchemaRule()); 
            pushFollow(FOLLOW_1);
            ruleSchema();

            state._fsp--;

             after(grammarAccess.getSchemaRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSchema"


    // $ANTLR start "ruleSchema"
    // InternalSQL.g:62:1: ruleSchema : ( ( rule__Schema__TablesAssignment )* ) ;
    public final void ruleSchema() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:66:2: ( ( ( rule__Schema__TablesAssignment )* ) )
            // InternalSQL.g:67:2: ( ( rule__Schema__TablesAssignment )* )
            {
            // InternalSQL.g:67:2: ( ( rule__Schema__TablesAssignment )* )
            // InternalSQL.g:68:3: ( rule__Schema__TablesAssignment )*
            {
             before(grammarAccess.getSchemaAccess().getTablesAssignment()); 
            // InternalSQL.g:69:3: ( rule__Schema__TablesAssignment )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==19) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSQL.g:69:4: rule__Schema__TablesAssignment
            	    {
            	    pushFollow(FOLLOW_3);
            	    rule__Schema__TablesAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

             after(grammarAccess.getSchemaAccess().getTablesAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSchema"


    // $ANTLR start "entryRuleTable"
    // InternalSQL.g:78:1: entryRuleTable : ruleTable EOF ;
    public final void entryRuleTable() throws RecognitionException {
        try {
            // InternalSQL.g:79:1: ( ruleTable EOF )
            // InternalSQL.g:80:1: ruleTable EOF
            {
             before(grammarAccess.getTableRule()); 
            pushFollow(FOLLOW_1);
            ruleTable();

            state._fsp--;

             after(grammarAccess.getTableRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTable"


    // $ANTLR start "ruleTable"
    // InternalSQL.g:87:1: ruleTable : ( ( rule__Table__Group__0 ) ) ;
    public final void ruleTable() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:91:2: ( ( ( rule__Table__Group__0 ) ) )
            // InternalSQL.g:92:2: ( ( rule__Table__Group__0 ) )
            {
            // InternalSQL.g:92:2: ( ( rule__Table__Group__0 ) )
            // InternalSQL.g:93:3: ( rule__Table__Group__0 )
            {
             before(grammarAccess.getTableAccess().getGroup()); 
            // InternalSQL.g:94:3: ( rule__Table__Group__0 )
            // InternalSQL.g:94:4: rule__Table__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Table__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTableAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTable"


    // $ANTLR start "entryRuleColumn"
    // InternalSQL.g:103:1: entryRuleColumn : ruleColumn EOF ;
    public final void entryRuleColumn() throws RecognitionException {
        try {
            // InternalSQL.g:104:1: ( ruleColumn EOF )
            // InternalSQL.g:105:1: ruleColumn EOF
            {
             before(grammarAccess.getColumnRule()); 
            pushFollow(FOLLOW_1);
            ruleColumn();

            state._fsp--;

             after(grammarAccess.getColumnRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleColumn"


    // $ANTLR start "ruleColumn"
    // InternalSQL.g:112:1: ruleColumn : ( ( rule__Column__Group__0 ) ) ;
    public final void ruleColumn() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:116:2: ( ( ( rule__Column__Group__0 ) ) )
            // InternalSQL.g:117:2: ( ( rule__Column__Group__0 ) )
            {
            // InternalSQL.g:117:2: ( ( rule__Column__Group__0 ) )
            // InternalSQL.g:118:3: ( rule__Column__Group__0 )
            {
             before(grammarAccess.getColumnAccess().getGroup()); 
            // InternalSQL.g:119:3: ( rule__Column__Group__0 )
            // InternalSQL.g:119:4: rule__Column__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Column__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getColumnAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleColumn"


    // $ANTLR start "entryRuleVarchar"
    // InternalSQL.g:128:1: entryRuleVarchar : ruleVarchar EOF ;
    public final void entryRuleVarchar() throws RecognitionException {
        try {
            // InternalSQL.g:129:1: ( ruleVarchar EOF )
            // InternalSQL.g:130:1: ruleVarchar EOF
            {
             before(grammarAccess.getVarcharRule()); 
            pushFollow(FOLLOW_1);
            ruleVarchar();

            state._fsp--;

             after(grammarAccess.getVarcharRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVarchar"


    // $ANTLR start "ruleVarchar"
    // InternalSQL.g:137:1: ruleVarchar : ( ( rule__Varchar__Group__0 ) ) ;
    public final void ruleVarchar() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:141:2: ( ( ( rule__Varchar__Group__0 ) ) )
            // InternalSQL.g:142:2: ( ( rule__Varchar__Group__0 ) )
            {
            // InternalSQL.g:142:2: ( ( rule__Varchar__Group__0 ) )
            // InternalSQL.g:143:3: ( rule__Varchar__Group__0 )
            {
             before(grammarAccess.getVarcharAccess().getGroup()); 
            // InternalSQL.g:144:3: ( rule__Varchar__Group__0 )
            // InternalSQL.g:144:4: rule__Varchar__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Varchar__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getVarcharAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVarchar"


    // $ANTLR start "entryRuleConstraint"
    // InternalSQL.g:153:1: entryRuleConstraint : ruleConstraint EOF ;
    public final void entryRuleConstraint() throws RecognitionException {
        try {
            // InternalSQL.g:154:1: ( ruleConstraint EOF )
            // InternalSQL.g:155:1: ruleConstraint EOF
            {
             before(grammarAccess.getConstraintRule()); 
            pushFollow(FOLLOW_1);
            ruleConstraint();

            state._fsp--;

             after(grammarAccess.getConstraintRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleConstraint"


    // $ANTLR start "ruleConstraint"
    // InternalSQL.g:162:1: ruleConstraint : ( ( rule__Constraint__Alternatives ) ) ;
    public final void ruleConstraint() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:166:2: ( ( ( rule__Constraint__Alternatives ) ) )
            // InternalSQL.g:167:2: ( ( rule__Constraint__Alternatives ) )
            {
            // InternalSQL.g:167:2: ( ( rule__Constraint__Alternatives ) )
            // InternalSQL.g:168:3: ( rule__Constraint__Alternatives )
            {
             before(grammarAccess.getConstraintAccess().getAlternatives()); 
            // InternalSQL.g:169:3: ( rule__Constraint__Alternatives )
            // InternalSQL.g:169:4: rule__Constraint__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Constraint__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getConstraintAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleConstraint"


    // $ANTLR start "entryRulePrimaryAnnotation"
    // InternalSQL.g:178:1: entryRulePrimaryAnnotation : rulePrimaryAnnotation EOF ;
    public final void entryRulePrimaryAnnotation() throws RecognitionException {
        try {
            // InternalSQL.g:179:1: ( rulePrimaryAnnotation EOF )
            // InternalSQL.g:180:1: rulePrimaryAnnotation EOF
            {
             before(grammarAccess.getPrimaryAnnotationRule()); 
            pushFollow(FOLLOW_1);
            rulePrimaryAnnotation();

            state._fsp--;

             after(grammarAccess.getPrimaryAnnotationRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePrimaryAnnotation"


    // $ANTLR start "rulePrimaryAnnotation"
    // InternalSQL.g:187:1: rulePrimaryAnnotation : ( ( rule__PrimaryAnnotation__Group__0 ) ) ;
    public final void rulePrimaryAnnotation() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:191:2: ( ( ( rule__PrimaryAnnotation__Group__0 ) ) )
            // InternalSQL.g:192:2: ( ( rule__PrimaryAnnotation__Group__0 ) )
            {
            // InternalSQL.g:192:2: ( ( rule__PrimaryAnnotation__Group__0 ) )
            // InternalSQL.g:193:3: ( rule__PrimaryAnnotation__Group__0 )
            {
             before(grammarAccess.getPrimaryAnnotationAccess().getGroup()); 
            // InternalSQL.g:194:3: ( rule__PrimaryAnnotation__Group__0 )
            // InternalSQL.g:194:4: rule__PrimaryAnnotation__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__PrimaryAnnotation__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPrimaryAnnotationAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePrimaryAnnotation"


    // $ANTLR start "entryRuleUniqueAnnotation"
    // InternalSQL.g:203:1: entryRuleUniqueAnnotation : ruleUniqueAnnotation EOF ;
    public final void entryRuleUniqueAnnotation() throws RecognitionException {
        try {
            // InternalSQL.g:204:1: ( ruleUniqueAnnotation EOF )
            // InternalSQL.g:205:1: ruleUniqueAnnotation EOF
            {
             before(grammarAccess.getUniqueAnnotationRule()); 
            pushFollow(FOLLOW_1);
            ruleUniqueAnnotation();

            state._fsp--;

             after(grammarAccess.getUniqueAnnotationRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleUniqueAnnotation"


    // $ANTLR start "ruleUniqueAnnotation"
    // InternalSQL.g:212:1: ruleUniqueAnnotation : ( ( rule__UniqueAnnotation__Group__0 ) ) ;
    public final void ruleUniqueAnnotation() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:216:2: ( ( ( rule__UniqueAnnotation__Group__0 ) ) )
            // InternalSQL.g:217:2: ( ( rule__UniqueAnnotation__Group__0 ) )
            {
            // InternalSQL.g:217:2: ( ( rule__UniqueAnnotation__Group__0 ) )
            // InternalSQL.g:218:3: ( rule__UniqueAnnotation__Group__0 )
            {
             before(grammarAccess.getUniqueAnnotationAccess().getGroup()); 
            // InternalSQL.g:219:3: ( rule__UniqueAnnotation__Group__0 )
            // InternalSQL.g:219:4: rule__UniqueAnnotation__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__UniqueAnnotation__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getUniqueAnnotationAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleUniqueAnnotation"


    // $ANTLR start "entryRuleNotNull"
    // InternalSQL.g:228:1: entryRuleNotNull : ruleNotNull EOF ;
    public final void entryRuleNotNull() throws RecognitionException {
        try {
            // InternalSQL.g:229:1: ( ruleNotNull EOF )
            // InternalSQL.g:230:1: ruleNotNull EOF
            {
             before(grammarAccess.getNotNullRule()); 
            pushFollow(FOLLOW_1);
            ruleNotNull();

            state._fsp--;

             after(grammarAccess.getNotNullRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNotNull"


    // $ANTLR start "ruleNotNull"
    // InternalSQL.g:237:1: ruleNotNull : ( ( rule__NotNull__Group__0 ) ) ;
    public final void ruleNotNull() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:241:2: ( ( ( rule__NotNull__Group__0 ) ) )
            // InternalSQL.g:242:2: ( ( rule__NotNull__Group__0 ) )
            {
            // InternalSQL.g:242:2: ( ( rule__NotNull__Group__0 ) )
            // InternalSQL.g:243:3: ( rule__NotNull__Group__0 )
            {
             before(grammarAccess.getNotNullAccess().getGroup()); 
            // InternalSQL.g:244:3: ( rule__NotNull__Group__0 )
            // InternalSQL.g:244:4: rule__NotNull__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__NotNull__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getNotNullAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNotNull"


    // $ANTLR start "ruleAllTypes"
    // InternalSQL.g:253:1: ruleAllTypes : ( ( rule__AllTypes__Alternatives ) ) ;
    public final void ruleAllTypes() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:257:1: ( ( ( rule__AllTypes__Alternatives ) ) )
            // InternalSQL.g:258:2: ( ( rule__AllTypes__Alternatives ) )
            {
            // InternalSQL.g:258:2: ( ( rule__AllTypes__Alternatives ) )
            // InternalSQL.g:259:3: ( rule__AllTypes__Alternatives )
            {
             before(grammarAccess.getAllTypesAccess().getAlternatives()); 
            // InternalSQL.g:260:3: ( rule__AllTypes__Alternatives )
            // InternalSQL.g:260:4: rule__AllTypes__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__AllTypes__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getAllTypesAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAllTypes"


    // $ANTLR start "rule__Column__Alternatives_1"
    // InternalSQL.g:268:1: rule__Column__Alternatives_1 : ( ( ( rule__Column__SimpleTypeAssignment_1_0 ) ) | ( ( rule__Column__VarcharAssignment_1_1 ) ) );
    public final void rule__Column__Alternatives_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:272:1: ( ( ( rule__Column__SimpleTypeAssignment_1_0 ) ) | ( ( rule__Column__VarcharAssignment_1_1 ) ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=11 && LA2_0<=18)) ) {
                alt2=1;
            }
            else if ( (LA2_0==24) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalSQL.g:273:2: ( ( rule__Column__SimpleTypeAssignment_1_0 ) )
                    {
                    // InternalSQL.g:273:2: ( ( rule__Column__SimpleTypeAssignment_1_0 ) )
                    // InternalSQL.g:274:3: ( rule__Column__SimpleTypeAssignment_1_0 )
                    {
                     before(grammarAccess.getColumnAccess().getSimpleTypeAssignment_1_0()); 
                    // InternalSQL.g:275:3: ( rule__Column__SimpleTypeAssignment_1_0 )
                    // InternalSQL.g:275:4: rule__Column__SimpleTypeAssignment_1_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Column__SimpleTypeAssignment_1_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getColumnAccess().getSimpleTypeAssignment_1_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSQL.g:279:2: ( ( rule__Column__VarcharAssignment_1_1 ) )
                    {
                    // InternalSQL.g:279:2: ( ( rule__Column__VarcharAssignment_1_1 ) )
                    // InternalSQL.g:280:3: ( rule__Column__VarcharAssignment_1_1 )
                    {
                     before(grammarAccess.getColumnAccess().getVarcharAssignment_1_1()); 
                    // InternalSQL.g:281:3: ( rule__Column__VarcharAssignment_1_1 )
                    // InternalSQL.g:281:4: rule__Column__VarcharAssignment_1_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Column__VarcharAssignment_1_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getColumnAccess().getVarcharAssignment_1_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__Alternatives_1"


    // $ANTLR start "rule__Constraint__Alternatives"
    // InternalSQL.g:289:1: rule__Constraint__Alternatives : ( ( rulePrimaryAnnotation ) | ( ruleUniqueAnnotation ) | ( ruleNotNull ) );
    public final void rule__Constraint__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:293:1: ( ( rulePrimaryAnnotation ) | ( ruleUniqueAnnotation ) | ( ruleNotNull ) )
            int alt3=3;
            switch ( input.LA(1) ) {
            case 25:
                {
                alt3=1;
                }
                break;
            case 27:
                {
                alt3=2;
                }
                break;
            case 28:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // InternalSQL.g:294:2: ( rulePrimaryAnnotation )
                    {
                    // InternalSQL.g:294:2: ( rulePrimaryAnnotation )
                    // InternalSQL.g:295:3: rulePrimaryAnnotation
                    {
                     before(grammarAccess.getConstraintAccess().getPrimaryAnnotationParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    rulePrimaryAnnotation();

                    state._fsp--;

                     after(grammarAccess.getConstraintAccess().getPrimaryAnnotationParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSQL.g:300:2: ( ruleUniqueAnnotation )
                    {
                    // InternalSQL.g:300:2: ( ruleUniqueAnnotation )
                    // InternalSQL.g:301:3: ruleUniqueAnnotation
                    {
                     before(grammarAccess.getConstraintAccess().getUniqueAnnotationParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleUniqueAnnotation();

                    state._fsp--;

                     after(grammarAccess.getConstraintAccess().getUniqueAnnotationParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSQL.g:306:2: ( ruleNotNull )
                    {
                    // InternalSQL.g:306:2: ( ruleNotNull )
                    // InternalSQL.g:307:3: ruleNotNull
                    {
                     before(grammarAccess.getConstraintAccess().getNotNullParserRuleCall_2()); 
                    pushFollow(FOLLOW_2);
                    ruleNotNull();

                    state._fsp--;

                     after(grammarAccess.getConstraintAccess().getNotNullParserRuleCall_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Constraint__Alternatives"


    // $ANTLR start "rule__AllTypes__Alternatives"
    // InternalSQL.g:316:1: rule__AllTypes__Alternatives : ( ( ( 'INT' ) ) | ( ( 'BIGINT' ) ) | ( ( 'UUID' ) ) | ( ( 'DECIMAL' ) ) | ( ( 'TIMESTAMP' ) ) | ( ( 'DATE' ) ) | ( ( 'BOOLEAN' ) ) | ( ( 'TEXT' ) ) );
    public final void rule__AllTypes__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:320:1: ( ( ( 'INT' ) ) | ( ( 'BIGINT' ) ) | ( ( 'UUID' ) ) | ( ( 'DECIMAL' ) ) | ( ( 'TIMESTAMP' ) ) | ( ( 'DATE' ) ) | ( ( 'BOOLEAN' ) ) | ( ( 'TEXT' ) ) )
            int alt4=8;
            switch ( input.LA(1) ) {
            case 11:
                {
                alt4=1;
                }
                break;
            case 12:
                {
                alt4=2;
                }
                break;
            case 13:
                {
                alt4=3;
                }
                break;
            case 14:
                {
                alt4=4;
                }
                break;
            case 15:
                {
                alt4=5;
                }
                break;
            case 16:
                {
                alt4=6;
                }
                break;
            case 17:
                {
                alt4=7;
                }
                break;
            case 18:
                {
                alt4=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalSQL.g:321:2: ( ( 'INT' ) )
                    {
                    // InternalSQL.g:321:2: ( ( 'INT' ) )
                    // InternalSQL.g:322:3: ( 'INT' )
                    {
                     before(grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0()); 
                    // InternalSQL.g:323:3: ( 'INT' )
                    // InternalSQL.g:323:4: 'INT'
                    {
                    match(input,11,FOLLOW_2); 

                    }

                     after(grammarAccess.getAllTypesAccess().getINTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalSQL.g:327:2: ( ( 'BIGINT' ) )
                    {
                    // InternalSQL.g:327:2: ( ( 'BIGINT' ) )
                    // InternalSQL.g:328:3: ( 'BIGINT' )
                    {
                     before(grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1()); 
                    // InternalSQL.g:329:3: ( 'BIGINT' )
                    // InternalSQL.g:329:4: 'BIGINT'
                    {
                    match(input,12,FOLLOW_2); 

                    }

                     after(grammarAccess.getAllTypesAccess().getBIGINTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalSQL.g:333:2: ( ( 'UUID' ) )
                    {
                    // InternalSQL.g:333:2: ( ( 'UUID' ) )
                    // InternalSQL.g:334:3: ( 'UUID' )
                    {
                     before(grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2()); 
                    // InternalSQL.g:335:3: ( 'UUID' )
                    // InternalSQL.g:335:4: 'UUID'
                    {
                    match(input,13,FOLLOW_2); 

                    }

                     after(grammarAccess.getAllTypesAccess().getUUIDEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalSQL.g:339:2: ( ( 'DECIMAL' ) )
                    {
                    // InternalSQL.g:339:2: ( ( 'DECIMAL' ) )
                    // InternalSQL.g:340:3: ( 'DECIMAL' )
                    {
                     before(grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3()); 
                    // InternalSQL.g:341:3: ( 'DECIMAL' )
                    // InternalSQL.g:341:4: 'DECIMAL'
                    {
                    match(input,14,FOLLOW_2); 

                    }

                     after(grammarAccess.getAllTypesAccess().getDECIMALEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalSQL.g:345:2: ( ( 'TIMESTAMP' ) )
                    {
                    // InternalSQL.g:345:2: ( ( 'TIMESTAMP' ) )
                    // InternalSQL.g:346:3: ( 'TIMESTAMP' )
                    {
                     before(grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4()); 
                    // InternalSQL.g:347:3: ( 'TIMESTAMP' )
                    // InternalSQL.g:347:4: 'TIMESTAMP'
                    {
                    match(input,15,FOLLOW_2); 

                    }

                     after(grammarAccess.getAllTypesAccess().getTIMESTAMPEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalSQL.g:351:2: ( ( 'DATE' ) )
                    {
                    // InternalSQL.g:351:2: ( ( 'DATE' ) )
                    // InternalSQL.g:352:3: ( 'DATE' )
                    {
                     before(grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5()); 
                    // InternalSQL.g:353:3: ( 'DATE' )
                    // InternalSQL.g:353:4: 'DATE'
                    {
                    match(input,16,FOLLOW_2); 

                    }

                     after(grammarAccess.getAllTypesAccess().getDATEEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalSQL.g:357:2: ( ( 'BOOLEAN' ) )
                    {
                    // InternalSQL.g:357:2: ( ( 'BOOLEAN' ) )
                    // InternalSQL.g:358:3: ( 'BOOLEAN' )
                    {
                     before(grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6()); 
                    // InternalSQL.g:359:3: ( 'BOOLEAN' )
                    // InternalSQL.g:359:4: 'BOOLEAN'
                    {
                    match(input,17,FOLLOW_2); 

                    }

                     after(grammarAccess.getAllTypesAccess().getBOOLEANEnumLiteralDeclaration_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalSQL.g:363:2: ( ( 'TEXT' ) )
                    {
                    // InternalSQL.g:363:2: ( ( 'TEXT' ) )
                    // InternalSQL.g:364:3: ( 'TEXT' )
                    {
                     before(grammarAccess.getAllTypesAccess().getTEXTEnumLiteralDeclaration_7()); 
                    // InternalSQL.g:365:3: ( 'TEXT' )
                    // InternalSQL.g:365:4: 'TEXT'
                    {
                    match(input,18,FOLLOW_2); 

                    }

                     after(grammarAccess.getAllTypesAccess().getTEXTEnumLiteralDeclaration_7()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AllTypes__Alternatives"


    // $ANTLR start "rule__Table__Group__0"
    // InternalSQL.g:373:1: rule__Table__Group__0 : rule__Table__Group__0__Impl rule__Table__Group__1 ;
    public final void rule__Table__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:377:1: ( rule__Table__Group__0__Impl rule__Table__Group__1 )
            // InternalSQL.g:378:2: rule__Table__Group__0__Impl rule__Table__Group__1
            {
            pushFollow(FOLLOW_4);
            rule__Table__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__0"


    // $ANTLR start "rule__Table__Group__0__Impl"
    // InternalSQL.g:385:1: rule__Table__Group__0__Impl : ( 'CREATE' ) ;
    public final void rule__Table__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:389:1: ( ( 'CREATE' ) )
            // InternalSQL.g:390:1: ( 'CREATE' )
            {
            // InternalSQL.g:390:1: ( 'CREATE' )
            // InternalSQL.g:391:2: 'CREATE'
            {
             before(grammarAccess.getTableAccess().getCREATEKeyword_0()); 
            match(input,19,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getCREATEKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__0__Impl"


    // $ANTLR start "rule__Table__Group__1"
    // InternalSQL.g:400:1: rule__Table__Group__1 : rule__Table__Group__1__Impl rule__Table__Group__2 ;
    public final void rule__Table__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:404:1: ( rule__Table__Group__1__Impl rule__Table__Group__2 )
            // InternalSQL.g:405:2: rule__Table__Group__1__Impl rule__Table__Group__2
            {
            pushFollow(FOLLOW_5);
            rule__Table__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__1"


    // $ANTLR start "rule__Table__Group__1__Impl"
    // InternalSQL.g:412:1: rule__Table__Group__1__Impl : ( 'TABLE' ) ;
    public final void rule__Table__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:416:1: ( ( 'TABLE' ) )
            // InternalSQL.g:417:1: ( 'TABLE' )
            {
            // InternalSQL.g:417:1: ( 'TABLE' )
            // InternalSQL.g:418:2: 'TABLE'
            {
             before(grammarAccess.getTableAccess().getTABLEKeyword_1()); 
            match(input,20,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getTABLEKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__1__Impl"


    // $ANTLR start "rule__Table__Group__2"
    // InternalSQL.g:427:1: rule__Table__Group__2 : rule__Table__Group__2__Impl rule__Table__Group__3 ;
    public final void rule__Table__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:431:1: ( rule__Table__Group__2__Impl rule__Table__Group__3 )
            // InternalSQL.g:432:2: rule__Table__Group__2__Impl rule__Table__Group__3
            {
            pushFollow(FOLLOW_6);
            rule__Table__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__2"


    // $ANTLR start "rule__Table__Group__2__Impl"
    // InternalSQL.g:439:1: rule__Table__Group__2__Impl : ( ( rule__Table__NameAssignment_2 ) ) ;
    public final void rule__Table__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:443:1: ( ( ( rule__Table__NameAssignment_2 ) ) )
            // InternalSQL.g:444:1: ( ( rule__Table__NameAssignment_2 ) )
            {
            // InternalSQL.g:444:1: ( ( rule__Table__NameAssignment_2 ) )
            // InternalSQL.g:445:2: ( rule__Table__NameAssignment_2 )
            {
             before(grammarAccess.getTableAccess().getNameAssignment_2()); 
            // InternalSQL.g:446:2: ( rule__Table__NameAssignment_2 )
            // InternalSQL.g:446:3: rule__Table__NameAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Table__NameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getTableAccess().getNameAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__2__Impl"


    // $ANTLR start "rule__Table__Group__3"
    // InternalSQL.g:454:1: rule__Table__Group__3 : rule__Table__Group__3__Impl rule__Table__Group__4 ;
    public final void rule__Table__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:458:1: ( rule__Table__Group__3__Impl rule__Table__Group__4 )
            // InternalSQL.g:459:2: rule__Table__Group__3__Impl rule__Table__Group__4
            {
            pushFollow(FOLLOW_5);
            rule__Table__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__3"


    // $ANTLR start "rule__Table__Group__3__Impl"
    // InternalSQL.g:466:1: rule__Table__Group__3__Impl : ( '(' ) ;
    public final void rule__Table__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:470:1: ( ( '(' ) )
            // InternalSQL.g:471:1: ( '(' )
            {
            // InternalSQL.g:471:1: ( '(' )
            // InternalSQL.g:472:2: '('
            {
             before(grammarAccess.getTableAccess().getLeftParenthesisKeyword_3()); 
            match(input,21,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getLeftParenthesisKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__3__Impl"


    // $ANTLR start "rule__Table__Group__4"
    // InternalSQL.g:481:1: rule__Table__Group__4 : rule__Table__Group__4__Impl rule__Table__Group__5 ;
    public final void rule__Table__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:485:1: ( rule__Table__Group__4__Impl rule__Table__Group__5 )
            // InternalSQL.g:486:2: rule__Table__Group__4__Impl rule__Table__Group__5
            {
            pushFollow(FOLLOW_7);
            rule__Table__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__4"


    // $ANTLR start "rule__Table__Group__4__Impl"
    // InternalSQL.g:493:1: rule__Table__Group__4__Impl : ( ( rule__Table__ColumnsAssignment_4 ) ) ;
    public final void rule__Table__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:497:1: ( ( ( rule__Table__ColumnsAssignment_4 ) ) )
            // InternalSQL.g:498:1: ( ( rule__Table__ColumnsAssignment_4 ) )
            {
            // InternalSQL.g:498:1: ( ( rule__Table__ColumnsAssignment_4 ) )
            // InternalSQL.g:499:2: ( rule__Table__ColumnsAssignment_4 )
            {
             before(grammarAccess.getTableAccess().getColumnsAssignment_4()); 
            // InternalSQL.g:500:2: ( rule__Table__ColumnsAssignment_4 )
            // InternalSQL.g:500:3: rule__Table__ColumnsAssignment_4
            {
            pushFollow(FOLLOW_2);
            rule__Table__ColumnsAssignment_4();

            state._fsp--;


            }

             after(grammarAccess.getTableAccess().getColumnsAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__4__Impl"


    // $ANTLR start "rule__Table__Group__5"
    // InternalSQL.g:508:1: rule__Table__Group__5 : rule__Table__Group__5__Impl rule__Table__Group__6 ;
    public final void rule__Table__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:512:1: ( rule__Table__Group__5__Impl rule__Table__Group__6 )
            // InternalSQL.g:513:2: rule__Table__Group__5__Impl rule__Table__Group__6
            {
            pushFollow(FOLLOW_7);
            rule__Table__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__5"


    // $ANTLR start "rule__Table__Group__5__Impl"
    // InternalSQL.g:520:1: rule__Table__Group__5__Impl : ( ( rule__Table__Group_5__0 )* ) ;
    public final void rule__Table__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:524:1: ( ( ( rule__Table__Group_5__0 )* ) )
            // InternalSQL.g:525:1: ( ( rule__Table__Group_5__0 )* )
            {
            // InternalSQL.g:525:1: ( ( rule__Table__Group_5__0 )* )
            // InternalSQL.g:526:2: ( rule__Table__Group_5__0 )*
            {
             before(grammarAccess.getTableAccess().getGroup_5()); 
            // InternalSQL.g:527:2: ( rule__Table__Group_5__0 )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==23) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalSQL.g:527:3: rule__Table__Group_5__0
            	    {
            	    pushFollow(FOLLOW_8);
            	    rule__Table__Group_5__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

             after(grammarAccess.getTableAccess().getGroup_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__5__Impl"


    // $ANTLR start "rule__Table__Group__6"
    // InternalSQL.g:535:1: rule__Table__Group__6 : rule__Table__Group__6__Impl ;
    public final void rule__Table__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:539:1: ( rule__Table__Group__6__Impl )
            // InternalSQL.g:540:2: rule__Table__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Table__Group__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__6"


    // $ANTLR start "rule__Table__Group__6__Impl"
    // InternalSQL.g:546:1: rule__Table__Group__6__Impl : ( ')' ) ;
    public final void rule__Table__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:550:1: ( ( ')' ) )
            // InternalSQL.g:551:1: ( ')' )
            {
            // InternalSQL.g:551:1: ( ')' )
            // InternalSQL.g:552:2: ')'
            {
             before(grammarAccess.getTableAccess().getRightParenthesisKeyword_6()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getRightParenthesisKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group__6__Impl"


    // $ANTLR start "rule__Table__Group_5__0"
    // InternalSQL.g:562:1: rule__Table__Group_5__0 : rule__Table__Group_5__0__Impl rule__Table__Group_5__1 ;
    public final void rule__Table__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:566:1: ( rule__Table__Group_5__0__Impl rule__Table__Group_5__1 )
            // InternalSQL.g:567:2: rule__Table__Group_5__0__Impl rule__Table__Group_5__1
            {
            pushFollow(FOLLOW_5);
            rule__Table__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Table__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_5__0"


    // $ANTLR start "rule__Table__Group_5__0__Impl"
    // InternalSQL.g:574:1: rule__Table__Group_5__0__Impl : ( ',' ) ;
    public final void rule__Table__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:578:1: ( ( ',' ) )
            // InternalSQL.g:579:1: ( ',' )
            {
            // InternalSQL.g:579:1: ( ',' )
            // InternalSQL.g:580:2: ','
            {
             before(grammarAccess.getTableAccess().getCommaKeyword_5_0()); 
            match(input,23,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getCommaKeyword_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_5__0__Impl"


    // $ANTLR start "rule__Table__Group_5__1"
    // InternalSQL.g:589:1: rule__Table__Group_5__1 : rule__Table__Group_5__1__Impl ;
    public final void rule__Table__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:593:1: ( rule__Table__Group_5__1__Impl )
            // InternalSQL.g:594:2: rule__Table__Group_5__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Table__Group_5__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_5__1"


    // $ANTLR start "rule__Table__Group_5__1__Impl"
    // InternalSQL.g:600:1: rule__Table__Group_5__1__Impl : ( ( rule__Table__ColumnsAssignment_5_1 ) ) ;
    public final void rule__Table__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:604:1: ( ( ( rule__Table__ColumnsAssignment_5_1 ) ) )
            // InternalSQL.g:605:1: ( ( rule__Table__ColumnsAssignment_5_1 ) )
            {
            // InternalSQL.g:605:1: ( ( rule__Table__ColumnsAssignment_5_1 ) )
            // InternalSQL.g:606:2: ( rule__Table__ColumnsAssignment_5_1 )
            {
             before(grammarAccess.getTableAccess().getColumnsAssignment_5_1()); 
            // InternalSQL.g:607:2: ( rule__Table__ColumnsAssignment_5_1 )
            // InternalSQL.g:607:3: rule__Table__ColumnsAssignment_5_1
            {
            pushFollow(FOLLOW_2);
            rule__Table__ColumnsAssignment_5_1();

            state._fsp--;


            }

             after(grammarAccess.getTableAccess().getColumnsAssignment_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__Group_5__1__Impl"


    // $ANTLR start "rule__Column__Group__0"
    // InternalSQL.g:616:1: rule__Column__Group__0 : rule__Column__Group__0__Impl rule__Column__Group__1 ;
    public final void rule__Column__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:620:1: ( rule__Column__Group__0__Impl rule__Column__Group__1 )
            // InternalSQL.g:621:2: rule__Column__Group__0__Impl rule__Column__Group__1
            {
            pushFollow(FOLLOW_9);
            rule__Column__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Column__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__Group__0"


    // $ANTLR start "rule__Column__Group__0__Impl"
    // InternalSQL.g:628:1: rule__Column__Group__0__Impl : ( ( rule__Column__NameAssignment_0 ) ) ;
    public final void rule__Column__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:632:1: ( ( ( rule__Column__NameAssignment_0 ) ) )
            // InternalSQL.g:633:1: ( ( rule__Column__NameAssignment_0 ) )
            {
            // InternalSQL.g:633:1: ( ( rule__Column__NameAssignment_0 ) )
            // InternalSQL.g:634:2: ( rule__Column__NameAssignment_0 )
            {
             before(grammarAccess.getColumnAccess().getNameAssignment_0()); 
            // InternalSQL.g:635:2: ( rule__Column__NameAssignment_0 )
            // InternalSQL.g:635:3: rule__Column__NameAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Column__NameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getColumnAccess().getNameAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__Group__0__Impl"


    // $ANTLR start "rule__Column__Group__1"
    // InternalSQL.g:643:1: rule__Column__Group__1 : rule__Column__Group__1__Impl rule__Column__Group__2 ;
    public final void rule__Column__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:647:1: ( rule__Column__Group__1__Impl rule__Column__Group__2 )
            // InternalSQL.g:648:2: rule__Column__Group__1__Impl rule__Column__Group__2
            {
            pushFollow(FOLLOW_10);
            rule__Column__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Column__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__Group__1"


    // $ANTLR start "rule__Column__Group__1__Impl"
    // InternalSQL.g:655:1: rule__Column__Group__1__Impl : ( ( rule__Column__Alternatives_1 ) ) ;
    public final void rule__Column__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:659:1: ( ( ( rule__Column__Alternatives_1 ) ) )
            // InternalSQL.g:660:1: ( ( rule__Column__Alternatives_1 ) )
            {
            // InternalSQL.g:660:1: ( ( rule__Column__Alternatives_1 ) )
            // InternalSQL.g:661:2: ( rule__Column__Alternatives_1 )
            {
             before(grammarAccess.getColumnAccess().getAlternatives_1()); 
            // InternalSQL.g:662:2: ( rule__Column__Alternatives_1 )
            // InternalSQL.g:662:3: rule__Column__Alternatives_1
            {
            pushFollow(FOLLOW_2);
            rule__Column__Alternatives_1();

            state._fsp--;


            }

             after(grammarAccess.getColumnAccess().getAlternatives_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__Group__1__Impl"


    // $ANTLR start "rule__Column__Group__2"
    // InternalSQL.g:670:1: rule__Column__Group__2 : rule__Column__Group__2__Impl ;
    public final void rule__Column__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:674:1: ( rule__Column__Group__2__Impl )
            // InternalSQL.g:675:2: rule__Column__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Column__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__Group__2"


    // $ANTLR start "rule__Column__Group__2__Impl"
    // InternalSQL.g:681:1: rule__Column__Group__2__Impl : ( ( rule__Column__ConstraintAssignment_2 )* ) ;
    public final void rule__Column__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:685:1: ( ( ( rule__Column__ConstraintAssignment_2 )* ) )
            // InternalSQL.g:686:1: ( ( rule__Column__ConstraintAssignment_2 )* )
            {
            // InternalSQL.g:686:1: ( ( rule__Column__ConstraintAssignment_2 )* )
            // InternalSQL.g:687:2: ( rule__Column__ConstraintAssignment_2 )*
            {
             before(grammarAccess.getColumnAccess().getConstraintAssignment_2()); 
            // InternalSQL.g:688:2: ( rule__Column__ConstraintAssignment_2 )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==25||(LA6_0>=27 && LA6_0<=28)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalSQL.g:688:3: rule__Column__ConstraintAssignment_2
            	    {
            	    pushFollow(FOLLOW_11);
            	    rule__Column__ConstraintAssignment_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

             after(grammarAccess.getColumnAccess().getConstraintAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__Group__2__Impl"


    // $ANTLR start "rule__Varchar__Group__0"
    // InternalSQL.g:697:1: rule__Varchar__Group__0 : rule__Varchar__Group__0__Impl rule__Varchar__Group__1 ;
    public final void rule__Varchar__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:701:1: ( rule__Varchar__Group__0__Impl rule__Varchar__Group__1 )
            // InternalSQL.g:702:2: rule__Varchar__Group__0__Impl rule__Varchar__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__Varchar__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Varchar__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Varchar__Group__0"


    // $ANTLR start "rule__Varchar__Group__0__Impl"
    // InternalSQL.g:709:1: rule__Varchar__Group__0__Impl : ( 'VARCHAR' ) ;
    public final void rule__Varchar__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:713:1: ( ( 'VARCHAR' ) )
            // InternalSQL.g:714:1: ( 'VARCHAR' )
            {
            // InternalSQL.g:714:1: ( 'VARCHAR' )
            // InternalSQL.g:715:2: 'VARCHAR'
            {
             before(grammarAccess.getVarcharAccess().getVARCHARKeyword_0()); 
            match(input,24,FOLLOW_2); 
             after(grammarAccess.getVarcharAccess().getVARCHARKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Varchar__Group__0__Impl"


    // $ANTLR start "rule__Varchar__Group__1"
    // InternalSQL.g:724:1: rule__Varchar__Group__1 : rule__Varchar__Group__1__Impl rule__Varchar__Group__2 ;
    public final void rule__Varchar__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:728:1: ( rule__Varchar__Group__1__Impl rule__Varchar__Group__2 )
            // InternalSQL.g:729:2: rule__Varchar__Group__1__Impl rule__Varchar__Group__2
            {
            pushFollow(FOLLOW_12);
            rule__Varchar__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Varchar__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Varchar__Group__1"


    // $ANTLR start "rule__Varchar__Group__1__Impl"
    // InternalSQL.g:736:1: rule__Varchar__Group__1__Impl : ( '(' ) ;
    public final void rule__Varchar__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:740:1: ( ( '(' ) )
            // InternalSQL.g:741:1: ( '(' )
            {
            // InternalSQL.g:741:1: ( '(' )
            // InternalSQL.g:742:2: '('
            {
             before(grammarAccess.getVarcharAccess().getLeftParenthesisKeyword_1()); 
            match(input,21,FOLLOW_2); 
             after(grammarAccess.getVarcharAccess().getLeftParenthesisKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Varchar__Group__1__Impl"


    // $ANTLR start "rule__Varchar__Group__2"
    // InternalSQL.g:751:1: rule__Varchar__Group__2 : rule__Varchar__Group__2__Impl rule__Varchar__Group__3 ;
    public final void rule__Varchar__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:755:1: ( rule__Varchar__Group__2__Impl rule__Varchar__Group__3 )
            // InternalSQL.g:756:2: rule__Varchar__Group__2__Impl rule__Varchar__Group__3
            {
            pushFollow(FOLLOW_13);
            rule__Varchar__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Varchar__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Varchar__Group__2"


    // $ANTLR start "rule__Varchar__Group__2__Impl"
    // InternalSQL.g:763:1: rule__Varchar__Group__2__Impl : ( ( rule__Varchar__SizeAssignment_2 ) ) ;
    public final void rule__Varchar__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:767:1: ( ( ( rule__Varchar__SizeAssignment_2 ) ) )
            // InternalSQL.g:768:1: ( ( rule__Varchar__SizeAssignment_2 ) )
            {
            // InternalSQL.g:768:1: ( ( rule__Varchar__SizeAssignment_2 ) )
            // InternalSQL.g:769:2: ( rule__Varchar__SizeAssignment_2 )
            {
             before(grammarAccess.getVarcharAccess().getSizeAssignment_2()); 
            // InternalSQL.g:770:2: ( rule__Varchar__SizeAssignment_2 )
            // InternalSQL.g:770:3: rule__Varchar__SizeAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Varchar__SizeAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getVarcharAccess().getSizeAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Varchar__Group__2__Impl"


    // $ANTLR start "rule__Varchar__Group__3"
    // InternalSQL.g:778:1: rule__Varchar__Group__3 : rule__Varchar__Group__3__Impl ;
    public final void rule__Varchar__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:782:1: ( rule__Varchar__Group__3__Impl )
            // InternalSQL.g:783:2: rule__Varchar__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Varchar__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Varchar__Group__3"


    // $ANTLR start "rule__Varchar__Group__3__Impl"
    // InternalSQL.g:789:1: rule__Varchar__Group__3__Impl : ( ')' ) ;
    public final void rule__Varchar__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:793:1: ( ( ')' ) )
            // InternalSQL.g:794:1: ( ')' )
            {
            // InternalSQL.g:794:1: ( ')' )
            // InternalSQL.g:795:2: ')'
            {
             before(grammarAccess.getVarcharAccess().getRightParenthesisKeyword_3()); 
            match(input,22,FOLLOW_2); 
             after(grammarAccess.getVarcharAccess().getRightParenthesisKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Varchar__Group__3__Impl"


    // $ANTLR start "rule__PrimaryAnnotation__Group__0"
    // InternalSQL.g:805:1: rule__PrimaryAnnotation__Group__0 : rule__PrimaryAnnotation__Group__0__Impl rule__PrimaryAnnotation__Group__1 ;
    public final void rule__PrimaryAnnotation__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:809:1: ( rule__PrimaryAnnotation__Group__0__Impl rule__PrimaryAnnotation__Group__1 )
            // InternalSQL.g:810:2: rule__PrimaryAnnotation__Group__0__Impl rule__PrimaryAnnotation__Group__1
            {
            pushFollow(FOLLOW_14);
            rule__PrimaryAnnotation__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PrimaryAnnotation__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryAnnotation__Group__0"


    // $ANTLR start "rule__PrimaryAnnotation__Group__0__Impl"
    // InternalSQL.g:817:1: rule__PrimaryAnnotation__Group__0__Impl : ( () ) ;
    public final void rule__PrimaryAnnotation__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:821:1: ( ( () ) )
            // InternalSQL.g:822:1: ( () )
            {
            // InternalSQL.g:822:1: ( () )
            // InternalSQL.g:823:2: ()
            {
             before(grammarAccess.getPrimaryAnnotationAccess().getPrimaryAnnotationAction_0()); 
            // InternalSQL.g:824:2: ()
            // InternalSQL.g:824:3: 
            {
            }

             after(grammarAccess.getPrimaryAnnotationAccess().getPrimaryAnnotationAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryAnnotation__Group__0__Impl"


    // $ANTLR start "rule__PrimaryAnnotation__Group__1"
    // InternalSQL.g:832:1: rule__PrimaryAnnotation__Group__1 : rule__PrimaryAnnotation__Group__1__Impl rule__PrimaryAnnotation__Group__2 ;
    public final void rule__PrimaryAnnotation__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:836:1: ( rule__PrimaryAnnotation__Group__1__Impl rule__PrimaryAnnotation__Group__2 )
            // InternalSQL.g:837:2: rule__PrimaryAnnotation__Group__1__Impl rule__PrimaryAnnotation__Group__2
            {
            pushFollow(FOLLOW_15);
            rule__PrimaryAnnotation__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__PrimaryAnnotation__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryAnnotation__Group__1"


    // $ANTLR start "rule__PrimaryAnnotation__Group__1__Impl"
    // InternalSQL.g:844:1: rule__PrimaryAnnotation__Group__1__Impl : ( 'PRIMARY' ) ;
    public final void rule__PrimaryAnnotation__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:848:1: ( ( 'PRIMARY' ) )
            // InternalSQL.g:849:1: ( 'PRIMARY' )
            {
            // InternalSQL.g:849:1: ( 'PRIMARY' )
            // InternalSQL.g:850:2: 'PRIMARY'
            {
             before(grammarAccess.getPrimaryAnnotationAccess().getPRIMARYKeyword_1()); 
            match(input,25,FOLLOW_2); 
             after(grammarAccess.getPrimaryAnnotationAccess().getPRIMARYKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryAnnotation__Group__1__Impl"


    // $ANTLR start "rule__PrimaryAnnotation__Group__2"
    // InternalSQL.g:859:1: rule__PrimaryAnnotation__Group__2 : rule__PrimaryAnnotation__Group__2__Impl ;
    public final void rule__PrimaryAnnotation__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:863:1: ( rule__PrimaryAnnotation__Group__2__Impl )
            // InternalSQL.g:864:2: rule__PrimaryAnnotation__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__PrimaryAnnotation__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryAnnotation__Group__2"


    // $ANTLR start "rule__PrimaryAnnotation__Group__2__Impl"
    // InternalSQL.g:870:1: rule__PrimaryAnnotation__Group__2__Impl : ( 'KEY' ) ;
    public final void rule__PrimaryAnnotation__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:874:1: ( ( 'KEY' ) )
            // InternalSQL.g:875:1: ( 'KEY' )
            {
            // InternalSQL.g:875:1: ( 'KEY' )
            // InternalSQL.g:876:2: 'KEY'
            {
             before(grammarAccess.getPrimaryAnnotationAccess().getKEYKeyword_2()); 
            match(input,26,FOLLOW_2); 
             after(grammarAccess.getPrimaryAnnotationAccess().getKEYKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PrimaryAnnotation__Group__2__Impl"


    // $ANTLR start "rule__UniqueAnnotation__Group__0"
    // InternalSQL.g:886:1: rule__UniqueAnnotation__Group__0 : rule__UniqueAnnotation__Group__0__Impl rule__UniqueAnnotation__Group__1 ;
    public final void rule__UniqueAnnotation__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:890:1: ( rule__UniqueAnnotation__Group__0__Impl rule__UniqueAnnotation__Group__1 )
            // InternalSQL.g:891:2: rule__UniqueAnnotation__Group__0__Impl rule__UniqueAnnotation__Group__1
            {
            pushFollow(FOLLOW_16);
            rule__UniqueAnnotation__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__UniqueAnnotation__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UniqueAnnotation__Group__0"


    // $ANTLR start "rule__UniqueAnnotation__Group__0__Impl"
    // InternalSQL.g:898:1: rule__UniqueAnnotation__Group__0__Impl : ( () ) ;
    public final void rule__UniqueAnnotation__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:902:1: ( ( () ) )
            // InternalSQL.g:903:1: ( () )
            {
            // InternalSQL.g:903:1: ( () )
            // InternalSQL.g:904:2: ()
            {
             before(grammarAccess.getUniqueAnnotationAccess().getUniqueAnnotationAction_0()); 
            // InternalSQL.g:905:2: ()
            // InternalSQL.g:905:3: 
            {
            }

             after(grammarAccess.getUniqueAnnotationAccess().getUniqueAnnotationAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UniqueAnnotation__Group__0__Impl"


    // $ANTLR start "rule__UniqueAnnotation__Group__1"
    // InternalSQL.g:913:1: rule__UniqueAnnotation__Group__1 : rule__UniqueAnnotation__Group__1__Impl ;
    public final void rule__UniqueAnnotation__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:917:1: ( rule__UniqueAnnotation__Group__1__Impl )
            // InternalSQL.g:918:2: rule__UniqueAnnotation__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__UniqueAnnotation__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UniqueAnnotation__Group__1"


    // $ANTLR start "rule__UniqueAnnotation__Group__1__Impl"
    // InternalSQL.g:924:1: rule__UniqueAnnotation__Group__1__Impl : ( 'UNIQUE' ) ;
    public final void rule__UniqueAnnotation__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:928:1: ( ( 'UNIQUE' ) )
            // InternalSQL.g:929:1: ( 'UNIQUE' )
            {
            // InternalSQL.g:929:1: ( 'UNIQUE' )
            // InternalSQL.g:930:2: 'UNIQUE'
            {
             before(grammarAccess.getUniqueAnnotationAccess().getUNIQUEKeyword_1()); 
            match(input,27,FOLLOW_2); 
             after(grammarAccess.getUniqueAnnotationAccess().getUNIQUEKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__UniqueAnnotation__Group__1__Impl"


    // $ANTLR start "rule__NotNull__Group__0"
    // InternalSQL.g:940:1: rule__NotNull__Group__0 : rule__NotNull__Group__0__Impl rule__NotNull__Group__1 ;
    public final void rule__NotNull__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:944:1: ( rule__NotNull__Group__0__Impl rule__NotNull__Group__1 )
            // InternalSQL.g:945:2: rule__NotNull__Group__0__Impl rule__NotNull__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__NotNull__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__NotNull__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NotNull__Group__0"


    // $ANTLR start "rule__NotNull__Group__0__Impl"
    // InternalSQL.g:952:1: rule__NotNull__Group__0__Impl : ( () ) ;
    public final void rule__NotNull__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:956:1: ( ( () ) )
            // InternalSQL.g:957:1: ( () )
            {
            // InternalSQL.g:957:1: ( () )
            // InternalSQL.g:958:2: ()
            {
             before(grammarAccess.getNotNullAccess().getNotNullAction_0()); 
            // InternalSQL.g:959:2: ()
            // InternalSQL.g:959:3: 
            {
            }

             after(grammarAccess.getNotNullAccess().getNotNullAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NotNull__Group__0__Impl"


    // $ANTLR start "rule__NotNull__Group__1"
    // InternalSQL.g:967:1: rule__NotNull__Group__1 : rule__NotNull__Group__1__Impl rule__NotNull__Group__2 ;
    public final void rule__NotNull__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:971:1: ( rule__NotNull__Group__1__Impl rule__NotNull__Group__2 )
            // InternalSQL.g:972:2: rule__NotNull__Group__1__Impl rule__NotNull__Group__2
            {
            pushFollow(FOLLOW_17);
            rule__NotNull__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__NotNull__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NotNull__Group__1"


    // $ANTLR start "rule__NotNull__Group__1__Impl"
    // InternalSQL.g:979:1: rule__NotNull__Group__1__Impl : ( 'NOT' ) ;
    public final void rule__NotNull__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:983:1: ( ( 'NOT' ) )
            // InternalSQL.g:984:1: ( 'NOT' )
            {
            // InternalSQL.g:984:1: ( 'NOT' )
            // InternalSQL.g:985:2: 'NOT'
            {
             before(grammarAccess.getNotNullAccess().getNOTKeyword_1()); 
            match(input,28,FOLLOW_2); 
             after(grammarAccess.getNotNullAccess().getNOTKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NotNull__Group__1__Impl"


    // $ANTLR start "rule__NotNull__Group__2"
    // InternalSQL.g:994:1: rule__NotNull__Group__2 : rule__NotNull__Group__2__Impl ;
    public final void rule__NotNull__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:998:1: ( rule__NotNull__Group__2__Impl )
            // InternalSQL.g:999:2: rule__NotNull__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__NotNull__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NotNull__Group__2"


    // $ANTLR start "rule__NotNull__Group__2__Impl"
    // InternalSQL.g:1005:1: rule__NotNull__Group__2__Impl : ( 'NULL' ) ;
    public final void rule__NotNull__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:1009:1: ( ( 'NULL' ) )
            // InternalSQL.g:1010:1: ( 'NULL' )
            {
            // InternalSQL.g:1010:1: ( 'NULL' )
            // InternalSQL.g:1011:2: 'NULL'
            {
             before(grammarAccess.getNotNullAccess().getNULLKeyword_2()); 
            match(input,29,FOLLOW_2); 
             after(grammarAccess.getNotNullAccess().getNULLKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NotNull__Group__2__Impl"


    // $ANTLR start "rule__Schema__TablesAssignment"
    // InternalSQL.g:1021:1: rule__Schema__TablesAssignment : ( ruleTable ) ;
    public final void rule__Schema__TablesAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:1025:1: ( ( ruleTable ) )
            // InternalSQL.g:1026:2: ( ruleTable )
            {
            // InternalSQL.g:1026:2: ( ruleTable )
            // InternalSQL.g:1027:3: ruleTable
            {
             before(grammarAccess.getSchemaAccess().getTablesTableParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleTable();

            state._fsp--;

             after(grammarAccess.getSchemaAccess().getTablesTableParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Schema__TablesAssignment"


    // $ANTLR start "rule__Table__NameAssignment_2"
    // InternalSQL.g:1036:1: rule__Table__NameAssignment_2 : ( RULE_ID ) ;
    public final void rule__Table__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:1040:1: ( ( RULE_ID ) )
            // InternalSQL.g:1041:2: ( RULE_ID )
            {
            // InternalSQL.g:1041:2: ( RULE_ID )
            // InternalSQL.g:1042:3: RULE_ID
            {
             before(grammarAccess.getTableAccess().getNameIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getTableAccess().getNameIDTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__NameAssignment_2"


    // $ANTLR start "rule__Table__ColumnsAssignment_4"
    // InternalSQL.g:1051:1: rule__Table__ColumnsAssignment_4 : ( ruleColumn ) ;
    public final void rule__Table__ColumnsAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:1055:1: ( ( ruleColumn ) )
            // InternalSQL.g:1056:2: ( ruleColumn )
            {
            // InternalSQL.g:1056:2: ( ruleColumn )
            // InternalSQL.g:1057:3: ruleColumn
            {
             before(grammarAccess.getTableAccess().getColumnsColumnParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleColumn();

            state._fsp--;

             after(grammarAccess.getTableAccess().getColumnsColumnParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__ColumnsAssignment_4"


    // $ANTLR start "rule__Table__ColumnsAssignment_5_1"
    // InternalSQL.g:1066:1: rule__Table__ColumnsAssignment_5_1 : ( ruleColumn ) ;
    public final void rule__Table__ColumnsAssignment_5_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:1070:1: ( ( ruleColumn ) )
            // InternalSQL.g:1071:2: ( ruleColumn )
            {
            // InternalSQL.g:1071:2: ( ruleColumn )
            // InternalSQL.g:1072:3: ruleColumn
            {
             before(grammarAccess.getTableAccess().getColumnsColumnParserRuleCall_5_1_0()); 
            pushFollow(FOLLOW_2);
            ruleColumn();

            state._fsp--;

             after(grammarAccess.getTableAccess().getColumnsColumnParserRuleCall_5_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Table__ColumnsAssignment_5_1"


    // $ANTLR start "rule__Column__NameAssignment_0"
    // InternalSQL.g:1081:1: rule__Column__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__Column__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:1085:1: ( ( RULE_ID ) )
            // InternalSQL.g:1086:2: ( RULE_ID )
            {
            // InternalSQL.g:1086:2: ( RULE_ID )
            // InternalSQL.g:1087:3: RULE_ID
            {
             before(grammarAccess.getColumnAccess().getNameIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getColumnAccess().getNameIDTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__NameAssignment_0"


    // $ANTLR start "rule__Column__SimpleTypeAssignment_1_0"
    // InternalSQL.g:1096:1: rule__Column__SimpleTypeAssignment_1_0 : ( ruleAllTypes ) ;
    public final void rule__Column__SimpleTypeAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:1100:1: ( ( ruleAllTypes ) )
            // InternalSQL.g:1101:2: ( ruleAllTypes )
            {
            // InternalSQL.g:1101:2: ( ruleAllTypes )
            // InternalSQL.g:1102:3: ruleAllTypes
            {
             before(grammarAccess.getColumnAccess().getSimpleTypeAllTypesEnumRuleCall_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleAllTypes();

            state._fsp--;

             after(grammarAccess.getColumnAccess().getSimpleTypeAllTypesEnumRuleCall_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__SimpleTypeAssignment_1_0"


    // $ANTLR start "rule__Column__VarcharAssignment_1_1"
    // InternalSQL.g:1111:1: rule__Column__VarcharAssignment_1_1 : ( ruleVarchar ) ;
    public final void rule__Column__VarcharAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:1115:1: ( ( ruleVarchar ) )
            // InternalSQL.g:1116:2: ( ruleVarchar )
            {
            // InternalSQL.g:1116:2: ( ruleVarchar )
            // InternalSQL.g:1117:3: ruleVarchar
            {
             before(grammarAccess.getColumnAccess().getVarcharVarcharParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVarchar();

            state._fsp--;

             after(grammarAccess.getColumnAccess().getVarcharVarcharParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__VarcharAssignment_1_1"


    // $ANTLR start "rule__Column__ConstraintAssignment_2"
    // InternalSQL.g:1126:1: rule__Column__ConstraintAssignment_2 : ( ruleConstraint ) ;
    public final void rule__Column__ConstraintAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:1130:1: ( ( ruleConstraint ) )
            // InternalSQL.g:1131:2: ( ruleConstraint )
            {
            // InternalSQL.g:1131:2: ( ruleConstraint )
            // InternalSQL.g:1132:3: ruleConstraint
            {
             before(grammarAccess.getColumnAccess().getConstraintConstraintParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleConstraint();

            state._fsp--;

             after(grammarAccess.getColumnAccess().getConstraintConstraintParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Column__ConstraintAssignment_2"


    // $ANTLR start "rule__Varchar__SizeAssignment_2"
    // InternalSQL.g:1141:1: rule__Varchar__SizeAssignment_2 : ( RULE_INT ) ;
    public final void rule__Varchar__SizeAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalSQL.g:1145:1: ( ( RULE_INT ) )
            // InternalSQL.g:1146:2: ( RULE_INT )
            {
            // InternalSQL.g:1146:2: ( RULE_INT )
            // InternalSQL.g:1147:3: RULE_INT
            {
             before(grammarAccess.getVarcharAccess().getSizeINTTerminalRuleCall_2_0()); 
            match(input,RULE_INT,FOLLOW_2); 
             after(grammarAccess.getVarcharAccess().getSizeINTTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Varchar__SizeAssignment_2"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000C00000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x000000000107F800L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x000000001A000000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x000000001A000002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000020000000L});

}