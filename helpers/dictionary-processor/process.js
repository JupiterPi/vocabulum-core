const fs = require("fs")

// read portions

const portions = new Map()

fs.readdirSync("./dictionary/").forEach(file => {
    fs.readFileSync("./dictionary/" + file).toString().replaceAll("\r\n", "\n").split("\n").forEach(line => {
        if (!(!line || line.startsWith("#") || line.startsWith("//"))) {

            const portion = line.split(":")[line.split(":").length - 1]
            const vocabulary = line.substring(0, line.length - (portion.length+1)).trim()

            const vocabularies = portions.get(portion) ?? []
            vocabularies.push(vocabulary)
            portions.set(portion, vocabularies)
            
        }
    })
})

// create folder

if (fs.existsSync("./portions")) {
    fs.rmSync("./portions", {recursive: true})
}
fs.mkdirSync("./portions")

// output to files

const allPortions = []
portions.forEach( (vocabularies, portion) => {
  allPortions.push({
    name: portion,
    i18n: "de",
    vocabularies: vocabularies
  })
})

allPortions.forEach(portion => {
  fs.writeFileSync("./portions/" + portion.name + ".json", JSON.stringify(portion, null, 2))
})

fs.writeFileSync("./portions.json", JSON.stringify(allPortions, null, 2))