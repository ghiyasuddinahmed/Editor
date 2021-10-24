public class FindPlugin implements FindPluginHandle {

    @Override
    public int[] getPosition(int caretPos, String s, String file) {
        int[] pos={-1,-1};
        int substrpos=file.indexOf(s,caretPos);
        if(substrpos!=-1){
        pos[0]=substrpos;
        pos[1]=substrpos+s.length();}

        return pos;
    }
}
