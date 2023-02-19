import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app-root/app.component';
import {FormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";
import {SchemaJsonEditorComponent} from './schema-json-editor/schema-json-editor.component';
import {TranslationAbbreviationsComponent} from './translation-abbreviations/translation-abbreviations.component';

@NgModule({
  declarations: [
    AppComponent,
    SchemaJsonEditorComponent,
    TranslationAbbreviationsComponent
  ],
    imports: [
        BrowserModule,
        FormsModule,
        RouterModule.forRoot([
          {path: "", redirectTo: "/schema-json-editor", pathMatch: "full"},
          {path: "schema-json-editor", component: SchemaJsonEditorComponent},
          {path: "translation-abbreviations", component: TranslationAbbreviationsComponent}
        ]),
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
