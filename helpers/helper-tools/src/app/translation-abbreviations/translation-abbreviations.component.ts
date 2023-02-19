import {Component} from '@angular/core';

@Component({
  selector: 'app-translation-abbreviations',
  templateUrl: './translation-abbreviations.component.html',
  styleUrls: ['./translation-abbreviations.component.css']
})
export class TranslationAbbreviationsComponent {
  inputString = "";
  outputString = "";

  generate() {
    const json = [];
    for (let line of this.inputString.split("\n")) {
      if (line === "") continue;

      let optional = false;
      if (line.indexOf("?") === 0) {
        line = line.substring(1);
        optional = true;
      }

      const parts = line.split(":");
      const abbreviation = {
        primaryKeyword: parts[0],
        secondaryKeywords: parts[1] === "-" ? [] : parts[1].split(","),
        optional
      };
      json.push(abbreviation);
    }
    this.outputString = JSON.stringify(json, null, 2);
  }
}
