import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        long startTime=new Date().getTime();
        String[] ignoredValues={"about","above","across","after","along","around","as","at","because","before","behind","below","beside","between","by","despite","down","during","except","for","from","in","into","like","near","of","on","out","outside","over","past","Preposition","since","through","to","toward","toward,about","under","until","with","within","another","anyone","anything","both","each","each other","either","everybody","everyone","everything","few","he","her","hers","herself","him","himself","his","I","it","its","itself","many","me","mine","my","myself","neither","no one","nobody","nothing","one another","other","our","ours","ourselves","she","some","someone","something","that","their","theirs","them","themselves","these","they","this","those","us","we","what","which","who","whom","whose","you","your","yours","yourself","yourselves","a","an","the","Accordingly","after","After","Also","although","And","as","because","before","Before","Besides","But","Consequently","Conversely","Finally","For","Furthermore","Hence","However","if","Indeed","Instead","Likewise","Meanwhile","Moreover","Nevertheless","Next","Nonetheless","Nor","Or","Otherwise","Similarly","since","So","Still","Subsequently","such","that","Then","Therefore","Thus","unless","until","when","where","while","Yet","is","was","were","may", "shall","will","would","could","can","might","should","must","has","have","cannot","need"};
        Path filePath = Paths.get("C:\\Users\\ShuklaSudhanshu\\IdeaProjects\\epicore_task\\src\\moby.txt");
        try{
            List<String> lines=Files.readAllLines(filePath);
            List<String> totalWords=new ArrayList<>();
            lines.parallelStream().forEach((line)->{
                line=line.replaceAll("[\\d.,?;!()&:\\-\"\\r\\n]","").toLowerCase();
                List<String> tokens=Arrays.asList(line.split(" "));
                totalWords.addAll(tokens.parallelStream().filter(token->{
                            if ( !token.endsWith("'s") && (token.endsWith("s") || token.endsWith("es")) )
                                return false;
                            else
                                return Arrays.stream(ignoredValues).noneMatch(token::equalsIgnoreCase);
                        }
                ).toList());
            });
            Map<String,Long> frequent=totalWords.parallelStream().
                    filter((key)->!"".equalsIgnoreCase(key))
                    .collect(Collectors.groupingBy(word->word,Collectors.counting())).entrySet()
                    .stream().sorted((Map.Entry.<String,Long>comparingByValue().reversed()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            int i=0;
            List<String> fiftyWords=new ArrayList<>();
            System.out.println("total word Count:"+totalWords.size());
            for (Map.Entry<String, Long> entry : frequent.entrySet()) {
                i++;
                if(i<=5 && entry.getValue()>1) {
                    System.out.println("key : '" + entry.getKey() + "'" + " no of times : '" + entry.getValue() + "'");
                }else {
                    if(entry.getValue()==1 && fiftyWords.size()<50)
                        fiftyWords.add(entry.getKey());
                    else if (fiftyWords.size()==50){
                        break;
                    }
                }
            }
            Collections.sort(fiftyWords);
            System.out.println(fiftyWords);
            System.out.println("final size is : "+fiftyWords.size());

        }catch(IOException e){
            e.printStackTrace();
        }

        long endTime=new Date().getTime();
        System.out.println("Total Time taken is : "+(endTime-startTime));

    }
}