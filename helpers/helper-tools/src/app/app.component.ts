import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  s: any = {
    "imperative": {},
    "infinitive": {
      "present": {},
      "perfect": {},
      "future": {}
    },
    "basic": {
      "active": {
        "present": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        },
        "imperfect": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        },
        "perfect": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        },
        "pluperfect": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        },
        "future_i": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        },
        "future_ii": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        }
      },
      "passive": {
        "present": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        },
        "imperfect": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        },
        "perfect": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        },
        "pluperfect": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        },
        "future_i": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        },
        "future_ii": {
          "indicative": {
            "sg": {},
            "pl": {}
          },
          "conjunctive": {
            "sg": {},
            "pl": {}
          }
        }
      }
    },
    "noun_like": {}
  };

  fields = [
    {
      "header": "Imperative"
    },
    {
      "name": "sg",
      "path": "imperative.sg"
    }
  ];

  setProperty(name: string, content: string) {
    console.log(name);
    console.log(content);
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

  stringify() {
    return JSON.stringify(this.s, null, 2);
  }
}
