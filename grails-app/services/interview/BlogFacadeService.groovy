package interview

import com.lerss.ent.api.HelloWorldFacade
import grails.transaction.Transactional
import com.lerss.ent.api.*
import com.lerss.ent.api.BlogEntryDTO 

@Transactional
class BlogFacadeService implements BlogFacade {
	 /**
     * 获取最新的n条日志
     *
     * @param n n条
     * @return 日志列表
     */
    List<BlogEntryDTO> getRecentEntries(int n){
    	def blogEntries = BlogEntry.list(offset:0, max:n)
    	def list = new ArrayList<BlogEntryDTO>();
        
        blogEntries.collect{ 
		    def b = new BlogEntryDTO(title:it.title,content:it.content,dateCreated:it.dateCreated)
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
    	def blog = new BlogEntry(title:entry.title,content:entry.content)
		blog.setDateCreated(entry.getDateCreated())
    	if(!blog.save()){
		   blog.errors.allErrors.each{
		        logger.info(it)
		    }
		}else{
		   flush:true
		}
    }
}