const fs = require("fs")

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

if (fs.existsSync("./portions")) {
    fs.rmSync("./portions", {recursive: true})
}
fs.mkdirSync("./portions")

portions.forEach( (vocabularies, portion) => {
    fs.writeFileSync("./portions/" + portion + ".json", JSON.stringify(
        {
            name: portion,
            i18n: "de",
            vocabularies: vocabularies
        }
    , null, 2))
})