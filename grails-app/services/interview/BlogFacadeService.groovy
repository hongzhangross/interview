package interview

import com.lerss.ent.api.HelloWorldFacade
import grails.transaction.Transactional
import com.lerss.ent.api.*
import com.lerss.ent.api.BlogEntryDTO 

@Transactional
class BlogFacadeService implements BlogFacade {//此文件中的tab和空格有混用的现象，需要统一成tab或者空格
	 /**
     * 获取最新的n条日志
     *
     * @param n n条
     * @return 日志列表
     */
    List<BlogEntryDTO> getRecentEntries(int n){//上面的都已经在大括号前面加上空格，这里也需要，类似的，其他的地方也需要修改
		def blogEntries = BlogEntry.list(offset:0, max:n)//需要在冒号后面加上空格
    	def list = new ArrayList<BlogEntryDTO>();
        
        blogEntries.collect{ //collect用法不正确，你可以搜索一下collect的用法，或者参考groovy的官方文档
		    def b = new BlogEntryDTO(title:it.title,content:it.content,dateCreated:it.dateCreated)//需要在逗号后面加上空格
		    list.add(b)
		}
		
        return list
	 }
 
    /**
     * 发布一条日志
     *
     * @param entry 日志
     * @exception Exception 发布失败时候会抛出异常//依然没有抛出异常
     */
    void publish(BlogEntryDTO entry){	
    	def blog = new BlogEntry(title:entry.title,content:entry.content)
		blog.setDateCreated(entry.getDateCreated())//没有groovy化
    	if(!blog.save()){
		   blog.errors.allErrors.each{
		        logger.info(it)
		    }
		}else{//else需要另起一行
		   flush:true
		}
    }
}