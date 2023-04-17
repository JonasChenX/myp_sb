import com.intellij.database.model.DasTable
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

// 資料類型映射
typeMapping = [
        (~/(?i)bigint/)            : "Long",
        (~/(?i)int/)               : "Integer",
        (~/(?i)float|double|real/) : "Double",
        (~/(?i)decimal/)           : "BigDecimal",
        (~/(?i)datetime|timestamp/): "Date",
        (~/(?i)date/)              : "Date",
        (~/(?i)time/)              : "Date",
        (~/(?i)/)                  : "String",
        (~/(?i)point/)             : "Point",
]
// 通用基於字段，不需要導入，直接繼承基礎實體類即可
//baseFields = ["id", "createTime"]
baseFields = []
FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    SELECTION.filter { it instanceof DasTable }.each { generate(it, dir) }
}

def generate(table, dir) {
    def className = javaName(table.getName(), true, true)
    def fields = calcFields(table)
    def packageName = getPackageName(dir)
    new File(dir, className + ".java").withPrintWriter("UTF-8") { out -> generate(out, className, fields, table.getName(),packageName) }
}

def generate(out, className, fields, tableName,packageName) {
    out.println "package $packageName"
    out.println ""
    // 引入import
    out.println "import lombok.AllArgsConstructor;"
    out.println "import lombok.Data;"
    out.println "import lombok.NoArgsConstructor;"
    out.println "import lombok.ToString; \n"
    
    out.println "import javax.persistence.Entity;"
    out.println "import javax.persistence.Id;"
    out.println "import javax.persistence.Table;"
    out.println "import java.io.Serializable;"
    out.println "import java.util.Date;"
    
    
    // 作者訊息
//    out.println "/**\n" +
//            " * @author JonasChen\n" +
//            " * @Description: groovy script auto generate \n" +
//            " */"

    //添加註釋
    out.println "@Data"
    out.println "@NoArgsConstructor"
    out.println "@AllArgsConstructor"
    out.println "@ToString"
    out.println "@Entity"
    out.println "@Table(name = \"$tableName\")"
    out.println "public class $className implements Serializable {"
    out.println ""
    out.println "private static final long serialVersionUID = -40356785423868312L; \n"
    fields.each() {
        // 如果基础属性中不包含字段名，才需要创建字段
        if (!baseFields.contains(it.name)) {
            // 输出注释
//            out.println "    /**"
//            out.println "     *  ${it.comment == null ? it.name : it.comment}"
//            out.println "     */"
            out.println "    //${it.comment == null ? it.name : it.comment}"

            if (it.name == "id") {
                out.println "    @Id"
            }

            out.println "    private ${it.type} ${it.name};\n"
        }
    }
    out.println "}"
}

/**
 * 计算表中每個字段名稱、類型、註釋
 * @param table
 * @return
 */
def calcFields(table) {
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
        fields += [[
                           col : col,
                           name   : javaName(col.getName(), false, false),
                           type   : typeStr,
                           comment: col.getComment()
                   ]]
    }
}
/**
 *
 * @param str
 * @param capitalize 是否需要開頭大寫
 * @param needRemovePrefix 是否需要去掉前缀  ms_user   User   @TableName("ms_user")
 * @return
 */
def javaName(str, capitalize, needRemovePrefix) {
    if (needRemovePrefix) {
        def index = str.indexOf("_")
        if (index != null) {
            str = str.substring(str.indexOf("_") + 1)
        }
    }
    def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
            .collect { Case.LOWER.apply(it).capitalize() }
            .join("")
            .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
    capitalize || s.length() == 1 ? s : Case.LOWER.apply(s[0]) + s[1..-1]
}
/**
 * 通過選中的文件夾路徑獲取包名
 * @param dir
 * @return
 */
def getPackageName(dir) {
    return dir.toString()
            .replaceAll("\\\\", ".")
            .replaceAll("/", ".")
            .replaceAll("^.*src(\\.main\\.java\\.)?", "") + ";"
}