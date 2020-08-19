package net.springboot.javaguids;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	
	@Autowired
	ProductRepository productRepository;
	
	
	public Page<Product> getAll(int pageNumber,String sortDirec,String sortField){
		Sort sort = Sort.by(sortField);
		sort=sortDirec.equals("asc")?sort.ascending():sort.descending();
		Pageable pageable=PageRequest.of(pageNumber - 1, 5,sort);
		return productRepository.findAll(pageable);
	}
	
	
	public void save(Product product) {
		productRepository.save(product);
	}
	
	
	public void delete(int id) {
		productRepository.deleteById(id);
	}
	
	
	public Product getById(int	 id) {
		return productRepository.findById(id).get();
	}
	
	
	
	
	
}
