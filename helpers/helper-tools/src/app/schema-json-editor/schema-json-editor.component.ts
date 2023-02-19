import {Component} from '@angular/core';

type Field = string | string[];

@Component({
  selector: 'app-schema-json-editor',
  templateUrl: './schema-json-editor.component.html',
  styleUrls: ['./schema-json-editor.component.css']
})
export class SchemaJsonEditorComponent {
  showFields = true;

  s: any = {};

  fields: Field[] = [];
  isHeader(field: Field) {
    return typeof field == "string";
  }
  getFields(fields: Field) {
    return fields as string[];
  }

  fieldsSchema = "";
  readFieldsSchema() {
    this.fields = JSON.parse(this.fieldsSchema);
  }

  inputDocument = "";
  readDocument() {
    this.s = JSON.parse(this.inputDocument);
  }

  setProperty(name: string, content: string) {
    this.setPropertyOn(this.s, name, content);
  }
  private setPropertyOn(obj: any, name: string, content: string) {
    const parts = name.split(".");
    if (parts.length > 1) {
      this.setPropertyOn(obj[parts[0]], parts.slice(1).join("."), content);
    } else {
      obj[parts[0]] = content;
    }
  }

  getProperty(name: string) {
    return this.getPropertyOn(this.s, name) ?? '';
  }
  private getPropertyOn(obj: any, name: string): string {
    const parts = name.split(".");
    if (parts.length > 1) {
      return this.getPropertyOn(obj[parts[0]], parts.slice(1).join("."));
    } else {
      return obj[parts[0]];
    }
  }

  stringify() {
    return JSON.stringify(this.s, null, 2);
  }

  getPathEnding(path: string) {
    const parts = path.split(".");
    return parts[parts.length-1];
  }

  eventResult(event: Event) {
    return ((event as InputEvent).target as HTMLInputElement).value;
  }
}
