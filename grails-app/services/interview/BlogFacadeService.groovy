package interview

import com.lerss.ent.api.HelloWorldFacade
import grails.transaction.Transactional
import com.lerss.ent.api.*
import com.lerss.ent.api.BlogEntryDTO //此处应该在下面空一行
@Transactional
class BlogFacadeService implements BlogFacade{//大括号前面应该有空格
	 /**
     * 获取最新的n条日志
     *
     * @param n n条
     * @return 日志列表
     */
    List<BlogEntryDTO> getRecentEntries(int n){
    	def blogentrys = BlogEntry.list(offset:0, max:n)//拼写错误，题目中已经包含了entry的复数形式entries，为什么没有参考呢？
    	//def blogsentrydto = blogentrys 没有用的代码应当及时删除掉
    	def list = new ArrayList<BlogEntryDTO>();
        
        blogentrys.each{ //这个地方用groovy的collect方法重写
		    def b = new BlogEntryDTO(title:it.getTitle(),content:it.getContent(),dateCreated:it.getDateCreated())//使用groovy的语法代替getXXX的Java写法
		    list.add(b)
		}
		
        return list
	 }
 
    /**
     * 发布一条日志
     *
     * @param entry 日志
     * @exception Exception 发布失败时候会抛出异常
     */
    void publish(BlogEntryDTO entry){
    //题目中要求发布失败抛出异常，实际上会抛出异常吗？	
    	def blog = new BlogEntry(title:entry.getTitle(),content:entry.getContent())
		blog.setDateCreated(entry.getDateCreated())
    	blog.save()
    }
}