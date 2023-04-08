function Category(props) {
    return (
        <div className='category' onClick={(e) => props.onCategoryPicked(props.category.trim())}>
            {props.category}
        </ div>
    )
}

export default Category;